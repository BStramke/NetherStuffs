package NetherStuffs.SoulFurnace;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import NetherStuffs.Blocks.NetherSoulFurnace;
import NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import NetherStuffs.Items.NetherItems;
import NetherStuffs.Items.SoulEnergyBottle;
import NetherStuffs.SoulWorkBench.SoulWorkBenchRecipes;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class TileSoulFurnace extends TileEntity implements IInventory, ISidedInventory {
	public static final int nTankFillSlot = 1;
	public static final int nOutputSlot = 2;
	private ItemStack[] inventory = new ItemStack[3];

	private int nTicksToComplete = 200;
	public int currentTankLevel = 0;
	public int maxTankLevel = 1280;

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

	@SideOnly(Side.CLIENT)
	public int getFillingScaled(int nPixelMax) {
		return (int) (((float) this.currentTankLevel / (float) this.maxTankLevel) * nPixelMax);
	}

	private void fillFuelToTank() {
		if (this.currentTankLevel < this.maxTankLevel && this.inventory[this.nTankFillSlot] != null && this.inventory[this.nTankFillSlot].itemID == NetherItems.SoulEnergyBottle.shiftedIndex) {
			if (this.currentTankLevel + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]) > this.maxTankLevel) {
				SoulEnergyBottle.setSoulEnergyAmount(this.inventory[this.nTankFillSlot], this.currentTankLevel + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot])
						- this.maxTankLevel);
				this.currentTankLevel = this.maxTankLevel;
			} else {
				this.currentTankLevel += SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]);
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public void updateEntity() {
		boolean bActive = this.furnaceCookTime > 0;
		boolean var2 = false;
		int nPrevTankLevel = this.currentTankLevel;

		if (!this.worldObj.isRemote) {

			fillFuelToTank();

			if (this.currentTankLevel != nPrevTankLevel) {
				var2 = true;
			}

			// check if smelting is to be done and has energy to do so
			if (this.canSmelt() && this.currentTankLevel > 0) {
				++this.furnaceCookTime;

				if (this.furnaceCookTime % 10 == 0) // every 10 Ticks needs 1 energy, 20 energy for each smelting
					this.currentTankLevel--;

				if (this.furnaceCookTime == nTicksToComplete) {
					this.furnaceCookTime = 0;
					this.smeltItem();
					var2 = true;
				}

				if (this.currentTankLevel != nPrevTankLevel) {
					var2 = true;
				}
			} else {
				this.furnaceCookTime = 0; // basically its ran out of fuel or something in the inventory changed
			}

			if (bActive != this.furnaceCookTime > 0) {
				var2 = true;
				int metadata = NetherSoulFurnace.unmarkedMetadata(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
				if (this.furnaceCookTime > 0)
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, NetherSoulFurnace.setActiveOnMetadata(metadata));
				else
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, NetherSoulFurnace.clearActiveOnMetadata(metadata));
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
		this.currentTankLevel = tagCompound.getShort("TankLevel");
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack var1 = DemonicFurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
			if (var1 == null)
				var1 = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);

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
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt() {
		if (this.inventory[0] == null) {
			return false;
		} else {
			ItemStack var1 = DemonicFurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
			if (var1 == null)
				var1 = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
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
		tagCompound.setShort("TankLevel", (short) this.currentTankLevel);
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
