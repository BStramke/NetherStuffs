package bstramke.NetherStuffs.Blocks.soulWorkBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Items.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSoulWorkBench extends TileEntity implements ISidedInventory  {
	public static final int nOutputSlot = 9;

	private ItemStack[] inventory = new ItemStack[11]; // 9 Crafting Grid, 1
	// Output, 1 as
	// "fill source" for
	// internal Tank Level
	// public int currentTankLevel = 0;
	
	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return this.inventory[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {
		if (this.inventory[slotIndex] != null) {
			ItemStack var3;

			if (this.inventory[slotIndex].stackSize <= amount) {
				var3 = this.inventory[slotIndex];
				this.inventory[slotIndex] = null;
				return var3;
			} else {
				var3 = this.inventory[slotIndex].splitStack(amount);

				if (this.inventory[slotIndex].stackSize == 0) {
					this.inventory[slotIndex] = null;
				}

				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		if (this.inventory[slotIndex] != null) {
			ItemStack var2 = this.inventory[slotIndex];
			this.inventory[slotIndex] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "container.soulworkbench";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	private boolean canProcess() {
		if (this.inventory[nOutputSlot].stackSize + 1 > this.getInventoryStackLimit())
			return false;

		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			if (this.inventory[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	public InventoryCrafting getCraftingInventory() {
		Container tmpContainer = new ContainerSoulWorkBench();
		InventoryCrafting tmpCraftingInventory = new InventoryCrafting(tmpContainer, 3, 3);
		for (int i = 0; i < 9; i++)
			tmpCraftingInventory.setInventorySlotContents(i, this.getStackInSlot(i));
		return tmpCraftingInventory;
	}

	@Override
	public void onInventoryChanged() {
		ItemStack tmpStack = SoulWorkBenchRecipes.getInstance().getCraftingResult(this);
		this.setInventorySlotContents(this.nOutputSlot, tmpStack);
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		switch (i) {
		case nOutputSlot:
			return false;
		default:
			return true;
		}
	}
	
	/**
	 * Get the size of the side inventory.
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		if (par1 == BlockRegistry.sideTop || par1 == BlockRegistry.sideBottom)
			return new int[] { 0,1,2,3,4,5,6,7,8 }; //crafting grid
		else
			return new int[] { nOutputSlot };
	}

	/**
	 * Description : Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean canInsertItem(int slot, ItemStack par2ItemStack, int side) {
		return this.isItemValidForSlot(slot, par2ItemStack);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean canExtractItem(int slot, ItemStack par2ItemStack, int side) {
		
		if((side == BlockRegistry.sideTop || side == BlockRegistry.sideBottom) && slot >= 0 && slot <= 8)
			return true;
		
		if(!(side == BlockRegistry.sideTop || side == BlockRegistry.sideBottom) && slot == nOutputSlot) //sides get the output slot
			return true;
		
		
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {		
	}

	@Override
	public void closeChest() {		
	}
}
