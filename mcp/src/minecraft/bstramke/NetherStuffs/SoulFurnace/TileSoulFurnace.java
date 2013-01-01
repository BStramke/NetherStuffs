package bstramke.NetherStuffs.SoulFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.NetherSoulFurnace;
import bstramke.NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.inventory.ISpecialInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSoulFurnace extends TileEntity implements ISpecialInventory, ITankContainer {
	private LiquidTank tank;
	public static final int nSmeltedSlot = 0;
	public static final int nTankFillSlot = 1;
	public static final int nOutputSlot = 2;
	private ItemStack[] inventory = new ItemStack[3];

	private int nTicksToComplete = 195;
	//public int currentTankLevel = 0;
	public int maxTankLevel = 1920; //1920 equals 2 stacks of Smelting Ores

	/** The number of ticks that the current item has been cooking for */
	public int furnaceCookTime = 0;

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	public TileSoulFurnace() {
		tank = new LiquidTank(maxTankLevel);
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
		return (int) (((float) this.getCurrentTankLevel() / (float) this.maxTankLevel) * nPixelMax);
	}
	
	public int getCurrentTankLevel() {
		if (this.tank.getLiquid() == null || this.tank.getLiquid().amount < 0)
			return 0;
		else
			return this.tank.getLiquid().amount;
	}

	public void setCurrentTankLevel(int nAmount) {
		if (this.tank.getLiquid() != null)
			this.tank.getLiquid().amount = nAmount;
		else {
			LiquidStack liquid = NetherStuffs.SoulEnergyLiquid.copy();
			liquid.amount = nAmount;
			this.tank.setLiquid(liquid);
		}

	}

	private void fillFuelToTank() {
		if (this.getCurrentTankLevel() < this.maxTankLevel && this.inventory[this.nTankFillSlot] != null
				&& this.inventory[this.nTankFillSlot].itemID == NetherItems.SoulEnergyBottle.shiftedIndex) {
			if (this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]) > this.maxTankLevel) {
				SoulEnergyBottle.decreaseSoulEnergyAmount(this.inventory[this.nTankFillSlot], this.maxTankLevel - this.getCurrentTankLevel());
				this.setCurrentTankLevel(this.maxTankLevel);
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
		int nPrevTankLevel = this.getCurrentTankLevel();

		if (!this.worldObj.isRemote) {

			fillFuelToTank();

			if (this.getCurrentTankLevel() != nPrevTankLevel) {
				var2 = true;
			}

			// check if smelting is to be done and has energy to do so
			if (this.canSmelt() && this.getCurrentTankLevel() > 0) {
				++this.furnaceCookTime;

				if (this.furnaceCookTime % 13 == 0) // every 13 Ticks needs 1 energy, 15 energy for each smelting
					this.setCurrentTankLevel(this.getCurrentTankLevel()-1);

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
		this.setCurrentTankLevel(tagCompound.getShort("TankLevel"));
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
		tagCompound.setShort("TankLevel", (short) this.getCurrentTankLevel());
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
		// every Soul Energy Bottle may go to the TankFillSlot, every Item that has a recipe will go to the Smelted Slot, regardless of input Side
		if (stack.itemID == NetherItems.SoulEnergyBottle.shiftedIndex && getStackInSlot(nTankFillSlot) == null)
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
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return fill(0, resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		if (tankIndex == 0)
			return tank.fill(resource, doFill);
		return 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[] { tank };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		return null;
	}
}
