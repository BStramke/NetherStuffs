package NetherStuffs.DemonicFurnace;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import NetherStuffs.Blocks.NetherDemonicFurnace;
import NetherStuffs.Blocks.NetherWood;
import NetherStuffs.Blocks.NetherWoodItemBlock;
import NetherStuffs.Items.NetherWoodCharcoal;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class TileDemonicFurnace extends TileEntity implements IInventory, ISidedInventory {

	private ItemStack[] inventory = new ItemStack[3];
	
	private int nFurnaceTicksToComplete = 400;
	
	/** The number of ticks that the furnace will keep burning */
	public int furnaceBurnTime = 0;

	/**
	 * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
	 */
	public int currentItemBurnTime = 0;

	/** The number of ticks that the current item has been cooking for */
	public int furnaceCookTime = 0;

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
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
		return this.furnaceCookTime * par1 / nFurnaceTicksToComplete;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	 * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	 */
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = nFurnaceTicksToComplete;
		}

		return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
	}

	/**
	 * Returns true if the furnace is currently burning
	 */
	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	public void updateEntity() {
		boolean var1 = this.furnaceBurnTime > 0;
		boolean var2 = false;

		if (this.furnaceBurnTime > 0) {
			--this.furnaceBurnTime;
		}

		if (!this.worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.canSmelt()) {
				this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.inventory[1]);

				if (this.furnaceBurnTime > 0) {
					var2 = true;

					if (this.inventory[1] != null) {
						--this.inventory[1].stackSize;

						if (this.inventory[1].stackSize == 0) {
							this.inventory[1] = this.inventory[1].getItem().getContainerItemStack(inventory[1]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt()) {
				++this.furnaceCookTime;

				if (this.furnaceCookTime == nFurnaceTicksToComplete) {
					this.furnaceCookTime = 0;
					this.smeltItem();
					var2 = true;
				}
			} else {
				this.furnaceCookTime = 0;
			}

			if (var1 != this.furnaceBurnTime > 0) {
				var2 = true;
				int metadata = NetherDemonicFurnace.unmarkedMetadata(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
				if (this.furnaceBurnTime > 0)
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, NetherDemonicFurnace.setActiveOnMetadata(metadata));
				else
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, NetherDemonicFurnace.clearActiveOnMetadata(metadata));
			}
		}

		if (var2) {
			this.onInventoryChanged();
		}
	}

	/**
	 * Return true if item is a fuel source (getItemBurnTime() > 0).
	 */
	public static boolean isItemFuel(ItemStack par0ItemStack) {
		return getItemBurnTime(par0ItemStack) > 0;
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

		this.furnaceBurnTime = tagCompound.getShort("BurnTime");
		this.furnaceCookTime = tagCompound.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.inventory[1]);
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack var1 = DemonicFurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);

			if (this.inventory[2] == null) {
				this.inventory[2] = var1.copy();
			} else if (this.inventory[2].isItemEqual(var1)) {
				inventory[2].stackSize += var1.stackSize;
			}

			--this.inventory[0].stackSize;

			if (this.inventory[0].stackSize <= 0) {
				this.inventory[0] = null;
			}
		}
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't fuel
	 */
	public static int getItemBurnTime(ItemStack par0ItemStack) {
		if (par0ItemStack == null) {
			return 0;
		} else {
			int itemId = par0ItemStack.getItem().shiftedIndex;
			int nMeta = par0ItemStack.getItemDamage();

			if (par0ItemStack.getItem() instanceof NetherWoodItemBlock) {
				if (par0ItemStack.getItemDamage() == NetherWood.hellfire)
					return 2400;
				else
					return 1600;
			}
			
			if(par0ItemStack.getItem() instanceof NetherWoodCharcoal){
				return 6400;
			}

			return 0;

			/*
			 * if (var3 == Block.woodSingleSlab) { return 150; }
			 * 
			 * if (var3.blockMaterial == Material.wood) { return 300; } } if (var2 instanceof ItemTool && ((ItemTool) var2).func_77861_e().equals("WOOD")) return 200; if (var2
			 * instanceof ItemSword && ((ItemSword) var2).func_77825_f().equals("WOOD")) return 200; if (var2 instanceof ItemHoe && ((ItemHoe) var2).func_77842_f().equals("WOOD"))
			 * return 200; if (var1 == Item.stick.shiftedIndex) return 100; if (var1 == Item.coal.shiftedIndex) return 1600; if (var1 == Item.bucketLava.shiftedIndex) return 20000; if
			 * (var1 == Block.sapling.blockID) return 100; if (var1 == Item.blazeRod.shiftedIndex) return 2400; return GameRegistry.getFuelValue(par0ItemStack);
			 */

		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt() {
		if (this.inventory[0] == null) {
			return false;
		} else {
			ItemStack var1 = DemonicFurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
			if (var1 == null)
				return false;
			if (this.inventory[2] == null)
				return true;
			if (!this.inventory[2].isItemEqual(var1))
				return false;
			int result = inventory[2].stackSize + var1.stackSize;
			return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
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

		tagCompound.setTag("Items", itemList);
	}

	@Override
	public String getInvName() {
		return "container.demonicfurnace";
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.DOWN)
			return 1;
		if (side == ForgeDirection.UP)
			return 0;
		return 2;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}
}