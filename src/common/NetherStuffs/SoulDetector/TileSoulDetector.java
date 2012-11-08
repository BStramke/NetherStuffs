package NetherStuffs.SoulDetector;

import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileSoulDetector extends TileEntity implements IInventory {

	private static final int nRangeNorth = 0;
	private static final int nRangeSouth = 1;
	private static final int nRangeWest = 2;
	private static final int nRangeEast = 3;
	private static final int nRangeDown = 4;
	private static final int nRangeUp = 5;

	protected int[] detectionRanges = new int[] { 0, 0, 0, 0, 0, 0 };
	protected int[] detectionRangesMax = new int[] { 20, 20, 20, 20, 20, 20 };

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getInvName() {
		return "container.souldetector";
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
						zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	protected int getRange(ForgeDirection dir) {
		switch (dir) {
		case NORTH:
			return this.detectionRanges[this.nRangeNorth];
		case SOUTH:
			return this.detectionRanges[this.nRangeSouth];
		case EAST:
			return this.detectionRanges[this.nRangeEast];
		case WEST:
			return this.detectionRanges[this.nRangeWest];
		case DOWN:
			return this.detectionRanges[this.nRangeDown];
		case UP:
			return this.detectionRanges[this.nRangeUp];
		default:
			return 0;
		}
	}

	protected int getRangeMax(ForgeDirection dir) {
		switch (dir) {
		case NORTH:
			return this.detectionRangesMax[this.nRangeNorth];
		case SOUTH:
			return this.detectionRangesMax[this.nRangeSouth];
		case EAST:
			return this.detectionRangesMax[this.nRangeEast];
		case WEST:
			return this.detectionRangesMax[this.nRangeWest];
		case DOWN:
			return this.detectionRangesMax[this.nRangeDown];
		case UP:
			return this.detectionRangesMax[this.nRangeUp];
		default:
			return 0;
		}
	}

	protected int setRange(ForgeDirection dir, int nRange) {
		switch (dir) {
		case NORTH:
			this.detectionRanges[this.nRangeNorth] = nRange;
			break;
		case SOUTH:
			this.detectionRanges[this.nRangeSouth] = nRange;
			break;
		case EAST:
			this.detectionRanges[this.nRangeEast] = nRange;
			break;
		case WEST:
			this.detectionRanges[this.nRangeWest] = nRange;
			break;
		case DOWN:
			this.detectionRanges[this.nRangeDown] = nRange;
			break;
		case UP:
			this.detectionRanges[this.nRangeUp] = nRange;
			break;
		default:
			break;
		}
		return nRange;
	}

	public int incRange(ForgeDirection dir) {
		int nMaxRange = this.getRangeMax(dir);
		int nCurRange = this.getRange(dir);
		if (nCurRange + 1 < nMaxRange)
			return this.setRange(dir, nCurRange + 1);
		else
			return this.getRange(dir);
	}

	public int decRange(ForgeDirection dir) {
		int nCurRange = this.getRange(dir);
		if (nCurRange - 1 >= 0)
			return this.setRange(dir, nCurRange - 1);
		else
			return this.getRange(dir);
	}

	@Override
	public void closeChest() {
	}

	@Override
	public void receiveClientEvent(int par1, int par2) {
		// TODO Auto-generated method stub
		super.receiveClientEvent(par1, par2);
		System.out.println(par1+","+ par2);
	}	
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		for (int i = 0; i < this.detectionRanges.length; i++) {
			tagCompound.setShort("Current_ID_" + i,
					(short) this.detectionRanges[i]);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		for (int i = 0; i < this.detectionRanges.length; i++) {
			this.detectionRanges[i] = tagCompound.getShort("Current_ID_" + i);
		}
	}

	
	
	@Override
	public void updateEntity() {
		/*Side side = FMLCommonHandler.instance().getEffectiveSide();
		System.out.println(this.getRange(ForgeDirection.UP)+" " + side);*/
		if(!worldObj.isRemote){
		
		}
	}
}
