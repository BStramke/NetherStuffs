package bstramke.NetherStuffs.SoulCondenser;

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
import bstramke.NetherStuffs.Blocks.SoulCondenser;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.inventory.ISpecialInventory;

public class TileSoulCondenser extends TileEntity implements ISpecialInventory, ITankContainer {
	private static int nTickCounter = 0;
	private LiquidTank tank;
	// public int currentTankLevel = 0;
	public int maxTankLevel = 10000;
	public static final int nTankFillSlot = 1;
	public static final int nTankDrainSlot = 0;

	public static int nToHarkenScytheRate = 250;
	public static int nFromHarkenScytheRate = 150;
	
	private ItemStack inventory[] = new ItemStack[2]; // 2 slots for bottles, one is SoulEnergyBottle, one is HarkenScythe Soul Vessel/Keeper

	public TileSoulCondenser() {
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
		if(getBlockMetadata() == 0)
			return "container.soulcondenser";
		else
			return "container.soulsmelter";
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

		if (tagCompound.getShort("TankLevel") > 0) {
			tagCompound.setShort("TankLevelNew", tagCompound.getShort("TankLevel"));
			tagCompound.removeTag("TankLevel");
		}
		setCurrentTankLevel(tagCompound.getShort("TankLevelNew"));

	}

	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			fillFuelToTank();
			fillFuelToBottle();
		}	
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
	public int getBlockMetadata() {
		return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
	}
	
	private void fillFuelToBottle() {
		if (this.inventory[this.nTankDrainSlot] != null) {
			if (getBlockMetadata() == 0) {
				if (inventory[nTankDrainSlot].itemID != SoulCondenser.HSEssenceKeeperItemId && inventory[nTankDrainSlot].itemID != SoulCondenser.HSEssenceVesselItemId
						&& inventory[nTankDrainSlot].itemID != SoulCondenser.HSSoulKeeperItemId && inventory[nTankDrainSlot].itemID != SoulCondenser.HSSoulVesselItemId)
					return;

				int nMaxSoulAmount = inventory[this.nTankDrainSlot].getMaxDamage();
				int nSoulAmountSpace = inventory[this.nTankDrainSlot].getItemDamage(); // it stores the space left for souls
				boolean bContainsSouls = this.inventory[this.nTankDrainSlot].itemID == SoulCondenser.HSSoulKeeperItemId
						|| this.inventory[this.nTankDrainSlot].itemID == SoulCondenser.HSSoulVesselItemId;

				if (bContainsSouls) {
					if (nSoulAmountSpace == 0)
						return;

					for (int i = nSoulAmountSpace - 1; i >= 0; i--) {
						if (getCurrentTankLevel() >= nToHarkenScytheRate) {
							inventory[this.nTankDrainSlot].setItemDamage(i);
							setCurrentTankLevel(getCurrentTankLevel() - nToHarkenScytheRate);
						} else
							break;
					}

				} else {
					if (getCurrentTankLevel() >= nToHarkenScytheRate) {
						if (inventory[this.nTankDrainSlot].itemID == SoulCondenser.HSEssenceKeeperItemId)
							setInventorySlotContents(this.nTankDrainSlot, new ItemStack(SoulCondenser.HSSoulKeeperItemId, 1, 19));
						else if (inventory[this.nTankDrainSlot].itemID == SoulCondenser.HSEssenceVesselItemId)
							setInventorySlotContents(this.nTankDrainSlot, new ItemStack(SoulCondenser.HSSoulVesselItemId, 1, 39));
						else
							return;

						setCurrentTankLevel(getCurrentTankLevel() - nToHarkenScytheRate);
					}
				}
			} else if (getBlockMetadata() == 1) {
				if (this.getCurrentTankLevel() > 0 && this.inventory[this.nTankDrainSlot] != null && this.inventory[this.nTankDrainSlot].itemID == NetherItems.SoulEnergyBottle.itemID) {
					int nRest = SoulEnergyBottle.addSoulEnergy(this.getCurrentTankLevel(), this.inventory[this.nTankDrainSlot]);
					this.setCurrentTankLevel(nRest);
				}
			}
		}
	}

	private void fillFuelToTank() {
		if (this.getCurrentTankLevel() >= this.maxTankLevel || this.inventory[this.nTankFillSlot] == null)
			return;

		if (getBlockMetadata() == 0) {
			if (this.inventory[this.nTankFillSlot].itemID == NetherItems.SoulEnergyBottle.itemID) {
				if (this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]) > this.maxTankLevel) {
					SoulEnergyBottle.decreaseSoulEnergyAmount(this.inventory[this.nTankFillSlot], this.maxTankLevel - this.getCurrentTankLevel());
					this.setCurrentTankLevel(this.maxTankLevel);
				} else {
					this.setCurrentTankLevel(this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]));
					SoulEnergyBottle.setSoulEnergyAmount(this.inventory[this.nTankFillSlot], 0);
				}
			}
		} else if (getBlockMetadata() == 1) {
			if (this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulKeeperItemId || this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulVesselItemId) {
				int nSouls = this.inventory[this.nTankFillSlot].getMaxDamage() - this.inventory[this.nTankFillSlot].getItemDamage();
				for (int i = 1; i < nSouls; i++) {
					if (getCurrentTankLevel() + nFromHarkenScytheRate > maxTankLevel)
						break;

					setCurrentTankLevel(getCurrentTankLevel() + nFromHarkenScytheRate);
					this.inventory[this.nTankFillSlot].setItemDamage(this.inventory[this.nTankFillSlot].getItemDamage() + 1);
				}

				if (getCurrentTankLevel() + nFromHarkenScytheRate <= maxTankLevel) {
					setCurrentTankLevel(getCurrentTankLevel() + nFromHarkenScytheRate);
					if (this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulKeeperItemId)
						setInventorySlotContents(this.nTankFillSlot, new ItemStack(SoulCondenser.HSEssenceKeeperItemId, 1, 0));
					else if (this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulVesselItemId)
						setInventorySlotContents(this.nTankFillSlot, new ItemStack(SoulCondenser.HSEssenceVesselItemId, 1, 0));
				}
			}
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
		if (getBlockMetadata() == 0) {
			if (from == ForgeDirection.DOWN || from == ForgeDirection.UP) {
				if (stack.itemID == NetherItems.SoulEnergyBottle.itemID && getStackInSlot(this.nTankFillSlot) == null) {
					ItemStack targetStack = getStackInSlot(this.nTankFillSlot);
					if (targetStack == null) {
						if (doAdd) {
							targetStack = stack.copy();
							setInventorySlotContents(this.nTankFillSlot, targetStack);
						}
						return stack.stackSize;
					}
				}
			} else {
				if ((stack.itemID == SoulCondenser.HSSoulKeeperItemId || stack.itemID == SoulCondenser.HSSoulVesselItemId || stack.itemID == SoulCondenser.HSEssenceKeeperItemId || stack.itemID == SoulCondenser.HSEssenceVesselItemId)
						&& getStackInSlot(this.nTankDrainSlot) == null) {
					ItemStack targetStack = getStackInSlot(this.nTankDrainSlot);
					if (targetStack == null) {
						if (doAdd) {
							targetStack = stack.copy();
							setInventorySlotContents(this.nTankDrainSlot, targetStack);
						}
						return stack.stackSize;
					}
				}
			}
		} else if (getBlockMetadata() == 1) {
			if (from == ForgeDirection.DOWN || from == ForgeDirection.UP) {
				if ((stack.itemID == SoulCondenser.HSSoulKeeperItemId || stack.itemID == SoulCondenser.HSSoulVesselItemId || stack.itemID == SoulCondenser.HSEssenceKeeperItemId || stack.itemID == SoulCondenser.HSEssenceVesselItemId)
						&& getStackInSlot(this.nTankFillSlot) == null) {
					ItemStack targetStack = getStackInSlot(this.nTankFillSlot);
					if (targetStack == null) {
						if (doAdd) {
							targetStack = stack.copy();
							setInventorySlotContents(this.nTankFillSlot, targetStack);
						}
						return stack.stackSize;
					}
				}
			} else {
				if (stack.itemID == NetherItems.SoulEnergyBottle.itemID && getStackInSlot(this.nTankDrainSlot) == null) {
					ItemStack targetStack = getStackInSlot(this.nTankDrainSlot);
					if (targetStack == null) {
						if (doAdd) {
							targetStack = stack.copy();
							setInventorySlotContents(this.nTankDrainSlot, targetStack);
						}
						return stack.stackSize;
					}
				}
			}
		}
		return 0;
	}

	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		if (from == ForgeDirection.DOWN || from == ForgeDirection.UP) {
			if (getStackInSlot(this.nTankFillSlot) != null) {
				ItemStack outputStack = getStackInSlot(this.nTankFillSlot).copy();
				outputStack.stackSize = 1;
				if (doRemove)
					decrStackSize(this.nTankFillSlot, 1);
				return new ItemStack[] { outputStack };
			}
		} else {
			if (getStackInSlot(this.nTankDrainSlot) != null) {
				ItemStack outputStack = getStackInSlot(this.nTankDrainSlot).copy();
				outputStack.stackSize = 1;
				if (doRemove)
					decrStackSize(this.nTankDrainSlot, 1);
				return new ItemStack[] { outputStack };
			}
		}
		return null;
	}

	public int getFillingScaled(int nPixelMax) {
		return (int) (((float) this.getCurrentTankLevel() / (float) this.maxTankLevel) * nPixelMax);
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
		if (getBlockMetadata() == 0) {
			switch (i) {
			case nTankFillSlot:
				return itemstack.itemID == NetherItems.SoulEnergyBottle.itemID;
			case nTankDrainSlot:
				if (itemstack.itemID == SoulCondenser.HSSoulKeeperItemId || itemstack.itemID == SoulCondenser.HSSoulVesselItemId
						|| itemstack.itemID == SoulCondenser.HSEssenceKeeperItemId || itemstack.itemID == SoulCondenser.HSEssenceVesselItemId)
					return true;
			}
		} else if (getBlockMetadata() == 1) {
			switch (i) {
			case nTankDrainSlot:
				return itemstack.itemID == NetherItems.SoulEnergyBottle.itemID;
			case nTankFillSlot:
				if (itemstack.itemID == SoulCondenser.HSSoulKeeperItemId || itemstack.itemID == SoulCondenser.HSSoulVesselItemId
						|| itemstack.itemID == SoulCondenser.HSEssenceKeeperItemId || itemstack.itemID == SoulCondenser.HSEssenceVesselItemId)
					return true;
			}
		}
		return false;
	}
}
