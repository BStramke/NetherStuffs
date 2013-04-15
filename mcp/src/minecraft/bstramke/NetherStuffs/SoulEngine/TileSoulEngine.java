package bstramke.NetherStuffs.SoulEngine;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.NetherStuffsCore;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.core.Position;
import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import buildcraft.api.power.PowerProvider;
import buildcraft.api.transport.IPipeConnection;

public class TileSoulEngine extends TileEntity implements IPowerReceptor, IInventory, ITankContainer, IOverrideDefaultTriggers, IPipeConnection {
	public static int MAX_LIQUID = LiquidContainerRegistry.BUCKET_VOLUME * 10;
	private boolean init = false;
	public ForgeDirection orientation = ForgeDirection.UP;
	EnergyStage energyStage = EnergyStage.Blue;
	IPowerProvider provider;
	private LiquidTank fuelTank; // the Fuel Tank
	private SoulEngineFuel currentFuel = null; // the Currently used Fuel inside
	// the Engine (its moved from
	// the Tank to here to get
	// burned i guess)
	private ItemStack itemInInventory; // the Inventory. This might be used to
	// Let buckets Fill the Tank
	private int energyLossWhenUnpowered = 2;

	/**
	 * The Energy Stage the Engine goes through. If you need, you can define a Explosion State here
	 */
	public enum EnergyStage {
		Blue, Green, Yellow, Red
	}

	/**
	 * The State of Progress, i think its the State of the moving, i.e. zero, move to the end, onend
	 */
	private enum ProgressState {
		Off, Calculate, Extracted
	}

	/**
	 * Maximum Heat "Storage"
	 */
	public static int MAX_HEAT = 10000;
	/**
	 * Used for SMP synchronisation
	 */
	boolean isActive = false;
	/**
	 * Used to detect if the Engine receives a redstone Signal or not
	 */
	public boolean isRedstonePowered = false;
	/**
	 * The internal Maximum stored Energy
	 */
	public int maxEnergy = 10000;
	/**
	 * Current Heat Value
	 */
	int heat = 0;
	/**
	 * The Time the Fuel has left to burn. Lava uses 20, Soul Energy 10 ticks to be used up (coal in a Furnace might have sth. around 1600 cause it lasts 80 seconds? not sure)
	 */
	int burnTime = 0;
	/**
	 * the Energy Output of the Fuel (1MJ for SoulEnergy / Lava, Oil gets 3MJ etc)
	 */
	protected float currentOutput = 0;
	/**
	 * How much Energy is Extracted per Cycle (maybe each tick, dont know exactly)
	 */
	public int maxEnergyExtracted = 100;
	/**
	 * The current amount of Stored Energy
	 */
	public float energy = 0;
	/**
	 * This SHOULD (i dont know it) be the progress of the Piston animation
	 */
	public float progress = 0;

	/**
	 * This should equal the progressPart variable in default BC source. I switched this to an enum to make it easier to read
	 */
	ProgressState lastProgressState = ProgressState.Off;

	/**
	 * The moving Speed of the Piston animations
	 */
	float serverPistonSpeed = 0;

	public TileSoulEngine() {
		fuelTank = new LiquidTank(MAX_LIQUID);
	}

	public String getTextureFile() {
		return CommonProxy.GFXFOLDERPREFIX + "base_soulengine.png";
	}

	public int minEnergyReceived() {
		return 0;
	}

	/**
	 * Max Activation Energy (i.e. Powering through other Engines)
	 */
	public int maxEnergyReceived() {
		return 0;
	}

	public void sendNetworkUpdate() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public int getCurrentTankLevel() {
		if (fuelTank.getLiquid() == null || fuelTank.getLiquid().amount < 0)
			return 0;
		else
			return fuelTank.getLiquid().amount;
	}

