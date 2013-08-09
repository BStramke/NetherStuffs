package bstramke.NetherStuffs.Blocks.soulEngine;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Common.NetherStuffsCore;
import buildcraft.api.core.Position;
import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public abstract class TileEngine extends TileEntity implements IInventory, IPowerEmitter, IPowerReceptor, IOverrideDefaultTriggers, IPipeConnection {
	public static final ResourceLocation SoulengineTexture = new ResourceLocation("netherstuffs:textures/gfx/base_soulengine.png");

	// The Energy Stage the Engine goes through. If you need, you can define a Explosion State here
	public enum EnergyStage {
		BLUE, GREEN, YELLOW, RED
	}

	// Maximum Heat "Storage"
	public static int MAX_HEAT = 10000;

	boolean isActive = false; // Used for SMP synch
	public ForgeDirection orientation = ForgeDirection.UP;
	EnergyStage energyStage = EnergyStage.BLUE;
	protected int progressPart = 0;
	protected boolean lastPower = false;
	protected PowerHandler powerHandler;
	public float currentOutput = 0;
	public boolean isRedstonePowered = false;
	public float progress;
	public float energy;
	public float heat = 0;
	private ItemStack itemInInventory;

	public TileEngine() {
		powerHandler = new PowerHandler(this, PowerHandler.Type.ENGINE);
		powerHandler.configurePowerPerdition(0, 0);
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		if (type == PipeType.POWER)
			return ConnectOverride.DEFAULT;
		if (with == orientation)
			return ConnectOverride.DISCONNECT;
		return ConnectOverride.DEFAULT;
	}

	@Override
	public LinkedList<ITrigger> getTriggers() {
		LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();

		triggers.add(NetherStuffsCore.triggerBlueEngineHeat);
		triggers.add(NetherStuffsCore.triggerGreenEngineHeat);
		triggers.add(NetherStuffsCore.triggerYellowEngineHeat);
		triggers.add(NetherStuffsCore.triggerRedEngineHeat);

		return triggers;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		if (worldObj.isRemote)
			return;
		addEnergy(powerHandler.useEnergy(1, maxEnergyReceived(), true) * 0.95F);
	}
	
	public boolean switchOrientation() {
		for (int i = orientation.ordinal() + 1; i <= orientation.ordinal() + 6; ++i) {
			ForgeDirection o = ForgeDirection.VALID_DIRECTIONS[i % 6];

			Position pos = new Position(xCoord, yCoord, zCoord, o);
			pos.moveForwards(1);
			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (isPoweredTile(tile, o)) {
				orientation = o;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));

				return true;
			}
		}
		return false;
	}


	public void addEnergy(float addition) {
		energy += addition;

		if (energy > getMaxEnergy())
			energy = getMaxEnergy();
	}

	@Override
	public boolean canEmitPowerFrom(ForgeDirection side) {
		return side == orientation;
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

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	public float extractEnergy(float min, float max, boolean doExtract) {
		if (energy < min)
			return 0;

		float actualMax;

		if (max > maxEnergyExtracted())
			actualMax = maxEnergyExtracted();
		else
			actualMax = max;

		if (actualMax < min)
			return 0;

		float extracted;

		if (energy >= actualMax) {
			extracted = actualMax;
			if (doExtract)
				energy -= actualMax;
		} else {
			extracted = energy;
			if (doExtract)
				energy = 0;
		}

		return extracted;
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("orientation", orientation.ordinal());
		data.setFloat("progress", progress);
		data.setFloat("energyF", energy);
		data.setFloat("heat", heat);

		if (itemInInventory != null) {
			NBTTagCompound cpt = new NBTTagCompound();
			itemInInventory.writeToNBT(cpt);
			data.setTag("itemInInventory", cpt);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		orientation = ForgeDirection.getOrientation(data.getInteger("orientation"));
		progress = data.getFloat("progress");
		energy = data.getFloat("energyF");
		NBTBase tag = data.getTag("heat");
		if (tag instanceof NBTTagFloat)
			heat = data.getFloat("heat");
		
		itemInInventory.readFromNBT(data);
	}
	
	protected void burn() {
	}

	protected void engineUpdate() {
		if (!isRedstonePowered)
			if (energy >= 1)
				energy -= 1;
			else if (energy < 1)
				energy = 0;
	}

	public boolean isActive() {
		return true;
	}

	protected final void setPumping(boolean isActive) {
		if (this.isActive == isActive)
			return;

		this.isActive = isActive;
		sendNetworkUpdate();
	}
	
	public void getGUINetworkData(int id, int value) {
		switch (id) {
			case 0:
				int iEnergy = Math.round(energy * 10);
				iEnergy = (iEnergy & 0xffff0000) | (value & 0xffff);
				energy = iEnergy / 10;
				break;
			case 1:
				iEnergy = Math.round(energy * 10);
				iEnergy = (iEnergy & 0xffff) | ((value & 0xffff) << 16);
				energy = iEnergy / 10;
				break;
			case 2:
				currentOutput = value / 10F;
				break;
			case 3:
				heat = value / 100F;
				break;
		}
	}
	
	public float getEnergyStored() {
		return energy;
	}

	public void checkRedstonePower() {
		isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	public boolean isPoweredTile(TileEntity tile, ForgeDirection side) {
		if (tile instanceof IPowerReceptor)
			return ((IPowerReceptor) tile).getPowerReceiver(side.getOpposite()) != null;

		return false;
	}
	
	public void sendNetworkUpdate() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	/**
	 * dependent on heat it will set the stage on the engine. You could set your Exploding state here if a specific heat value goes over the maximum or calculate on Energy Stored
	 * etc.
	 */
	public void computeEnergyStage() {
		double Percentage = ((double) heat / (double) MAX_HEAT) * 100.0;
		EnergyStage tmp = energyStage;

		if (Percentage <= 25.0)
			energyStage = EnergyStage.BLUE;
		else if (Percentage <= 50.0)
			energyStage = EnergyStage.GREEN;
		else if (Percentage <= 75.0)
			energyStage = EnergyStage.YELLOW;
		else
			energyStage = EnergyStage.RED;

		if (tmp != energyStage)
			sendNetworkUpdate();
	}

	public final EnergyStage getEnergyStage() {
		if (!worldObj.isRemote) {
			computeEnergyStage();
		}

		return energyStage;
	}
	
	
	public abstract float maxEnergyReceived();

	public abstract float getMaxEnergy();

	public abstract float maxEnergyExtracted();
}
