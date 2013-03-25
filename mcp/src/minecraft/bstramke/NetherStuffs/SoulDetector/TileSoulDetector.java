package bstramke.NetherStuffs.SoulDetector;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Blocks.SoulDetector;
import bstramke.NetherStuffs.Client.ClientPacketHandler;
import bstramke.NetherStuffs.Common.ServerPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSoulDetector extends TileEntity implements IInventory {

	public static final int nRangeNorth = 0;
	public static final int nRangeSouth = 1;
	public static final int nRangeWest = 2;
	public static final int nRangeEast = 3;
	public static final int nRangeDown = 4;
	public static final int nRangeUp = 5;

	public static final int nDetectEverything = 0;
	public static final int nDetectHostile = 1;
	public static final int nDetectNonHostile = 2;

	public static final int nDetectPig = 0;
	public static final int nDetectSheep = 1;
	public static final int nDetectCow = 2;
	public static final int nDetectChicken = 3;
	public static final int nDetectSquid = 4;
	public static final int nDetectMooshroom = 5;
	public static final int nDetectVillager = 6;
	public static final int nDetectOcelot = 7;
	public static final int nDetectBat = 8;
	public static final int nDetectWolfTameable = 9;
	public static final int nDetectIronGolem = 10;
	public static final int nDetectSnowGolem = 11;
	public static final int nDetectCreeper = 12;
	public static final int nDetectZombie = 13;
	public static final int nDetectSpider = 14;
	public static final int nDetectWither = 15;
	public static final int nDetectSkeleton = 16;
	public static final int nDetectWolfAggressive = 17;
	public static final int nDetectSilverfish = 18;
	public static final int nDetectEnderman = 19;
	public static final int nDetectSlime = 20;
	public static final int nDetectGhast = 21;
	public static final int nDetectPigZombie = 22;
	public static final int nDetectWitherSkeleton = 23;
	public static final int nDetectMagmaCube = 24;
	public static final int nDetectWitherSkeletonJockey = 25;
	public static final int nDetectBlaze = 26;
	public static final int nDetectWitch = 27;
	public static final int nDetectZombieVillager = 28;
	public static final int nDetectSkeletonJockey = 29;
	public static final int nDetectEnderDragon = 30;

	public short[] detectionRanges = new short[] { 0, 0, 0, 0, 0, 0 };
	public short[] detectionRangesMax = new short[] { 13, 13, 13, 13, 13, 13 };
	public boolean[] detectEntities = new boolean[] { true, false, false };
	public boolean[] detectEntitiesMobs = new boolean[] { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false };

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
	}

	@Override
	public String getInvName() {
		return "container.souldetector";
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {}

	public int getRange(ForgeDirection dir) {
		switch (dir) {
		case NORTH:
			return this.detectionRanges[this.nRangeNorth];
		case SOUTH:
			return this.detectionRanges[this.nRangeSouth];
		case EAST:
			return this.detectionRanges[this.nRangeEast];
		case WEST:
			return this.detectionRanges[this.nRangeWest];
		case DOWN:
			return this.detectionRanges[this.nRangeDown];
		case UP:
			return this.detectionRanges[this.nRangeUp];
		default:
			return 0;
		}
	}

	public int getRangeMax(ForgeDirection dir) {
		int nMeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		short nMaxRange = 12;
		if ((nMeta & 7) == SoulDetector.mk1)
			nMaxRange = 5;
		else if ((nMeta & 7) == SoulDetector.mk2)
			nMaxRange = 9;
		else if ((nMeta & 7) == SoulDetector.mk3 || (nMeta & 7) == SoulDetector.mk4)
			nMaxRange = 13;

		for (int i = 0; i < 6; i++)
			detectionRangesMax[i] = nMaxRange;

		switch (dir) {
		case NORTH:
			return this.detectionRangesMax[this.nRangeNorth];
		case SOUTH:
			return this.detectionRangesMax[this.nRangeSouth];
		case EAST:
			return this.detectionRangesMax[this.nRangeEast];
		case WEST:
			return this.detectionRangesMax[this.nRangeWest];
		case DOWN:
			return this.detectionRangesMax[this.nRangeDown];
		case UP:
			return this.detectionRangesMax[this.nRangeUp];
		default:
			return 0;
		}
	}

	public int setRange(ForgeDirection dir, int nRange) {
		switch (dir) {
		case NORTH:
			this.detectionRanges[this.nRangeNorth] = (short) nRange;
			break;
		case SOUTH:
			this.detectionRanges[this.nRangeSouth] = (short) nRange;
			break;
		case EAST:
			this.detectionRanges[this.nRangeEast] = (short) nRange;
			break;
		case WEST:
			this.detectionRanges[this.nRangeWest] = (short) nRange;
			break;
		case DOWN:
			this.detectionRanges[this.nRangeDown] = (short) nRange;
			break;
		case UP:
			this.detectionRanges[this.nRangeUp] = (short) nRange;
			break;
		default:
			break;
		}

		// this.worldObj.notifyBlockChange(xCoord, yCoord, zCoord, NetherBlocks.NetherSoulDetector.blockID);
		// this.onInventoryChanged();
		return nRange;
	}

	public int incRange(ForgeDirection dir) {
		int nMaxRange = this.getRangeMax(dir);
		int nCurRange = this.getRange(dir);
		int nRetRange = nCurRange;
		if (nCurRange + 1 < nMaxRange)
			nRetRange = this.setRange(dir, nCurRange + 1);

		if (this.worldObj.isRemote) {
			sendToServer(nRetRange, dir);
		}
		return nRetRange;
	}

	public int decRange(ForgeDirection dir) {
		int nCurRange = this.getRange(dir);
		int nRetRange = nCurRange;
		if (nCurRange - 1 >= 0)
			nRetRange = this.setRange(dir, nCurRange - 1);

		if (this.worldObj.isRemote) {
			sendToServer(nRetRange, dir);
		}
		return nRetRange;
	}

	public boolean setDetectEverything(boolean bActive) {
		detectEntities[this.nDetectEverything] = bActive;
		if (this.worldObj.isRemote) {
			sendDetectToServer();
		}
		return detectEntities[this.nDetectEverything];
	}

	public boolean setDetectHostile(boolean bActive) {
		detectEntities[this.nDetectHostile] = bActive;
		if (this.worldObj.isRemote) {
			sendDetectToServer();
		}
		return detectEntities[this.nDetectHostile];
	}

	public boolean setDetectNonHostile(boolean bActive) {
		detectEntities[this.nDetectNonHostile] = bActive;
		if (this.worldObj.isRemote) {
			sendDetectToServer();
		}
		return detectEntities[this.nDetectNonHostile];
	}

	public boolean setDetectMob(int i, boolean bActive) {
		detectEntitiesMobs[i] = bActive;
		if (this.worldObj.isRemote) {
			sendDetectMobsToServer();
		}
		return detectEntitiesMobs[i];
	}

	@SideOnly(Side.CLIENT)
	private void sendDetectMobsToServer() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeShort(ServerPacketHandler.PacketType.SoulDetectorMobDetectionSettings.getValue());
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);

			for (int i = 0; i < this.detectEntitiesMobs.length; i++)
				outputStream.writeBoolean(this.detectEntitiesMobs[i]);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	@SideOnly(Side.CLIENT)
	private void sendDetectToServer() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeShort(ServerPacketHandler.PacketType.SoulDetectionSettings.getValue());
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);

			for (int i = 0; i < this.detectEntities.length; i++)
				outputStream.writeBoolean(this.detectEntities[i]);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	@SideOnly(Side.CLIENT)
	private void sendToServer(int nRange, ForgeDirection dir) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		int nDirection = -1;
		switch (dir) {
		case NORTH:
			nDirection = this.nRangeNorth;
			break;
		case SOUTH:
			nDirection = this.nRangeSouth;
			break;
		case EAST:
			nDirection = this.nRangeEast;
			break;
		case WEST:
			nDirection = this.nRangeWest;
			break;
		case DOWN:
			nDirection = this.nRangeDown;
			break;
		case UP:
			nDirection = this.nRangeUp;
			break;
		default:
			nDirection = -1;
		}

		try {
			outputStream.writeShort(ServerPacketHandler.PacketType.SoulDetectorRange.getValue());
			outputStream.writeShort(nRange);
			outputStream.writeShort(nDirection);
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	public void sendToClient(EntityPlayer player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeShort(ClientPacketHandler.PacketType.SoulDetectorRange.getValue());
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);
			for (int i = 0; i < detectionRanges.length; i++)
				outputStream.writeShort(detectionRanges[i]);
			for (int i = 0; i < detectionRangesMax.length; i++)
				outputStream.writeShort(detectionRangesMax[i]);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
	}

	public void sendDetectToClient(EntityPlayer player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeShort(ClientPacketHandler.PacketType.SoulDetectionSettings.getValue());
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);
			for (int i = 0; i < this.detectEntities.length; i++)
				outputStream.writeBoolean(this.detectEntities[i]);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
	}
	
	public void sendDetectMobsToClient(EntityPlayer player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeShort(ClientPacketHandler.PacketType.SoulDetectorMobDetectionSettings.getValue());
			outputStream.writeInt(this.xCoord);
			outputStream.writeInt(this.yCoord);
			outputStream.writeInt(this.zCoord);
			for (int i = 0; i < this.detectEntitiesMobs.length; i++)
				outputStream.writeBoolean(this.detectEntitiesMobs[i]);

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
	public void closeChest() {}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		for (int i = 0; i < this.detectionRanges.length; i++) {
			tagCompound.setShort("Current_ID_" + i, (short) this.detectionRanges[i]);
		}
		for (int i = 0; i < this.detectEntities.length; i++) {
			tagCompound.setBoolean("Detect_ID_" + i, this.detectEntities[i]);
		}
		for (int i = 0; i < this.detectEntitiesMobs.length; i++) {
			tagCompound.setBoolean("Detect_Mob_ID_" + i, this.detectEntitiesMobs[i]);
		}

		// System.out.println("written");
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		for (int i = 0; i < this.detectionRanges.length; i++) {
			this.detectionRanges[i] = tagCompound.getShort("Current_ID_" + i);
		}
		for (int i = 0; i < this.detectEntities.length; i++) {
			this.detectEntities[i] = tagCompound.getBoolean("Detect_ID_" + i);
		}
		for (int i = 0; i < this.detectEntitiesMobs.length; i++) {
			this.detectEntitiesMobs[i] = tagCompound.getBoolean("Detect_Mob_ID_" + i);
		}
		// System.out.println("read");
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {

		}
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
}
