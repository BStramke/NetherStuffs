package bstramke.NetherStuffs.SoulSiphon;

import net.minecraft.entity.player.EntityPlayer;
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
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import bstramke.NetherStuffs.SoulLiquid.SoulEnergyLiquid;
import buildcraft.api.inventory.ISpecialInventory;

public class TileSoulSiphon extends TileEntity implements ISpecialInventory, ITankContainer {
	private LiquidTank tank;
	private int nTankFillSlot = 0;
	// public int currentTankLevel = 0;
	public int maxTankLevel = 10000;

	private ItemStack[] inventory = new ItemStack[1]; // 1 slot for bottle

	public TileSoulSiphon() {
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
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("TankLevelNew", (short) this.getCurrentTankLevel());
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

		if(tagCompound.getShort("TankLevel")>0) {
			tagCompound.setShort("TankLevelNew", tagCompound.getShort("TankLevel"));
			tagCompound.removeTag("TankLevel");
		}
		setCurrentTankLevel(tagCompound.getShort("TankLevelNew"));
	}

	public void updateEntity() {
		if (!this.worldObj.isRemote) {

		}
		fillFuelToBottle();
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

	private void fillFuelToBottle() {
		if (this.getCurrentTankLevel() > 0 && this.inventory[this.nTankFillSlot] != null && this.inventory[this.nTankFillSlot].itemID == NetherItems.SoulEnergyBottle.shiftedIndex) {
			int nRest = SoulEnergyBottle.addSoulEnergy(this.getCurrentTankLevel(), this.inventory[this.nTankFillSlot]);
			this.setCurrentTankLevel(nRest);
		}
	}

	public int addFuelToTank(int nAmount) {
		int nRest = 0;
		LiquidStack liquid = NetherStuffs.SoulEnergyLiquid.copy();
		liquid.amount = nAmount;

		if (tank.getLiquid() == null || tank.getLiquid().amount <= 0)
			nRest = tank.fill(liquid, true);
		else
			nRest = tank.fill(liquid, true);

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
		return (int) (((float) this.getCurrentTankLevel() / (float) this.maxTankLevel) * nPixelMax);
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
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
		return null;
	}
}
