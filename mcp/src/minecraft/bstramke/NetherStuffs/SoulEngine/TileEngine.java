package bstramke.NetherStuffs.SoulEngine;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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

public class TileEngine extends TileEntity implements IPowerReceptor, IInventory, ITankContainer, IOverrideDefaultTriggers, IPipeConnection {
	public static int MAX_LIQUID = LiquidContainerRegistry.BUCKET_VOLUME * 10;
	int progressPart = 0;
	float serverPistonSpeed = 0;
	boolean isActive = false; // Used for SMP synch

	boolean lastPower = false;

	public ForgeDirection orientation;

	IPowerProvider provider;

	public boolean isRedstonePowered = false;

	public int getMaxLiquid() {
		return MAX_LIQUID;
	}

	public TileEngine() {
		provider = PowerFramework.currentFramework.createPowerProvider();
		provider.configure(0, minEnergyReceived(), maxEnergyReceived(), 0, maxEnergy);
		checkRedstonePower();
		maxEnergy = 100000;
		maxEnergyExtracted = 500;
		fuelTank = new LiquidTank(MAX_LIQUID);
	}

	public void sendNetworkUpdate() {
		if (!worldObj.isRemote) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			// sendToPlayers(getUpdatePacket(), worldObj, xCoord, yCoord, zCoord, 128);
		}
	}

	public void sendToPlayers(Packet packet, World world, int x, int y, int z, int maxDistance) {
		if (packet != null) {
			for (int j = 0; j < world.playerEntities.size(); j++) {
				EntityPlayerMP player = (EntityPlayerMP) world.playerEntities.get(j);

				if (Math.abs(player.posX - x) <= maxDistance && Math.abs(player.posY - y) <= maxDistance && Math.abs(player.posZ - z) <= maxDistance) {
					player.playerNetServerHandler.sendPacketToPlayer(packet);
				}
			}
		}
	}

	public int getCurrentTankLevel() {
		if (fuelTank.getLiquid() == null || fuelTank.getLiquid().amount < 0)
			return 0;
		else
			return fuelTank.getLiquid().amount;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (this instanceof IPowerReceptor) {
			IPowerReceptor receptor = ((IPowerReceptor) this);

			receptor.getPowerProvider().update(receptor);
		}

		if (worldObj.isRemote) {
			if (progressPart != 0) {
				progress += serverPistonSpeed;

				if (progress > 1) {
					progressPart = 0;
					progress = 0;
				}
			} else if (this.isActive) {
				progressPart = 1;
			}

			return;
		}

		float newPistonSpeed = getPistonSpeed();
		if (newPistonSpeed != serverPistonSpeed) {
			serverPistonSpeed = newPistonSpeed;
			sendNetworkUpdate();
		}

		if (progressPart != 0) {
			progress += getPistonSpeed();

			if (progress > 0.5 && progressPart == 1) {
				progressPart = 2;

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
			} else if (progress >= 1) {
				progress = 0;
				progressPart = 0;
			}
		} else if (isRedstonePowered && isActive()) {

			Position pos = new Position(xCoord, yCoord, zCoord, orientation);
			pos.moveForwards(1.0);
			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (isPoweredTile(tile)) {
				IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();

				if (extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), false) > 0) {
					progressPart = 1;
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
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
				break;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		int orient = nbttagcompound.getInteger("orientation");

		progress = nbttagcompound.getFloat("progress");
		energy = nbttagcompound.getFloat("energyF");
		orientation = ForgeDirection.values()[orient];
		
		if (nbttagcompound.hasKey("liquidId")) {
			fuelTank.setLiquid(new LiquidStack(nbttagcompound.getInteger("liquidId"), nbttagcompound.getInteger("liquidQty"), nbttagcompound
					.getInteger("liquidMeta")));
		} else if (nbttagcompound.hasKey("fuelTank")) {
			fuelTank.setLiquid(LiquidStack.loadLiquidStackFromNBT(nbttagcompound.getCompoundTag("fuelTank")));
		}

		burnTime = nbttagcompound.getInteger("burnTime");

		heat = nbttagcompound.getInteger("heat");
		penaltyCooling = nbttagcompound.getInteger("penaltyCooling");

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
		nbttagcompound.setInteger("penaltyCooling", penaltyCooling);

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

	/* STATE INFORMATION */
	public boolean isBurning() {
		LiquidStack fuel = fuelTank.getLiquid();
		return fuel != null && fuel.amount > 0 && penaltyCooling == 0 && tile.isRedstonePowered;
	}

	public int getScaledBurnTime(int nPixelMax) {
		return (int) (((float) this.getCurrentTankLevel() / (float) MAX_LIQUID) * nPixelMax);
	}

	/* SMP UPDATING */
	@Override
	public Packet getDescriptionPacket() {
		return super.getDescriptionPacket();
	}

	/*
	 * @Override public Packet getUpdatePacket() { if (engine != null) { serverPistonSpeed = engine.getPistonSpeed(); }
	 * 
	 * return super.getUpdatePacket(); }
	 * 
	 * @Override public void handleDescriptionPacket(PacketUpdate packet) { createEngineIfNeeded();
	 * 
	 * super.handleDescriptionPacket(packet); }
	 * 
	 * @Override public void handleUpdatePacket(PacketUpdate packet) { createEngineIfNeeded();
	 * 
	 * super.handleUpdatePacket(packet); }
	 */

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

	public boolean isPoweredTile(TileEntity tile) {
		if (tile instanceof IPowerReceptor) {
			IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();

			return receptor != null && receptor.getClass().getSuperclass().equals(PowerProvider.class);
		}

		return false;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public int powerRequest() {
		return 0;
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

	@Override
	public boolean isPipeConnected(ForgeDirection with) {

		return with.ordinal() != orientation.ordinal();
	}

	public void checkRedstonePower() {
		isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
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
	public static int MAX_HEAT = 100000;
	
	private ItemStack itemInInventory;
	
	TileEngine tile;
	
	boolean lastPowered = false;
	int burnTime = 0;
	int heat = 0;
	
	private LiquidTank fuelTank;
	private SoulEngineFuel currentFuel = null;

	public int penaltyCooling = 0;
	
	
	public String getTextureFile() {
		return CommonProxy.GFXFOLDERPREFIX+"base_wood.png";
	}

	
	public int minEnergyReceived() {
		return 0;
	}

	
	public int maxEnergyReceived() {
		return 2000;
	}

	
	public float getPistonSpeed() {
		switch (getEnergyStage()) {
		case Blue:
			return 0.04F;
		case Green:
			return 0.05F;
		case Yellow:
			return 0.06F;
		case Red:
			return 0.07F;
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
		if (itemInInventory != null && itemInInventory.itemID == NetherItems.SoulEnergyBottle.itemID
				&& this.getCurrentTankLevel() < MAX_LIQUID) {
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
		if (!tile.isRedstonePowered) {
			if (energy >= 1) {
				energy -= 1;
			} else if (energy < 1) {
				energy = 0;
			}
		}

		fillFuelToTank();

		if (heat > 0 && (penaltyCooling > 0 || !tile.isRedstonePowered)) {
			heat -= 10;

		}

		if (heat <= 0) {
			heat = 0;
		}

		if (heat == 0 && penaltyCooling > 0) {
			penaltyCooling--;
		}
	}

	

	
	public void burn() {
		currentOutput = 0;
		LiquidStack fuel = this.fuelTank.getLiquid();
		if(currentFuel == null) {
			currentFuel = SoulEngineFuel.getFuelForLiquid(fuel);
		}

		if (currentFuel == null)
			return;

		if (penaltyCooling <= 0 && tile.isRedstonePowered) {

			lastPowered = true;

			if (burnTime > 0 || fuel.amount > 0) {
				if (burnTime > 0) {
					burnTime--;
				}
				if (burnTime <= 0) {
					if(fuel != null) {
						if (--fuel.amount <= 0) {
							fuelTank.setLiquid(null);
						}
						burnTime = currentFuel.totalBurningTime / LiquidContainerRegistry.BUCKET_VOLUME;
					} else {
						currentFuel = null;
						return;
					}
				}
				currentOutput = currentFuel.powerPerCycle;
				addEnergy(currentFuel.powerPerCycle);
				heat += currentFuel.powerPerCycle;
			}
		} else if (penaltyCooling <= 0) {
			if (lastPowered) {
				lastPowered = false;
				penaltyCooling = 30 * 20;
				// 30 sec of penalty on top of the cooling
			}
		}
	}

	
	public void computeEnergyStage() {
		if (heat <= MAX_HEAT / 4) {
			energyStage = EnergyStage.Blue;
		} else if (heat <= MAX_HEAT / 2) {
			energyStage = EnergyStage.Green;
		} else if (heat <= MAX_HEAT * 3F / 4F) {
			energyStage = EnergyStage.Yellow;
		} else if (heat <= MAX_HEAT) {
			energyStage = EnergyStage.Red;
		} else {
			energyStage = EnergyStage.Red;
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
			heat = (heat & 0xffff0000) | (j & 0xffff);
			break;
		case 4:
			heat = (heat & 0xffff) | ((j & 0xffff) << 16);
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
				fuelTank.getLiquid().itemID = j;
			}
			break;
		case 9:
			if (fuelTank.getLiquid() == null) {
				fuelTank.setLiquid(new LiquidStack(0, 0, j));
			} else {
				fuelTank.getLiquid().itemMeta = j;
			}
			break;
		}
	}

	
	public void sendGUINetworkData(ContainerEngine containerEngine, ICrafting iCrafting) {
		iCrafting.sendProgressBarUpdate(containerEngine, 0, Math.round(energy * 10) & 0xffff);
		iCrafting.sendProgressBarUpdate(containerEngine, 1, (Math.round(energy * 10) & 0xffff0000) >> 16);
		iCrafting.sendProgressBarUpdate(containerEngine, 2, Math.round(currentOutput * 10));
		iCrafting.sendProgressBarUpdate(containerEngine, 3, heat & 0xffff);
		iCrafting.sendProgressBarUpdate(containerEngine, 4, (heat & 0xffff0000) >> 16);
		iCrafting.sendProgressBarUpdate(containerEngine, 5, fuelTank.getLiquid() != null ? fuelTank.getLiquid().amount : 0);
		iCrafting.sendProgressBarUpdate(containerEngine, 6, fuelTank.getLiquid() != null ? fuelTank.getLiquid().itemID : 0);
		iCrafting.sendProgressBarUpdate(containerEngine, 9, fuelTank.getLiquid() != null ? fuelTank.getLiquid().itemMeta : 0);
	}

	
	public boolean isActive() {
		return penaltyCooling <= 0;
	}

	
	public int getHeat() {
		return heat;
	}
	
	
	public int maxEnergy;

	protected float currentOutput = 0;
	public float progress;
	
	public float energy;
	EnergyStage energyStage = EnergyStage.Blue;

	public int maxEnergyExtracted = 1;


	public enum EnergyStage {
		Blue, Green, Yellow, Red
	}


	public final EnergyStage getEnergyStage() {
		if (!tile.worldObj.isRemote) {
			computeEnergyStage();
		}

		return energyStage;
	}

	public void addEnergy(float addition) {
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
}