	public void initialize() {
		if (!worldObj.isRemote) {
			provider = PowerFramework.currentFramework.createPowerProvider();
			provider.configure(0, minEnergyReceived(), maxEnergyReceived(), 0, maxEnergy);
			checkRedstonePower();
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (!init && !isInvalid()) {
			initialize();
			init = true;
		}

		if (worldObj.isRemote) {
			if (lastProgressState != ProgressState.Off) {
				progress += serverPistonSpeed;

				if (progress > 1) {
					lastProgressState = ProgressState.Off;
					progress = 0;
				}
			} else if (this.isActive) {
				lastProgressState = ProgressState.Calculate;
			}
			return;
		}

		update();

		float newPistonSpeed = getPistonSpeed();
		if (newPistonSpeed != serverPistonSpeed) {
			serverPistonSpeed = newPistonSpeed;
			sendNetworkUpdate();
		}

		if (lastProgressState != ProgressState.Off) {
			progress += getPistonSpeed();

			if (progress > 0.5 && lastProgressState == ProgressState.Calculate) {
				lastProgressState = ProgressState.Extracted;

				Position pos = new Position(xCoord, yCoord, zCoord, orientation);
				pos.moveForwards(1.0);
				TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

				if (isPoweredTile(tile)) {
					IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();

					float extracted = extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), true);

					if (extracted > 0) {
						receptor.receiveEnergy(extracted, orientation.getOpposite());
					}
				}
			} else if (progress >= 1 && (lastProgressState != ProgressState.Off)) {
				progress = 0;
				lastProgressState = ProgressState.Off;
			}
		} else if (isRedstonePowered) {

			Position pos = new Position(xCoord, yCoord, zCoord, orientation);
			pos.moveForwards(1.0);
			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (isPoweredTile(tile)) {
				IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();

				if (extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), false) > 0) {
					lastProgressState = ProgressState.Calculate;
					setActive(true);
				} else {
					setActive(false);
				}
			} else {
				setActive(false);
			}

		} else {
			setActive(false);
		}

		burn();
	}

	/**
	 * Handles the Active Flag. Sends Network Update when neccessary
	 * 
	 * @param isActive
	 *           The value to set the Active Flag to
	 */
	private void setActive(boolean isActive) {
		if (this.isActive == isActive)
			return;

		this.isActive = isActive;
		sendNetworkUpdate();
	}

	public void switchOrientation() {
		for (int i = orientation.ordinal() + 1; i <= orientation.ordinal() + 6; ++i) {
			ForgeDirection o = ForgeDirection.values()[i % 6];

			Position pos = new Position(xCoord, yCoord, zCoord, o);

			pos.moveForwards(1);

			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (isPoweredTile(tile)) {
				orientation = o;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
				break;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		progress = nbttagcompound.getFloat("progress");
		energy = nbttagcompound.getFloat("energyF");
		orientation = ForgeDirection.values()[nbttagcompound.getInteger("orientation")];

		if (nbttagcompound.hasKey("liquidId")) {
			fuelTank.setLiquid(new LiquidStack(nbttagcompound.getInteger("liquidId"), nbttagcompound.getInteger("liquidQty"), nbttagcompound.getInteger("liquidMeta")));
		} else if (nbttagcompound.hasKey("fuelTank")) {
			fuelTank.setLiquid(LiquidStack.loadLiquidStackFromNBT(nbttagcompound.getCompoundTag("fuelTank")));
		}

		burnTime = nbttagcompound.getInteger("burnTime");
		if (nbttagcompound.hasKey("serverPistonSpeed"))
			serverPistonSpeed = nbttagcompound.getFloat("serverPistonSpeed");

		if (nbttagcompound.hasKey("isActive"))
			isActive = nbttagcompound.getBoolean("isActive");

		if (nbttagcompound.hasKey("energyStage")) {
			int nStage = nbttagcompound.getInteger("energyStage");
			if (nStage == EnergyStage.Blue.ordinal())
				energyStage = EnergyStage.Blue;
			else if (nStage == EnergyStage.Green.ordinal())
				energyStage = EnergyStage.Green;
			else if (nStage == EnergyStage.Yellow.ordinal())
				energyStage = EnergyStage.Yellow;
			else if (nStage == EnergyStage.Red.ordinal())
				energyStage = EnergyStage.Red;
			else
				energyStage = EnergyStage.Blue;
		}

		heat = nbttagcompound.getInteger("heat");

		if (nbttagcompound.hasKey("itemInInventory")) {
			NBTTagCompound cpt = nbttagcompound.getCompoundTag("itemInInventory");
			itemInInventory = ItemStack.loadItemStackFromNBT(cpt);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("orientation", orientation.ordinal());
		nbttagcompound.setFloat("progress", progress);
		nbttagcompound.setFloat("energyF", energy);

		if (fuelTank.getLiquid() != null) {
			nbttagcompound.setTag("fuelTank", fuelTank.getLiquid().writeToNBT(new NBTTagCompound()));
		}

		nbttagcompound.setInteger("burnTime", burnTime);
		nbttagcompound.setInteger("heat", heat);

		if (itemInInventory != null) {
			NBTTagCompound cpt = new NBTTagCompound();
			itemInInventory.writeToNBT(cpt);
			nbttagcompound.setTag("itemInInventory", cpt);
		}
	}

	/* IINVENTORY IMPLEMENTATION */
	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return itemInInventory;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		itemInInventory = itemstack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (itemInInventory != null) {
			if (itemInInventory.stackSize <= 0) {
				itemInInventory = null;
				return null;
			}
			ItemStack newStack = itemInInventory;
			if (amount >= newStack.stackSize) {
				itemInInventory = null;
			} else {
				newStack = itemInInventory.splitStack(amount);
			}

			return newStack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if (itemInInventory == null)
			return null;
		ItemStack toReturn = itemInInventory;
		itemInInventory = null;
		return toReturn;
	}

	@Override
	public String getInvName() {
		return "Engine";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this;
	}

	/**
	 * Running Device which has a Signal and Fuel
	 */
	public boolean isBurning() {
		LiquidStack fuel = fuelTank.getLiquid();
		return fuel != null && fuel.amount > 0 && isRedstonePowered;
	}

	/**
	 * Gets the Size of the Tank Level scaled to match the current level
	 * 
	 * @param nPixelMax
	 *           Maximum Height of the Tank Level Picture
	 * @return Height of the Tank Level in Pixels
	 */
	public int getScaledTankLevel(int nPixelMax) {
		return (int) (((float) this.getCurrentTankLevel() / (float) MAX_LIQUID) * nPixelMax);
	}

	@Override
	public void setPowerProvider(IPowerProvider provider) {
		this.provider = provider;
	}

	@Override
	public IPowerProvider getPowerProvider() {
		return provider;
	}

	@Override
	public void doWork() {
		if (worldObj.isRemote)
			return;

		addEnergy(provider.useEnergy(1, maxEnergyReceived(), true) * 0.95F);
	}

	/**
	 * Checks if the given Tile is able to receive Power. Used for Orientating and Sending Power
	 * 
	 * @param tile
	 *           The Tile to check
	 * @return true if the tile is an IPowerReceptor
	 */
	public boolean isPoweredTile(TileEntity tile) {
		if (tile instanceof IPowerReceptor) {
			IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();
			return receptor != null && receptor.getClass().getSuperclass().equals(PowerProvider.class);
		}

		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public LinkedList<ITrigger> getTriggers() {
		LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();

		triggers.add(NetherStuffsCore.triggerBlueEngineHeat);
		triggers.add(NetherStuffsCore.triggerGreenEngineHeat);
		triggers.add(NetherStuffsCore.triggerYellowEngineHeat);
		triggers.add(NetherStuffsCore.triggerRedEngineHeat);

		triggers.add(NetherStuffsCore.triggerEmptyLiquid);
		triggers.add(NetherStuffsCore.triggerContainsLiquid);
		triggers.add(NetherStuffsCore.triggerSpaceLiquid);
		triggers.add(NetherStuffsCore.triggerFullLiquid);

		return triggers;
	}

	@Override
	public boolean isPipeConnected(ForgeDirection with) {
		return with.ordinal() != orientation.ordinal();
	}

	/**
	 * Checks if the Engine receives a Redstone Signal Sets isRedstonePowered accordingly
	 */
	public void checkRedstonePower() {
		if (worldObj != null)
			isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		else
			isRedstonePowered = false;
	}

	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		if (SoulEngineFuel.getFuelForLiquid(resource) != null)
			return fuelTank.fill(resource, doFill);

		return 0;
	}

	public LiquidTank[] getLiquidSlots() {
		return new LiquidTank[] { fuelTank };
	}

	public LiquidStack getFuel() {
		return fuelTank.getLiquid();
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		return fill(ForgeDirection.UNKNOWN, resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		switch (direction) {
		case UP:
		case DOWN:
			return fuelTank;
		default:
			return null;
		}
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

	public float getPistonSpeed() {
		switch (getEnergyStage()) {
		case Blue:
			return 0.05F;
		case Green:
			return 0.06F;
		case Yellow:
			return 0.07F;
		case Red:
			return 0.08F;
		default:
			return 0.0f;
		}
	}

	private static ItemStack consumeItem(ItemStack stack) {
		if (stack.stackSize == 1) {
			if (stack.getItem().hasContainerItem())
				return stack.getItem().getContainerItemStack(stack);
			else
				return null;
		} else {
			stack.splitStack(1);

			return stack;
		}
	}

	public void setCurrentTankLevel(int nAmount) {
		if (this.fuelTank.getLiquid() != null)
			this.fuelTank.getLiquid().amount = nAmount;
		else {
			LiquidStack liquid = NetherStuffs.SoulEnergyLiquid.copy();
			liquid.amount = nAmount;
			this.fuelTank.setLiquid(liquid);
		}
	}

	private void fillFuelToTank() {
		if (itemInInventory != null && itemInInventory.itemID == NetherItems.SoulEnergyBottle.itemID && this.getCurrentTankLevel() < MAX_LIQUID) {
			if (this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(itemInInventory) > MAX_LIQUID) {
				SoulEnergyBottle.decreaseSoulEnergyAmount(itemInInventory, MAX_LIQUID - this.getCurrentTankLevel());
				this.setCurrentTankLevel(MAX_LIQUID);
			} else {
				this.setCurrentTankLevel(this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(itemInInventory));
				SoulEnergyBottle.setSoulEnergyAmount(itemInInventory, 0);
			}
		}
	}

	public void update() {
		// energy loss when the engine is not running
		if (!isRedstonePowered) {
			energy -= energyLossWhenUnpowered;
			if (energy <= 0)
				energy = 0;
		}
		if (heat > 0 && (!isRedstonePowered))
			heat -= 10;

		if (heat < 1)
			heat = 0;

		fillFuelToTank();
	}

	public void burn() {
		currentOutput = 0;
		LiquidStack fuel = this.fuelTank.getLiquid();
		if (currentFuel == null) {
			currentFuel = SoulEngineFuel.getFuelForLiquid(fuel);
		}

		if (currentFuel == null)
			return;

		if (isRedstonePowered) {
			// Check if the engine burns or atleast has fuel to be burned
			if (burnTime > 0 || fuel.amount > 0) {
				// the burnTime is set when a Fuel "Part" is being burned. this
				// means it decreases the burntime counter (i.e a coal may burn
				// for 100 Ticks (not accurate, just an
				// example)
				if (burnTime > 0) {
					burnTime--;
				}

				if (burnTime <= 0) { // burntime is NOW zero, we have to burn fuel if its available
					if (fuel != null) {
						if (--fuel.amount <= 0) {
							fuelTank.setLiquid(null);
						}
						// set the burnTime to the value it gets from the currentFuel. Lava has a Burntime of 20000, SoulEnergy 10000, whcih gets devided by Bucket_Volume (which is
						// 1000). This means Lava gets a Value of 20, SoulEnergy a Value of 10 ==> Lava burns every 20 Ticks, Soul Energy every 10 Ticks
						burnTime = currentFuel.totalBurningTime / LiquidContainerRegistry.BUCKET_VOLUME;
					} else {
						currentFuel = null;
						return;
					}
				}

				// we are currently burning, so receive our energy (all other cases, ie. no fuel available are already handled)
				currentOutput = currentFuel.powerPerCycle; // set the Energy Output to the Capability of the Fuel (1MJ for SoulEnergy / Lava, Oil gets 3MJ etc)
				addEnergy(currentFuel.powerPerCycle); // increase the overall available Energy (if there is space)
				addHeat(currentFuel.powerPerCycle); // increase the Heat by the value of MJ. This MAY be used to determine how fast an engine goes, for example the Combustion Engine
																// "heats up" to increase the Piston Speed and determine the Energy Stage
			}
		}
	}

	/**
	 * dependent on heat it will set the stage on the engine. You could set your Exploding state here if a specific heat value goes over the maximum or calculate on Energy Stored
	 * etc.
	 */
	public void computeEnergyStage() {
		double Percentage = ((double) heat / (double) MAX_HEAT) * 100.0;
		EnergyStage tmp = energyStage;

		if (Percentage <= 25.0)
			energyStage = EnergyStage.Blue;
		else if (Percentage <= 50.0)
			energyStage = EnergyStage.Green;
		else if (Percentage <= 75.0)
			energyStage = EnergyStage.Yellow;
		else
			energyStage = EnergyStage.Red;

		if (tmp != energyStage)
			sendNetworkUpdate();
	}

	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 0:
			int iEnergy = Math.round(energy * 10);
			iEnergy = (iEnergy & 0xffff0000) | (j & 0xffff);
			energy = iEnergy / 10;
			break;
		case 1:
			iEnergy = Math.round(energy * 10);
			iEnergy = (iEnergy & 0xffff) | ((j & 0xffff) << 16);
			energy = iEnergy / 10;
			break;
		case 2:
			currentOutput = j / 10;
			break;
		case 3:
			heat = j;
			break;
		case 5:
			if (fuelTank.getLiquid() == null) {
				fuelTank.setLiquid(new LiquidStack(0, j));
			} else {
				fuelTank.getLiquid().amount = j;
			}
			break;
		case 6:
			if (fuelTank.getLiquid() == null) {
				fuelTank.setLiquid(new LiquidStack(j, 0));
			} else {
				fuelTank.setLiquid(new LiquidStack(j, fuelTank.getLiquid().amount, fuelTank.getLiquid().itemMeta));
			}
			break;
		case 9:
			if (fuelTank.getLiquid() == null) {
				fuelTank.setLiquid(new LiquidStack(0, 0, j));
			} else {
				fuelTank.setLiquid(new LiquidStack(fuelTank.getLiquid().itemID, fuelTank.getLiquid().amount, j));
			}
			break;
		}
	}

	public void sendGUINetworkData(ContainerSoulEngine containerEngine, ICrafting iCrafting) {
		iCrafting.sendProgressBarUpdate(containerEngine, 0, Math.round(energy * 10) & 0xffff);
		iCrafting.sendProgressBarUpdate(containerEngine, 1, (Math.round(energy * 10) & 0xffff0000) >> 16);
		iCrafting.sendProgressBarUpdate(containerEngine, 2, Math.round(currentOutput * 10));
		iCrafting.sendProgressBarUpdate(containerEngine, 3, heat);
		iCrafting.sendProgressBarUpdate(containerEngine, 5, fuelTank.getLiquid() != null ? fuelTank.getLiquid().amount : 0);
		iCrafting.sendProgressBarUpdate(containerEngine, 6, fuelTank.getLiquid() != null ? fuelTank.getLiquid().itemID : 0);
		iCrafting.sendProgressBarUpdate(containerEngine, 9, fuelTank.getLiquid() != null ? fuelTank.getLiquid().itemMeta : 0);
	}

	public int getHeat() {
		return heat;
	}

	public final EnergyStage getEnergyStage() {
		if (!worldObj.isRemote) {
			computeEnergyStage();
		}

		return energyStage;
	}

	/**
	 * Add to the Heat value. If you want your engine to Explode, you will have to handle the heat > MAX_HEAT differently (add an explosion State)
	 * 
	 * @param addition
	 *           the amount of Heat to add
	 */
	private void addHeat(float addition) {
		if (heat > MAX_HEAT) {
			heat = MAX_HEAT;
		} else {
			heat += addition;
			if (heat > MAX_HEAT)
				heat = MAX_HEAT;
		}
	}

	private void addEnergy(float addition) {
		energy += addition;

		if (energy > maxEnergy) {
			energy = maxEnergy;
		}
	}

	public float extractEnergy(int min, int max, boolean doExtract) {
		if (energy < min)
			return 0;

		int actualMax;

		if (max > maxEnergyExtracted) {
			actualMax = maxEnergyExtracted;
		} else {
			actualMax = max;
		}

		if (actualMax < min)
			return 0;

		float extracted;

		if (energy >= actualMax) {
			extracted = actualMax;
			if (doExtract) {
				energy -= actualMax;
			}
		} else {
			extracted = energy;
			if (doExtract) {
				energy = 0;
			}
		}

		return extracted;
	}

	public float getEnergyStored() {
		return energy;
	}

	public float getCurrentOutput() {
		return currentOutput;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		ILiquidTank[] tanks = new ILiquidTank[1];
		tanks[0] = fuelTank;
		return tanks;
	}

	@Override
	public void onDataPacket(net.minecraft.network.INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.customParam1);
	};

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound var1 = new NBTTagCompound();
		this.writeToNBT(var1);
		if (!worldObj.isRemote) {
			var1.setFloat("serverPistonSpeed", serverPistonSpeed);
			var1.setBoolean("isActive", isActive);
			var1.setInteger("energyStage", energyStage.ordinal());
		}
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, var1);
	}

	@Override
	public int powerRequest(ForgeDirection from) {
		return 0;
	}
}