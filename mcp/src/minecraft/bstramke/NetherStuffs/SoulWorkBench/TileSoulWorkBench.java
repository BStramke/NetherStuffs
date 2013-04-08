package bstramke.NetherStuffs.SoulWorkBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
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
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.inventory.ISpecialInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSoulWorkBench extends TileEntity implements ISpecialInventory, ITankContainer, ISidedInventory  {
	private LiquidTank tank;
	public static final int nTankFillSlot = 9;
	public static final int nOutputSlot = 10;

	private ItemStack[] inventory = new ItemStack[11]; // 9 Crafting Grid, 1
	// Output, 1 as
	// "fill source" for
	// internal Tank Level
	private int nTicksToComplete = 400;
	// public int currentTankLevel = 0;
	public int processTime = 0;
	public int maxTankLevel = 1000;
	public int energyUsedPerTick = 1;
	public int nSoulEnergyRequired = 0;

	public TileSoulWorkBench() {
		tank = new LiquidTank(maxTankLevel);
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
		return (int) (((float) this.getCurrentTankLevel() / (float) this.maxTankLevel) * nPixelMax);
	}

	public boolean hasEnoughFuel(ItemStack item) {
		return this.getCurrentTankLevel() >= SoulWorkBenchRecipes.getInstance().getCraftingSoulEnergyRequired(item);
	}

	public boolean consumeFuelFromTank(ItemStack item) {
		return consumeFuelFromTank(SoulWorkBenchRecipes.getInstance().getCraftingSoulEnergyRequired(item));
	}

	public boolean consumeFuelFromTank(int nConsumedFuel) {
		if (this.getCurrentTankLevel() - nConsumedFuel >= 0)
			this.setCurrentTankLevel(this.getCurrentTankLevel() - nConsumedFuel);
		else
			return false;
		return true;
	}

	private void fillFuelToTank() {
		if (this.inventory[this.nTankFillSlot] != null && this.inventory[this.nTankFillSlot].itemID == NetherItems.SoulEnergyBottle.itemID
				&& this.getCurrentTankLevel() < this.maxTankLevel) {
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

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	public void updateEntity() {
		if (!this.worldObj.isRemote) {

		}
		fillFuelToTank();
	}

	private boolean canProcess() {
		if (this.inventory[nOutputSlot].stackSize + 1 > this.getInventoryStackLimit())
			return false;

		if (this.getCurrentTankLevel() == 0 && this.inventory[nTankFillSlot] == null)
			return false;

		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("TankLevelNew", (short) this.getCurrentTankLevel());
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

		if (tagCompound.getShort("TankLevel") > 0) {
			tagCompound.setShort("TankLevelNew", (short) (tagCompound.getShort("TankLevel") * 10));
			tagCompound.removeTag("TankLevel");
		}
		this.setCurrentTankLevel(tagCompound.getShort("TankLevelNew"));
		this.processTime = tagCompound.getShort("ProcessTime");
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
		this.nSoulEnergyRequired = SoulWorkBenchRecipes.getInstance().nSoulEnergyRequired;
		if (tmpStack == null)
			this.nSoulEnergyRequired = 0;
		this.setInventorySlotContents(this.nOutputSlot, tmpStack);
	}

	public int getSoulEnergyRequired() {
		return this.nSoulEnergyRequired;
	}

	@Override
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
		int nTargetSlot = 0;
		// every Soul Energy Bottle may go to the TankFillSlot, other items go to the Inventory
		if (stack.itemID == NetherItems.SoulEnergyBottle.itemID && getStackInSlot(nTankFillSlot) == null) {
			nTargetSlot = nTankFillSlot;
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
		} else {

			ItemStack[] targetStacks = new ItemStack[] { this.getStackInSlot(0), this.getStackInSlot(1), this.getStackInSlot(2), this.getStackInSlot(3), this.getStackInSlot(4),
					this.getStackInSlot(5), this.getStackInSlot(6), this.getStackInSlot(7), this.getStackInSlot(8) };
			int nUsedAmount = 0;
			for (int nStackSize = stack.stackSize; nStackSize > 0; nStackSize--) {
				int nTargetInputSlot = -1;
				int nLeastStackSize = getInventoryStackLimit() + 1;
				int nLeastStackIndex = -1;
				int nFirstEmptyStackIndex = -1;

				for (int i = 0; i < 9; i++) {
					if (targetStacks[i] != null && targetStacks[i].isItemEqual(stack) && targetStacks[i].stackSize < getInventoryStackLimit()) {
						if (targetStacks[i].stackSize < nLeastStackSize) {
							nLeastStackSize = targetStacks[i].stackSize;
							nLeastStackIndex = i;
						}
					} else if (targetStacks[i] == null) {
						nFirstEmptyStackIndex = i;
					}
				}

				if (nLeastStackIndex >= 0) {
					if (doAdd) {
						targetStacks[nLeastStackIndex].stackSize++;
					}
					nUsedAmount++;
				} else if (nFirstEmptyStackIndex >= 0) {
					if (doAdd) {
						ItemStack tmp = stack.copy();
						tmp.stackSize = 1;
						setInventorySlotContents(nFirstEmptyStackIndex, tmp);
						targetStacks[nFirstEmptyStackIndex] = tmp;
					}
					nUsedAmount++;
				} else
					return nUsedAmount; // basically this happens when all slots are filled, doing return to shortcut Execution for large Stacks

			}
			return nUsedAmount;
		}
	}

	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		if (from == ForgeDirection.UP) {
			for (int i = 0; i < 9; i++) {
				if (this.getStackInSlot(i) == null)
					continue;

				ItemStack outputStack = this.getStackInSlot(i).copy();
				if (this.getStackInSlot(i) != null) {
					outputStack.stackSize = 1;
					if (doRemove)
						decrStackSize(i, 1);
					return new ItemStack[] { outputStack };
				}
			}
			return null;
		} else if (from == ForgeDirection.DOWN && getStackInSlot(this.nTankFillSlot) != null) {
			ItemStack outputStack = getStackInSlot(this.nTankFillSlot).copy();
			outputStack.stackSize = 1;
			if (doRemove)
				decrStackSize(this.nTankFillSlot, 1);
			return new ItemStack[] { outputStack };
		} else if (from != ForgeDirection.UP && from != ForgeDirection.DOWN/* && getStackInSlot(this.nOutputSlot) != null/* && this.nSoulEnergyRequired <= this.currentTankLevel */) {
			ItemStack outputStack = SoulWorkBenchRecipes.getInstance().getCraftingResult(this);
			if (outputStack == null || !hasEnoughFuel(outputStack))
				return null;
			else {
				if (doRemove) {
					consumeFuelFromTank(outputStack);
					for (int i = 0; i < 9; i++)
						decrStackSize(i, 1);
					onInventoryChanged();
				}
				return new ItemStack[] { outputStack };
			}
		} else
			return null;
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
		return drain(0, maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		if (tankIndex == 0)
			return tank.drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[] { tank };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		if (type.isLiquidEqual(NetherStuffs.SoulEnergyLiquid))
			return tank;
		else
			return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		switch (i) {
		case nOutputSlot:
			return false;
		case nTankFillSlot:
			return itemstack.itemID == NetherItems.SoulEnergyBottle.itemID;
		default:
			return true;
		}
	}
	
	/**
	 * Get the size of the side inventory.
	 */
	@Override
	public int[] getSizeInventorySide(int par1) {
		if (par1 == NetherBlocks.sideTop)
			return new int[] { 0,1,2,3,4,5,6,7,8 }; //crafting grid
		else if (par1 == NetherBlocks.sideBottom)
			return new int[] { nOutputSlot };
		else
			return new int[] { nTankFillSlot }; //sides
	}

	/**
	 * Description : Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean func_102007_a(int slot, ItemStack par2ItemStack, int side) {
		return this.isStackValidForSlot(slot, par2ItemStack);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean func_102008_b(int slot, ItemStack par2ItemStack, int side) {
		
		if(side == NetherBlocks.sideTop && slot >= 0 && slot <= 8)
			return true;
		
		if(side == NetherBlocks.sideBottom && slot == nOutputSlot) //bottom gets the output slot
			return true;
		
		if(side != NetherBlocks.sideTop && slot != NetherBlocks.sideBottom && slot == nTankFillSlot) //enables output from sides of the tankfillslot
			return true;
		
		return false;
	}
}
