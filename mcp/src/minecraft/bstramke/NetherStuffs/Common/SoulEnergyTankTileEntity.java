package bstramke.NetherStuffs.Common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import bstramke.NetherStuffs.FluidRegistry;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Common.Gui.ISoulEnergyTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SoulEnergyTankTileEntity extends TileEntity implements ISoulEnergyTank, ISidedInventory, IFluidHandler {
	private FluidTank tank;

	public SoulEnergyTankTileEntity(int MaxTankLevel) {
		tank = new FluidTank(MaxTankLevel);
	}

	public abstract void fillFuelToTank();

	@Override
	public final int getMaxTankLevel() {
		return tank.getCapacity();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	public final int getCurrentTankLevel() {
		if (this.tank.getFluid() == null || this.tank.getFluid().amount < 0)
			return 0;
		else
			return this.tank.getFluid().amount;
	}

	public final boolean consumeFuelFromTank(int nConsumedFuel) {
		if (getCurrentTankLevel() - nConsumedFuel >= 0)
			setCurrentTankLevel(getCurrentTankLevel() - nConsumedFuel);
		else
			return false;
		return true;
	}

	@SideOnly(Side.CLIENT)
	public int getFillingScaled(int nPixelMax) {
		return (int) (((float) this.getCurrentTankLevel() / (float) this.getMaxTankLevel()) * nPixelMax);
	}

	public FluidTank getTank() {
		return tank;
	}
/*
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return fill(0, resource, doFill);
	}

	@Override
	public int fill(int tankIndex, FluidStack resource, boolean doFill) {
		if (tankIndex == 0)
			return getTank().fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return drain(0, maxDrain, doDrain);
	}/*

/*	@Override
	public FluidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		if (tankIndex == 0)
			return getTank().drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public IFluidTank getTank(ForgeDirection direction, FluidStack type) {
		if (type.isFluidEqual(NetherStuffs.SoulEnergyLiquid))
			return getTank();
		else
			return null;
	}
	
	@Override
	public IFluidTank[] getTanks(ForgeDirection direction) {
		return new IFluidTank[] { tank };
	}*/

	public final void setCurrentTankLevel(int nAmount) {
		if (this.tank.getFluid() != null)
			this.tank.getFluid().amount = nAmount;
		else {
			FluidStack liquid = new FluidStack(FluidRegistry.SoulEnergy, nAmount);
			tank.setFluid(liquid);
		}
	}

	public final int addFuelToTank(int nAmount) {
		int nRest = 0;
		FluidStack liquid = new FluidStack(FluidRegistry.SoulEnergy, nAmount);

		if (tank.getFluid() == null || tank.getFluid().amount <= 0)
			nRest = tank.fill(liquid, true);
		else
			nRest = tank.fill(liquid, true);

		return nRest;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("TankLevelNew", (short) this.getCurrentTankLevel());
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		if (tagCompound.getShort("TankLevel") > 0) {
			tagCompound.setShort("TankLevelNew", (short) (tagCompound.getShort("TankLevel") * 10));
			tagCompound.removeTag("TankLevel");
		}
		this.setCurrentTankLevel(tagCompound.getShort("TankLevelNew"));
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
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
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}
}
