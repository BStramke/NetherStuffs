package bstramke.NetherStuffs.Blocks.soulFurnace;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.demonicFurnace.DemonicFurnaceRecipes;
import bstramke.NetherStuffs.Common.BlockActiveHelper;
import bstramke.NetherStuffs.Common.BlockNotifyType;
import bstramke.NetherStuffs.Common.SoulEnergyTankTileEntity;
import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.inventory.ISpecialInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSoulFurnace extends SoulEnergyTankTileEntity implements ISpecialInventory, ISidedInventory {
	public static final int nSmeltedSlot = 0;
	public static final int nTankFillSlot = 1;
	public static final int nOutputSlot = 2;
	private ItemStack[] inventory = new ItemStack[3];

	private int nTicksToComplete = 195;

	/** The number of ticks that the current item has been cooking for */
	public int furnaceCookTime = 0;

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	public TileSoulFurnace() {
		super(19200); // 19200 equals 2 stacks of Smelting Ores
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return this.inventory[slotIndex];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void fillFuelToTank() {
		if (this.getCurrentTankLevel() < this.getMaxTankLevel() && this.inventory[this.nTankFillSlot] != null
				&& this.inventory[this.nTankFillSlot].itemID == ItemRegistry.SoulEnergyBottle.itemID) {
			if (this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]) > this.getMaxTankLevel()) {
				SoulEnergyBottle.decreaseSoulEnergyAmount(this.inventory[this.nTankFillSlot], this.getMaxTankLevel() - this.getCurrentTankLevel());
				this.setCurrentTankLevel(this.getMaxTankLevel());
			} else {
				this.setCurrentTankLevel(this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]));
				SoulEnergyBottle.setSoulEnergyAmount(this.inventory[this.nTankFillSlot], 0);
			}
		}
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
	public int getCookProgressScaled(int par1) {
		return this.furnaceCookTime * par1 / nTicksToComplete;
	}

	/**
	 * Returns true if the furnace is currently burning
	 */
	public boolean isSmelting() {
		return this.furnaceCookTime > 0;
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
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void updateEntity() {
		boolean bActive = this.furnaceCookTime > 0;
		boolean var2 = false;
		int nPrevTankLevel = this.getCurrentTankLevel();

		if (!this.worldObj.isRemote) {

			fillFuelToTank();

			if (this.getCurrentTankLevel() != nPrevTankLevel) {
				var2 = true;
			}

			// check if smelting is to be done and has energy to do so
			if (this.canSmelt() && this.getCurrentTankLevel() > 10) {
				++this.furnaceCookTime;

				if (this.furnaceCookTime % 13 == 0) // every 13 Ticks needs 10 energy, 15 energy for each smelting
					this.setCurrentTankLevel(this.getCurrentTankLevel() - 10);

				if (this.furnaceCookTime == nTicksToComplete) {
					this.furnaceCookTime = 0;
					this.smeltItem();
					var2 = true;
				}

				if (this.getCurrentTankLevel() != nPrevTankLevel) {
					var2 = true;
				}
			} else {
				this.furnaceCookTime = 0; // basically its ran out of fuel or something in the inventory changed
			}

			if (bActive != this.furnaceCookTime > 0) {
				var2 = true;
				int metadata = BlockActiveHelper.unmarkedMetadata(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
				if (this.furnaceCookTime > 0)
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, BlockActiveHelper.setActiveOnMetadata(metadata), BlockNotifyType.ALL);
				else
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, BlockActiveHelper.clearActiveOnMetadata(metadata), BlockNotifyType.ALL);
			}
		}

		if (var2) {
			this.onInventoryChanged();
		}
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

		this.furnaceCookTime = tagCompound.getShort("CookTime");
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack var1 = DemonicFurnaceRecipes.smelting().getSmeltingResult(this.inventory[nSmeltedSlot]);
			if (var1 == null)
				var1 = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[nSmeltedSlot]);

			if (this.inventory[nOutputSlot] == null) {
				this.inventory[nOutputSlot] = var1.copy();
			} else if (this.inventory[nOutputSlot].isItemEqual(var1)) {
				inventory[nOutputSlot].stackSize += var1.stackSize;
			}

			--this.inventory[nSmeltedSlot].stackSize;

			if (this.inventory[nSmeltedSlot].stackSize <= 0) {
				this.inventory[nSmeltedSlot] = null;
			}
		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt() {
		if (this.inventory[0] == null) {
			return false;
		} else {
			ItemStack var1 = DemonicFurnaceRecipes.smelting().getSmeltingResult(this.inventory[nSmeltedSlot]);
			if (var1 == null)
				var1 = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[nSmeltedSlot]);
			if (var1 == null)
				return false;
			if (this.inventory[nOutputSlot] == null)
				return true;
			if (!this.inventory[nOutputSlot].isItemEqual(var1))
				return false;
			int result = inventory[nOutputSlot].stackSize + var1.stackSize;
			return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("CookTime", (short) this.furnaceCookTime);
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
	public String getInvName() {
		return "container.soulfurnace";
	}

	@Override
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
		int nTargetSlot = 0;
		// every Soul Energy Bottle may go to the TankFillSlot, every Item that
		// has a recipe will go to the Smelted Slot, regardless of input Side
		if (stack.itemID == ItemRegistry.SoulEnergyBottle.itemID && getStackInSlot(nTankFillSlot) == null)
			nTargetSlot = nTankFillSlot;
		else if (DemonicFurnaceRecipes.smelting().getSmeltingResult(stack) != null || FurnaceRecipes.smelting().getSmeltingResult(stack) != null)
			nTargetSlot = nSmeltedSlot;
		else
			return 0;

		ItemStack targetStack = getStackInSlot(nTargetSlot);
		if (targetStack == null) {
			if (doAdd) {
				targetStack = stack.copy();
				setInventorySlotContents(nTargetSlot, targetStack);
			}
			return stack.stackSize;
		}

		if (!targetStack.isItemEqual(stack))
			return 0;

		int nFreeStackSize = this.getInventoryStackLimit() - targetStack.stackSize;
		if (nFreeStackSize >= stack.stackSize) {
			if (doAdd)
				targetStack.stackSize += stack.stackSize;
			return stack.stackSize;
		} else {
			if (doAdd)
				targetStack.stackSize = getInventoryStackLimit();
			return nFreeStackSize;
		}
	}

	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		if (from == ForgeDirection.UP && getStackInSlot(this.nSmeltedSlot) != null) {
			ItemStack outputStack = getStackInSlot(this.nSmeltedSlot).copy();
			outputStack.stackSize = 1;
			if (doRemove)
				decrStackSize(this.nSmeltedSlot, 1);
			return new ItemStack[] { outputStack };
		} else if (from == ForgeDirection.DOWN && getStackInSlot(this.nTankFillSlot) != null) {
			ItemStack outputStack = getStackInSlot(this.nTankFillSlot).copy();
			outputStack.stackSize = 1;
			if (doRemove)
				decrStackSize(this.nTankFillSlot, 1);
			return new ItemStack[] { outputStack };
		} else if (from != ForgeDirection.UP && from != ForgeDirection.DOWN && getStackInSlot(this.nOutputSlot) != null) {
			ItemStack outputStack = this.getStackInSlot(this.nOutputSlot);
			outputStack = outputStack.copy();
			outputStack.stackSize = 1;
			if (doRemove)
				decrStackSize(this.nOutputSlot, 1);
			return new ItemStack[] { outputStack };
		} else
			return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		switch (i) {
		case nSmeltedSlot:
			return (DemonicFurnaceRecipes.smelting().getSmeltingResult(itemstack) != null || FurnaceRecipes.smelting().getSmeltingResult(itemstack) != null);
		case nTankFillSlot:
			if (itemstack.itemID == ItemRegistry.SoulEnergyBottle.itemID) {
				if (SoulEnergyBottle.getSoulEnergyAmount(itemstack) > 0) // only accept bottles that have an amount of energy in them
					return true;
				else
					return false;
			}
		}

		return false;
	}

	/**
	 * Get the size of the side inventory.
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		if (par1 == BlockRegistry.sideTop)
			return new int[] { nSmeltedSlot };
		else if (par1 == BlockRegistry.sideBottom)
			return new int[] { nOutputSlot };
		else
			return new int[] { nTankFillSlot }; // sides
	}

	/**
	 * Description : Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean canInsertItem(int slot, ItemStack par2ItemStack, int side) {
		return this.isStackValidForSlot(slot, par2ItemStack);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean canExtractItem(int slot, ItemStack par2ItemStack, int side) {
		if (par2ItemStack.itemID == ItemRegistry.SoulEnergyBottle.itemID) {
			if (SoulEnergyBottle.getSoulEnergyAmount(par2ItemStack) == 0)
				return true; // empty bottle is extractable always
			else
				return false; // partially filled bottle is not extractable
		}

		if (side == BlockRegistry.sideTop && slot == nSmeltedSlot)
			return true;

		if (side == BlockRegistry.sideBottom && slot == nOutputSlot) // bottom gets the output slot
			return true;

		if (side != BlockRegistry.sideTop && slot != BlockRegistry.sideBottom && slot == nTankFillSlot) // enables output from sides of the tankfillslot
			return true;

		return false;
	}
}
