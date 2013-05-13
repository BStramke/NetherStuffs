package bstramke.NetherStuffs.Blocks.puddles;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Blocks.Wood;
import bstramke.NetherStuffs.Client.ClientPacketHandler;
import bstramke.NetherStuffs.Client.ClientPacketHandler.PacketType;
import bstramke.NetherStuffs.Common.BlockNotifyType;

public class TileNetherWoodPuddle extends TileEntity {

	private ForgeDirection puddleSide = ForgeDirection.UNKNOWN;
	public short puddleSize = 0;

	private static short maxPuddleSize = 4;
	
	private boolean canGrowPuddle() {
		if (puddleSize >= maxPuddleSize)
			return false;

		return hasPuddle();
	}

	public ForgeDirection getPuddleSide() {
		return this.puddleSide;
	}

	/**
	 * checks if it has a puddle available
	 */
	public boolean hasPuddle() {
		if (puddleSide == ForgeDirection.UNKNOWN)
			setPuddleDirection(this.worldObj.rand);
		return true;
	}

	public void setPuddleDirection(Random rand) {
		if (this.worldObj.isRemote)
			return;

		if (puddleSide != ForgeDirection.UNKNOWN)
			return;

		int side = rand.nextInt(4);
		int meta = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;
		switch (side) {
		case 0:
			puddleSide = ForgeDirection.NORTH;
			break;
		case 1:
			puddleSide = ForgeDirection.SOUTH;
			meta = meta | 4;
			break;
		case 2:
			puddleSide = ForgeDirection.WEST;
			meta = meta | 8;
			break;
		case 3:
			puddleSide = ForgeDirection.EAST;
			meta = meta | 12;
			break;
		}
		this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, BlockNotifyType.ALL);
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean harvestPuddle() {		
		if (hasPuddle() == false)
			return false;

		if (puddleSize < maxPuddleSize)
			return false;

		puddleSize = 0;
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return true;
	}

	private void incPuddleSize() {
		puddleSize++;
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	/**
	 * Tries to Grow a puddle
	 */
	public void growPuddle(Random rand) {	
		setPuddleDirection(rand);

		if (canGrowPuddle() == false)
			return;

		puddleSize++;
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		switch (puddleSide) {
		case NORTH:
			tagCompound.setShort("puddleSide", (short) 0);
			break;
		case EAST:
			tagCompound.setShort("puddleSide", (short) 1);
			break;
		case SOUTH:
			tagCompound.setShort("puddleSide", (short) 2);
			break;
		case WEST:
			tagCompound.setShort("puddleSide", (short) 3);
			break;
		default:
			tagCompound.setShort("puddleSide", (short) 4);
			break;
		}
		tagCompound.setShort("puddleSize", puddleSize);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		switch (tagCompound.getShort("puddleSide")) {
		case 0:
			puddleSide = ForgeDirection.NORTH;
			break;
		case 1:
			puddleSide = ForgeDirection.EAST;
			break;
		case 2:
			puddleSide = ForgeDirection.SOUTH;
			break;
		case 3:
			puddleSide = ForgeDirection.WEST;
			break;
		default:
			puddleSide = ForgeDirection.UNKNOWN;
			break;
		}

		puddleSize = tagCompound.getShort("puddleSize");
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.customParam1);
	};
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, var1);
	}
}
