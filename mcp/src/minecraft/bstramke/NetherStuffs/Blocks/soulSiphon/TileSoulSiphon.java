package bstramke.NetherStuffs.Blocks.soulSiphon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffsEventHook;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Common.BlockActiveHelper;
import bstramke.NetherStuffs.Common.BlockNotifyType;
import bstramke.NetherStuffs.Common.SoulEnergyTankTileEntity;
import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import buildcraft.api.inventory.ISpecialInventory;

public class TileSoulSiphon extends SoulEnergyTankTileEntity implements ISpecialInventory, ISidedInventory {
	private static int nTickCounter = 0;
	public int nTankFillSlot = 1;
	public int nTankDrainSlot = 0;

	private ItemStack[] inventory = new ItemStack[2]; // 2 slots for bottles

	public TileSoulSiphon() {
		super(10000);
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

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			fillFuelToTank();

			int nMeta = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if (isReceivingRedstoneSignal()) {
				if (!BlockActiveHelper.isActiveSet(nMeta))
					this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, BlockActiveHelper.setActiveOnMetadata(nMeta), BlockNotifyType.CLIENT_SERVER);

				doSiphoning(BlockActiveHelper.unmarkedMetadata(nMeta));

			} else {
				if (BlockActiveHelper.isActiveSet(nMeta))
					this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, BlockActiveHelper.clearActiveOnMetadata(nMeta), BlockNotifyType.CLIENT_SERVER);
			}

		}
		fillFuelToBottle();
	}

	private boolean isReceivingRedstoneSignal()
	{
		return this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
	
	private void doSiphoning(int nUnmarkedMeta) {
		nTickCounter++;
		if (nTickCounter >= 40) {
			nTickCounter = 0;
			int nRange;
			switch (nUnmarkedMeta) {
			case SoulSiphon.mk1:
				nRange = 4;
				break;
			case SoulSiphon.mk2:
				nRange = 8;
				break;
			case SoulSiphon.mk3:
			case SoulSiphon.mk4:
				nRange = 12;
				break;
			default:
				nRange = 4;
			}
			int nRangeUp = nRange;
			int nRangeDown = nRange;
			int nRangeNorth = nRange;
			int nRangeSouth = nRange;
			int nRangeEast = nRange;
			int nRangeWest = nRange;

			Integer nLowerX = xCoord - nRangeWest;
			Integer nLowerY = yCoord - nRangeDown;
			Integer nLowerZ = zCoord - nRangeNorth;
			Integer nUpperX = xCoord + nRangeEast + 1;
			Integer nUpperY = yCoord + nRangeUp + 1;// height has to be 1 more for upwards detection (detects Head Position)
			Integer nUpperZ = zCoord + nRangeSouth + 1;

			AxisAlignedBB axis = AxisAlignedBB.getAABBPool().getAABB(nLowerX, nLowerY, nLowerZ, nUpperX, nUpperY, nUpperZ);
			List tmp = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, axis);

			List results = new ArrayList(); // this could be a boolean, but maybe we want a count based detection, like, if 5 Pigs...
			Iterator it = tmp.iterator();

			while (it.hasNext()) {
				Object data = it.next();
				if (data instanceof EntityLiving && !(data instanceof EntityPlayerMP) && !(data instanceof EntityPlayer) && !(data instanceof EntityVillager)) {
					((EntityLiving) data).experienceValue = 0;
					if (data instanceof EntityAnimal) {
						((EntityLiving) data).attackEntityFrom(DamageSource.generic, 1);
					} else {
						((EntityLiving) data)
								.attackEntityFrom(new EntityDamageSource("generic", NetherStuffsEventHook.getPlayerDummyForDimension(this.worldObj.provider.dimensionId)), 2);
					}
				} else {
					it.remove();
				}
			}

			if (!tmp.isEmpty()) {
				int nSiphonAmount = tmp.size();
				if (nUnmarkedMeta == SoulSiphon.mk4)
					nSiphonAmount = (int) (nSiphonAmount * 1.25F); // MK4 gets a Bonus on Siphoned amount

				nSiphonAmount *= 10;
				this.addFuelToTank(nSiphonAmount);
			}
		}
	}

	private void fillFuelToBottle() {
		if (this.getCurrentTankLevel() > 0 && this.inventory[this.nTankDrainSlot] != null && this.inventory[this.nTankDrainSlot].itemID == ItemRegistry.SoulEnergyBottle.itemID) {
			int nRest = SoulEnergyBottle.addSoulEnergy(this.getCurrentTankLevel(), this.inventory[this.nTankDrainSlot]);
			this.setCurrentTankLevel(nRest);
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
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
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

		return 0;
	}

	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		if (from != ForgeDirection.DOWN || from == ForgeDirection.UP) {
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return itemstack.itemID == ItemRegistry.SoulEnergyBottle.itemID;
	}

	/**
	 * Get the size of the side inventory.
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		if (par1 == BlockRegistry.sideTop)
			return new int[] { nTankFillSlot };
		else if (par1 == BlockRegistry.sideBottom)
			return new int[] { nTankDrainSlot };
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

		if (side == BlockRegistry.sideTop && slot == nTankFillSlot)
			return true;

		if (side == BlockRegistry.sideBottom && slot == nTankDrainSlot) // bottom gets the output slot
			return true;

		if (side != BlockRegistry.sideTop && slot != BlockRegistry.sideBottom && slot == nTankFillSlot) // enables output from sides of the tankfillslot
			return true;

		return false;
	}
}
