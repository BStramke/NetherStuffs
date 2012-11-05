package NetherStuffs.SoulWorkBench;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import NetherStuffs.Items.NetherItems;
import NetherStuffs.Items.SoulEnergyBottle;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class TileSoulWorkBench extends TileEntity implements IInventory, ISidedInventory {
	public static final int nTankFillSlot = 9;
	public static final int nOutputSlot = 10;

	private ItemStack[] inventory = new ItemStack[11]; // 9 Crafting Grid, 1 Output, 1 as "fill source" for internal Tank Level
	private int nTicksToComplete = 400;
	public int currentTankLevel = 0;
	public int processTime = 0;
	public int maxTankLevel = 100;
	public int energyUsedPerTick = 1;

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}

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

	@SideOnly(Side.CLIENT)
	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getProgressScaled(int par1) {
		return (int) (((float) this.processTime / (float) this.nTicksToComplete) * par1);
	}

	@SideOnly(Side.CLIENT)
	public int getFillingScaled(int nPixelMax) {
		return (int) (((float) this.currentTankLevel / (float) this.maxTankLevel) * nPixelMax);
	}

	private void fillFuelToTank() {
		if (!this.worldObj.isRemote) {
			if (this.inventory[this.nTankFillSlot] != null && this.inventory[this.nTankFillSlot].itemID == NetherItems.SoulEnergyBottle.shiftedIndex
					&& this.currentTankLevel < this.maxTankLevel) {
				if (this.currentTankLevel + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]) > this.maxTankLevel) {
					SoulEnergyBottle.setSoulEnergyAmount(this.inventory[this.nTankFillSlot],
							this.currentTankLevel + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]) - this.maxTankLevel);
					this.currentTankLevel = this.maxTankLevel;
				} else {
					this.currentTankLevel += SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]);
					SoulEnergyBottle.setSoulEnergyAmount(this.inventory[this.nTankFillSlot], 0);
				}
			}
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
		return "container.sacirifactionaltar";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			fillFuelToTank();
			this.onInventoryChanged();
		}
	}

	private boolean canProcess() {
		if (this.inventory[nOutputSlot].stackSize + 1 > this.getInventoryStackLimit())
			return false;

		if (this.currentTankLevel == 0 && this.inventory[nTankFillSlot] == null)
			return false;
		/*
		 * if (this.inventory[0] == null) { return false; } else { ItemStack var1 = DemonicFurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]); if (var1 == null) return
		 * false; if (this.inventory[2] == null) return true; if (!this.inventory[2].isItemEqual(var1)) return false; int result = inventory[2].stackSize + var1.stackSize; return
		 * (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize()); }
		 */

		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("TankLevel", (short) this.currentTankLevel);
		tagCompound.setShort("ProcessTime", (short) this.processTime);
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
		this.processTime = tagCompound.getShort("ProcessTime");
	}

	public InventoryCrafting getCraftingInventory() {
		Container tmpContainer = new ContainerSoulWorkBench();
		InventoryCrafting tmpCraftingInventory = new InventoryCrafting(tmpContainer, 3, 3);
		for (int i = 0; i < 9; i++)
			tmpCraftingInventory.setInventorySlotContents(i, this.getStackInSlot(i));
		return tmpCraftingInventory;
	}

	public void onInventoryChanged() {
		this.setInventorySlotContents(this.nOutputSlot, SoulWorkBenchRecipes.getInstance().getCraftingResult(this));
	}
}
