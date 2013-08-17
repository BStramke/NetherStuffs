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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import bstramke.NetherStuffs.NetherStuffsEventHook;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Common.BlockActiveHelper;
import bstramke.NetherStuffs.Common.BlockNotifyType;

public class TileSoulSiphon extends TileEntity  {
	private static int nTickCounter = 0;
	public int nTankFillSlot = 1;
	public int nTankDrainSlot = 0;

	private ItemStack[] inventory = new ItemStack[2]; // 2 slots for bottles

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

	}

	private boolean isReceivingRedstoneSignal() {
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
		}
	}
}
