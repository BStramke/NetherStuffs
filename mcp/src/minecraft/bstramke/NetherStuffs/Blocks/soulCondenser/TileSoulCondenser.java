package bstramke.NetherStuffs.Blocks.soulCondenser;


public class TileSoulCondenser{}/* extends SoulEnergyTankBottleTileEntity implements ISpecialInventory, ISidedInventory {
	private static int nTickCounter = 0;

	public static int nToHarkenScytheRate = 250;
	public static int nFromHarkenScytheRate = 150;

	private ItemStack inventory[] = new ItemStack[2]; // 2 slots for bottles, one is SoulEnergyBottle, one is HarkenScythe Soul Vessel/Keeper

	public TileSoulCondenser() {
		super(1, 0, 10000);
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
		if (getBlockMetadata() == 0)
			return "container.soulcondenser";
		else
			return "container.soulsmelter";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
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

	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			fillFuelToTank();
			fillFuelToBottle();
		}
	}

	@Override
	public int getBlockMetadata() {
		return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public void fillFuelToBottle() {
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
				if (this.getCurrentTankLevel() > 0 && this.inventory[this.nTankDrainSlot] != null && this.inventory[this.nTankDrainSlot].itemID == ItemRegistry.SoulEnergyBottle.itemID) {
					int nRest = SoulEnergyBottle.addSoulEnergy(this.getCurrentTankLevel(), this.inventory[this.nTankDrainSlot]);
					this.setCurrentTankLevel(nRest);
				}
			}
		}
	}

	@Override
	public void fillFuelToTank() {
		if (this.getCurrentTankLevel() >= this.getMaxTankLevel() || this.inventory[this.nTankFillSlot] == null)
			return;

		if (getBlockMetadata() == 0) {
			if (this.inventory[this.nTankFillSlot].itemID == ItemRegistry.SoulEnergyBottle.itemID) {
				if (this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]) > this.getMaxTankLevel()) {
					SoulEnergyBottle.decreaseSoulEnergyAmount(this.inventory[this.nTankFillSlot], this.getMaxTankLevel() - this.getCurrentTankLevel());
					this.setCurrentTankLevel(this.getMaxTankLevel());
				} else {
					this.setCurrentTankLevel(this.getCurrentTankLevel() + SoulEnergyBottle.getSoulEnergyAmount(this.inventory[this.nTankFillSlot]));
					SoulEnergyBottle.setSoulEnergyAmount(this.inventory[this.nTankFillSlot], 0);
				}
			}
		} else if (getBlockMetadata() == 1) {
			if (this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulKeeperItemId || this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulVesselItemId) {
				int nSouls = this.inventory[this.nTankFillSlot].getMaxDamage() - this.inventory[this.nTankFillSlot].getItemDamage();
				for (int i = 1; i < nSouls; i++) {
					if (getCurrentTankLevel() + nFromHarkenScytheRate > getMaxTankLevel())
						break;

					setCurrentTankLevel(getCurrentTankLevel() + nFromHarkenScytheRate);
					this.inventory[this.nTankFillSlot].setItemDamage(this.inventory[this.nTankFillSlot].getItemDamage() + 1);
				}

				if (getCurrentTankLevel() + nFromHarkenScytheRate <= getMaxTankLevel()) {
					setCurrentTankLevel(getCurrentTankLevel() + nFromHarkenScytheRate);
					if (this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulKeeperItemId)
						setInventorySlotContents(this.nTankFillSlot, new ItemStack(SoulCondenser.HSEssenceKeeperItemId, 1, 0));
					else if (this.inventory[this.nTankFillSlot].itemID == SoulCondenser.HSSoulVesselItemId)
						setInventorySlotContents(this.nTankFillSlot, new ItemStack(SoulCondenser.HSEssenceVesselItemId, 1, 0));
				}
			}
		}
	}

	@Override
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
		if (getBlockMetadata() == 0) {
			if (from == ForgeDirection.DOWN || from == ForgeDirection.UP) {
				if (stack.itemID == ItemRegistry.SoulEnergyBottle.itemID && getStackInSlot(this.nTankFillSlot) == null) {
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
				if (stack.itemID == ItemRegistry.SoulEnergyBottle.itemID && getStackInSlot(this.nTankDrainSlot) == null) {
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

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (getBlockMetadata() == 0) {
			if (i == nTankFillSlot) {
				return itemstack.itemID == ItemRegistry.SoulEnergyBottle.itemID;
			} else if (i == nTankDrainSlot) {
				if (itemstack.itemID == SoulCondenser.HSSoulKeeperItemId || itemstack.itemID == SoulCondenser.HSSoulVesselItemId
						|| itemstack.itemID == SoulCondenser.HSEssenceKeeperItemId || itemstack.itemID == SoulCondenser.HSEssenceVesselItemId)
					return true;
			}
		} else if (getBlockMetadata() == 1) {
			if (i == nTankDrainSlot) {
				return itemstack.itemID == ItemRegistry.SoulEnergyBottle.itemID;
			} else if (i == nTankFillSlot) {
				if (itemstack.itemID == SoulCondenser.HSSoulKeeperItemId || itemstack.itemID == SoulCondenser.HSSoulVesselItemId
						|| itemstack.itemID == SoulCondenser.HSEssenceKeeperItemId || itemstack.itemID == SoulCondenser.HSEssenceVesselItemId)
					return true;
			}
		}
		return false;
	}


	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		if (par1 == BlockRegistry.sideTop)
			return new int[] { nTankFillSlot };
		else if (par1 == BlockRegistry.sideBottom)
			return new int[] { nTankDrainSlot };
		else
			return new int[] { nTankFillSlot }; // sides
	}


	@Override
	public boolean canInsertItem(int slot, ItemStack par2ItemStack, int side) {
		return this.isItemValidForSlot(slot, par2ItemStack);
	}


	@Override
	public boolean canExtractItem(int slot, ItemStack par2ItemStack, int side) {

		if (side == BlockRegistry.sideTop && slot == nTankFillSlot)
			return true;

		if (side == BlockRegistry.sideBottom && slot == nTankDrainSlot) // bottom gets the output slot
			return true;

		if (side != BlockRegistry.sideTop && slot != BlockRegistry.sideBottom && slot == nTankFillSlot) // enables output from sides of the tankfillslot
			return true;

		return false;
	}
}*/