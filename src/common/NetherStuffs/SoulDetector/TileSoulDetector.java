package NetherStuffs.SoulDetector;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;
import NetherStuffs.Blocks.SoulDetector;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class TileSoulDetector extends TileEntity implements IInventory {

	public static final int nRangeNorth = 0;
	public static final int nRangeSouth = 1;
	public static final int nRangeWest = 2;
	public static final int nRangeEast = 3;
	public static final int nRangeDown = 4;
	public static final int nRangeUp = 5;

	public short[] detectionRanges = new short[] { 0, 0, 0, 0, 0, 0 };
	public short[] detectionRangesMax = new short[] { 12, 12, 12, 12, 12, 12 };

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		// TODO Auto-generated method stub

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
			nMaxRange = 4;
		else if ((nMeta & 7) == SoulDetector.mk2)
			nMaxRange = 8;
		else if ((nMeta & 7) == SoulDetector.mk3 || (nMeta & 7) == SoulDetector.mk4)
			nMaxRange = 12;

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
			outputStream.writeShort(1);
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
			outputStream.writeShort(1);
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

	@Override
	public void closeChest() {}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		for (int i = 0; i < this.detectionRanges.length; i++) {
			tagCompound.setShort("Current_ID_" + i, (short) this.detectionRanges[i]);
		}
		// System.out.println("written");
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		for (int i = 0; i < this.detectionRanges.length; i++) {
			this.detectionRanges[i] = tagCompound.getShort("Current_ID_" + i);

		}
		// System.out.println("read");
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {

		}
	}
}
