package NetherStuffs.SoulDetector;

import java.util.HashMap;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileSoulDetector extends TileEntity implements IInventory {

	private HashMap<String, Integer> detectionRanges = new HashMap<String, Integer>();

	public TileSoulDetector() {
		//initialize the basic Hashmap
		String[] Directions = new String[] { "North", "South", "West", "East", "Down", "Up" };
		for (String dir : Directions) {
			detectionRanges.put(dir, 0);
			detectionRanges.put(dir + "Max", 20);
		}
	}

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
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {}

	protected int getRange(ForgeDirection dir) {
		switch (dir) {
		case NORTH:
			return detectionRanges.get("North");
		case SOUTH:
			return detectionRanges.get("South");
		case EAST:
			return detectionRanges.get("East");
		case WEST:
			return detectionRanges.get("West");
		case DOWN:
			return detectionRanges.get("Down");
		case UP:
			return detectionRanges.get("Up");
		default:
			return 0;
		}
	}
	
	protected int getRangeMax(ForgeDirection dir) {
		switch (dir) {
		case NORTH:
			return detectionRanges.get("NorthMax");
		case SOUTH:
			return detectionRanges.get("SouthMax");
		case EAST:
			return detectionRanges.get("EastMax");
		case WEST:
			return detectionRanges.get("WestMax");
		case DOWN:
			return detectionRanges.get("DownMax");
		case UP:
			return detectionRanges.get("UpMax");
		default:
			return 0;
		}
	}

	@Override
	public void closeChest() {}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		/*
		 * tagCompound.setShort("TankLevel", (short) this.currentTankLevel); tagCompound.setShort("ProcessTime", (short) this.processTime); NBTTagList itemList = new NBTTagList();
		 * 
		 * for (int i = 0; i < inventory.length; i++) { if (this.inventory[i] != null) { NBTTagCompound tag = new NBTTagCompound();
		 * 
		 * tag.setByte("Slot", (byte) i); this.inventory[i].writeToNBT(tag); itemList.appendTag(tag); } } tagCompound.setTag("Inventory", itemList);
		 */
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		/*
		 * NBTTagList tagList = tagCompound.getTagList("Inventory");
		 * 
		 * for (int i = 0; i < tagList.tagCount(); i++) { NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
		 * 
		 * byte slot = tag.getByte("Slot");
		 * 
		 * if (slot >= 0 && slot < inventory.length) { inventory[slot] = ItemStack.loadItemStackFromNBT(tag); } }
		 * 
		 * this.currentTankLevel = tagCompound.getShort("TankLevel"); this.processTime = tagCompound.getShort("ProcessTime");
		 */
	}
}
