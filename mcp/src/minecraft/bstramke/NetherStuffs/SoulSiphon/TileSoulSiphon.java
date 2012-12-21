package bstramke.NetherStuffs.SoulSiphon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.inventory.ISpecialInventory;

public class TileSoulSiphon extends TileEntity implements ISpecialInventory {
	private int nTankFillSlot = 0;
	public int currentTankLevel = 0;
	public int maxTankLevel = 1000;

	private ItemStack[] inventory = new ItemStack[1]; // 1 slot for bottle

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
		return "container.soulsiphon";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
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

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("TankLevel", (short) this.currentTankLevel);
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

		this.currentTankLevel = tagCompound.getShort("TankLevel");
	}

	public void updateEntity() {
		if (!this.worldObj.isRemote) {

		}
		fillFuelToBottle();
	}

	private void fillFuelToBottle() {
		if (this.currentTankLevel > 0 && this.inventory[this.nTankFillSlot] != null && this.inventory[this.nTankFillSlot].itemID == NetherItems.SoulEnergyBottle.shiftedIndex) {
			int nRest = SoulEnergyBottle.addSoulEnergy(this.currentTankLevel, this.inventory[this.nTankFillSlot]);
			this.currentTankLevel = nRest;
		}
	}
	
	public int addFuelToTank(int nAmount){
		int nRest = 0;
		this.currentTankLevel += nAmount;
		if(this.currentTankLevel > this.maxTankLevel){
			nRest = this.currentTankLevel - this.maxTankLevel;
			this.currentTankLevel = this.maxTankLevel;
		}
		return nRest;
	}

	@Override
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
		int nTargetSlot = 0;
		// every Soul Energy Bottle may go to the TankFillSlot
		if (stack.itemID == NetherItems.SoulEnergyBottle.shiftedIndex && getStackInSlot(nTankFillSlot) == null) {
			nTargetSlot = nTankFillSlot;
			ItemStack targetStack = getStackInSlot(nTargetSlot);
			if (targetStack == null) {
				if (doAdd) {
					targetStack = stack.copy();
					setInventorySlotContents(nTargetSlot, targetStack);
				}
				return stack.stackSize;
			}
		}

		return 0;
	}

	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		if (getStackInSlot(this.nTankFillSlot) != null) {
			ItemStack outputStack = getStackInSlot(this.nTankFillSlot).copy();
			outputStack.stackSize = 1;
			if (doRemove)
				decrStackSize(this.nTankFillSlot, 1);
			return new ItemStack[] { outputStack };
		}
		return null;
	}

	public int getFillingScaled(int nPixelMax) {
		return (int) (((float) this.currentTankLevel / (float) this.maxTankLevel) * nPixelMax);
	}
}
