package bstramke.NetherStuffs.NetherWoodPuddle;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Blocks.NetherWood;
import bstramke.NetherStuffs.Client.ClientPacketHandler;
import bstramke.NetherStuffs.Client.ClientPacketHandler.PacketType;

public class TileNetherWoodPuddle extends TileEntity {

	private ForgeDirection puddleSide = ForgeDirection.UNKNOWN;
	public short puddleSize = -1;

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

	private void setPuddleDirection(Random rand) {
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
		this.worldObj.setBlockMetadata(xCoord, yCoord, zCoord, meta);
	}

	public boolean harvestPuddle() {
		if (hasPuddle() == false)
			return false;

		if (puddleSize < maxPuddleSize)
			return false;

		resetPuddleSize();
		return true;
	}

	private void incPuddleSize() {
		puddleSize++;
		sendPuddleSizeToClients();
	}

	private void resetPuddleSize() {
		puddleSize = 0;
		sendPuddleSizeToClients();
	}

	/**
	 * Tries to Grow a puddle
	 */
	public void growPuddle(Random rand) {
		if (this.worldObj.isRemote)
			return;
		setPuddleDirection(rand);

		if (this.puddleSize > 0) // just update if its not the default size. clients always get default size by metadata
			sendPuddleSizeToClients();

		if (canGrowPuddle() == false)
			return;

		incPuddleSize();
	}

	public void sendPuddleSizeToClients() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeShort(ClientPacketHandler.PacketType.NetherWoodPuddleSize.getValue());
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);
			outputStream.writeShort(this.puddleSize);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 32, worldObj.provider.dimensionId, packet);
	}
	
	public void sendPuddleSizeToClient(EntityPlayer player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeShort(ClientPacketHandler.PacketType.NetherWoodPuddleSize.getValue());
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);
			outputStream.writeShort(this.puddleSize);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
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

		if (zCoord <= -94 && zCoord >= -98 && yCoord == 55 && xCoord > 40 && xCoord < 50)
			System.out.println(puddleSide);
		puddleSize = tagCompound.getShort("puddleSize");
	}
}
