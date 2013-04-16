package bstramke.NetherStuffs.Common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.Gui.ISoulEnergyTank;

public abstract class SoulEnergyTankTileEntity extends TileEntity implements ISoulEnergyTank, ISidedInventory, ITankContainer {
	private int maxTankLevel = 0;
	private LiquidTank tank;

	public SoulEnergyTankTileEntity(int MaxTankLevel) {
		maxTankLevel = MaxTankLevel;
		tank = new LiquidTank(maxTankLevel);
	}

	public abstract void fillFuelToTank();

	@Override
	public final int getMaxTankLevel() {
		return maxTankLevel;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	public final int getCurrentTankLevel() {
		if (this.tank.getLiquid() == null || this.tank.getLiquid().amount < 0)
			return 0;
		else
			return this.tank.getLiquid().amount;
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

	public LiquidTank getTank() {
		return tank;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return fill(0, resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		if (tankIndex == 0)
			return getTank().fill(resource, doFill);
		return 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return drain(0, maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		if (tankIndex == 0)
			return getTank().drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		if (type.isLiquidEqual(NetherStuffs.SoulEnergyLiquid))
			return getTank();
		else
			return null;
	}
	
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[] { tank };
	}

	public final void setCurrentTankLevel(int nAmount) {
		if (this.tank.getLiquid() != null)
			this.tank.getLiquid().amount = nAmount;
		else {
			LiquidStack liquid = NetherStuffs.SoulEnergyLiquid.copy();
			liquid.amount = nAmount;
			this.tank.setLiquid(liquid);
		}
	}

	public final int addFuelToTank(int nAmount) {
		int nRest = 0;
		LiquidStack liquid = NetherStuffs.SoulEnergyLiquid.copy();
		liquid.amount = nAmount;

		if (tank.getLiquid() == null || tank.getLiquid().amount <= 0)
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
	public final void openChest() {}

	@Override
	public final void closeChest() {}
}
