package bstramke.NetherStuffs.Blocks.soulEngine;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import bstramke.NetherStuffs.FluidRegistry;
import bstramke.NetherStuffs.Common.NetherStuffsCore;
import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.core.Position;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class TileSoulEngine extends TileEngine implements IFluidHandler {
	public static int MAX_LIQUID = FluidContainerRegistry.BUCKET_VOLUME * 10;
	private boolean init = false;

	IPowerProvider provider;
	private FluidTank fuelTank; // the Fuel Tank
	private SoulEngineFuel currentFuel = null; // the Currently used Fuel inside
	// the Engine (its moved from
	// the Tank to here to get
	// burned i guess)
	private ItemStack itemInInventory; // the Inventory. This might be used to
	// Let buckets Fill the Tank
	private int energyLossWhenUnpowered = 2;

	// The State of Progress, i think its the State of the moving, i.e. zero, move to the end, onend

	private enum ProgressState {
		Off, Calculate, Extracted
	}

	// Used to detect if the Engine receives a redstone Signal or not
	public boolean isRedstonePowered = false;

	// The internal Maximum stored Energy
	public int maxEnergy = 10000;
	// Current Heat Value
	int heat = 0;

	// The Time the Fuel has left to burn. Lava uses 20, Soul Energy 10 ticks to be used up (coal in a Furnace might have sth. around 1600 cause it lasts 80 seconds? not sure)
	int burnTime = 0;

	// the Energy Output of the Fuel (1MJ for SoulEnergy / Lava, Oil gets 3MJ etc)
	protected float currentOutput = 0;

	// How much Energy is Extracted per Cycle (maybe each tick, dont know exactly)
	public int maxEnergyExtracted = 100;

	// The current amount of Stored Energy
	public float energy = 0;
	// This SHOULD (i dont know it) be the progress of the Piston animation
	public float progress = 0;

	// This should equal the progressPart variable in default BC source. I switched this to an enum to make it easier to read
	ProgressState lastProgressState = ProgressState.Off;

	// The moving Speed of the Piston animations
	float serverPistonSpeed = 0;

	public TileSoulEngine() {
		fuelTank = new FluidTank(MAX_LIQUID);
	}

	public ResourceLocation getTextureFile() {
		return SoulengineTexture;
	}

	public int minEnergyReceived() {
		return 0;
	}

	// Max Activation Energy (i.e. Powering through other Engines)
	public float maxEnergyReceived() {
		return 0;
	}

	public int getCurrentTankLevel() {
		if (fuelTank.getFluid() == null || fuelTank.getFluid().amount < 0)
			return 0;
		else
			return fuelTank.getFluid().amount;
	}

	public void initialize() {
		if (!worldObj.isRemote) {
			/*
			 * provider = PowerFramework.currentFramework.createPowerProvider(); provider.configure(0, minEnergyReceived(), maxEnergyReceived(), 0, maxEnergy); checkRedstonePower();
			 */
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
				progress += getPistonSpeed();

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
		
		if (progressPart != 0) {
			progress += getPistonSpeed();

			if (progress > 0.5 && progressPart == 1) {
				progressPart = 2;
				sendPower(); // Comment out for constant power
			} else if (progress >= 1) {
				progress = 0;
				progressPart = 0;
			}
		} else if (isRedstonePowered && isActive())
			if (isPoweredTile(this, orientation))
				if (getPowerToExtract() > 0) {
					progressPart = 1;
					setPumping(true);
				} else
					setPumping(false);
			else
				setPumping(false);
		else
			setPumping(false);
		
		/*float newPistonSpeed = getPistonSpeed();
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
		}*/

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

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		progress = nbttagcompound.getFloat("progress");
		energy = nbttagcompound.getFloat("energyF");
		orientation = ForgeDirection.values()[nbttagcompound.getInteger("orientation")];

		if (nbttagcompound.hasKey("liquidId")) {
			fuelTank.setFluid(new FluidStack(nbttagcompound.getInteger("liquidId"), nbttagcompound.getInteger("liquidQty")/*, nbttagcompound.getInteger("liquidMeta")*/));
		} else if (nbttagcompound.hasKey("fuelTank")) {
			fuelTank.setFluid(FluidStack.loadFluidStackFromNBT(nbttagcompound.getCompoundTag("fuelTank")));
		}

		burnTime = nbttagcompound.getInteger("burnTime");
		if (nbttagcompound.hasKey("serverPistonSpeed"))
			serverPistonSpeed = nbttagcompound.getFloat("serverPistonSpeed");

		if (nbttagcompound.hasKey("isActive"))
			isActive = nbttagcompound.getBoolean("isActive");

		if (nbttagcompound.hasKey("energyStage")) {
			int nStage = nbttagcompound.getInteger("energyStage");
			if (nStage == EnergyStage.BLUE.ordinal())
				energyStage = EnergyStage.BLUE;
			else if (nStage == EnergyStage.GREEN.ordinal())
				energyStage = EnergyStage.GREEN;
			else if (nStage == EnergyStage.YELLOW.ordinal())
				energyStage = EnergyStage.YELLOW;
			else if (nStage == EnergyStage.RED.ordinal())
				energyStage = EnergyStage.RED;
			else
				energyStage = EnergyStage.BLUE;
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

		if (fuelTank.getFluid() != null) {
			nbttagcompound.setTag("fuelTank", fuelTank.getFluid().writeToNBT(new NBTTagCompound()));
		}

		nbttagcompound.setInteger("burnTime", burnTime);
		nbttagcompound.setInteger("heat", heat);

		if (itemInInventory != null) {
			NBTTagCompound cpt = new NBTTagCompound();
			itemInInventory.writeToNBT(cpt);
			nbttagcompound.setTag("itemInInventory", cpt);
		}
	}

	// IINVENTORY IMPLEMENTATION
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
		FluidStack fuel = fuelTank.getFluid();
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

	/**
	 * Checks if the Engine receives a Redstone Signal Sets isRedstonePowered accordingly
	 */
	public void checkRedstonePower() {
		if (worldObj != null)
			isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		else
			isRedstonePowered = false;
	}

	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (SoulEngineFuel.getFuelForLiquid(resource) != null)
			return fuelTank.fill(resource, doFill);

		return 0;
	}

	public FluidTank[] getFluidSlots() {
		return new FluidTank[] { fuelTank };
	}

	public FluidStack getFuel() {
		return fuelTank.getFluid();
	}


	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}


	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (itemstack == null)
			return false;
		
		return FluidContainerRegistry.getFluidForFilledItem(itemstack) != null;
	}

	public float getPistonSpeed() {
		switch (getEnergyStage()) {
		case BLUE:
			return 0.05F;
		case GREEN:
			return 0.06F;
		case YELLOW:
			return 0.07F;
		case RED:
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
		if (this.fuelTank.getFluid() != null)
			this.fuelTank.getFluid().amount = nAmount;
		else {
			FluidStack liquid = new FluidStack(FluidRegistry.SoulEnergy, nAmount);
			this.fuelTank.setFluid(liquid);
		}
	}

	private void fillFuelToTank() {
		if (itemInInventory != null && itemInInventory.itemID == ItemRegistry.SoulEnergyBottle.itemID && this.getCurrentTankLevel() < MAX_LIQUID) {
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

		if (heat > 0 && isBurning() == false) // its not burning, decrease heat
			heat -= 10;

		if (heat < 1)
			heat = 0;

		fillFuelToTank();
	}

	public void burn() {
		currentOutput = 0;
		FluidStack fuel = this.fuelTank.getFluid();
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
							fuelTank.setFluid(null);
						}
						// set the burnTime to the value it gets from the currentFuel. Lava has a Burntime of 20000, SoulEnergy 10000, whcih gets devided by Bucket_Volume (which is
						// 1000). This means Lava gets a Value of 20, SoulEnergy a Value of 10 ==> Lava burns every 20 Ticks, Soul Energy every 10 Ticks
						burnTime = currentFuel.totalBurningTime / FluidContainerRegistry.BUCKET_VOLUME;
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
			if (fuelTank.getFluid() == null) {
				fuelTank.setFluid(new FluidStack(0, j));
			} else {
				fuelTank.getFluid().amount = j;
			}
			break;
		case 6:
			if (fuelTank.getFluid() == null) {
				fuelTank.setFluid(new FluidStack(j, 0));
			} else {
				fuelTank.setFluid(new FluidStack(j, fuelTank.getFluid().amount));
			}
			break;
		}
	}

	public void sendGUINetworkData(ContainerSoulEngine containerEngine, ICrafting iCrafting) {
		iCrafting.sendProgressBarUpdate(containerEngine, 0, Math.round(energy * 10) & 0xffff);
		iCrafting.sendProgressBarUpdate(containerEngine, 1, (Math.round(energy * 10) & 0xffff0000) >> 16);
		iCrafting.sendProgressBarUpdate(containerEngine, 2, Math.round(currentOutput * 10));
		iCrafting.sendProgressBarUpdate(containerEngine, 3, heat);
		iCrafting.sendProgressBarUpdate(containerEngine, 5, fuelTank.getFluid() != null ? fuelTank.getFluid().amount : 0);
		iCrafting.sendProgressBarUpdate(containerEngine, 6, fuelTank.getFluid() != null ? fuelTank.getFluid().fluidID : 0);
	}

	public int getHeat() {
		return heat;
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
	
	private void sendPower() {
		TileEntity tile = this;
		if (isPoweredTile(tile, orientation)) {
			PowerReceiver receptor = ((IPowerReceptor) tile).getPowerReceiver(orientation.getOpposite());

			float extracted = getPowerToExtract();
			if (extracted > 0) {
				float needed = receptor.receiveEnergy(PowerHandler.Type.ENGINE, extracted, orientation.getOpposite());
				extractEnergy(receptor.getMinEnergyReceived(), needed, true); // Comment out for constant power
//				currentOutput = extractEnergy(0, needed, true); // Uncomment for constant power
			}
		}
	}
	
	private float getPowerToExtract() {
		TileEntity tile = this;
		PowerReceiver receptor = ((IPowerReceptor) tile).getPowerReceiver(orientation.getOpposite());
		return extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), false); // Comment out for constant power
//		return extractEnergy(0, getActualOutput(), false); // Uncomment for constant power
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getMaxEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float maxEnergyExtracted() {
		// TODO Auto-generated method stub
		return 0;
	}
}