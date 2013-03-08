package bstramke.NetherStuffs.Common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Client.ClientPacketHandler.PacketType;
import bstramke.NetherStuffs.NetherWoodPuddle.TileNetherWoodPuddle;
import bstramke.NetherStuffs.SoulDetector.TileSoulDetector;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {
	public enum PacketType {
		SoulDetectorRange((short) 1), SoulDetectorRangeQuery((short) 2), SoulDetectionSettings((short) 3), SoulDetectorMobDetectionSettings((short) 4), NetherWoodPuddleSizeQuery((short) 6);

		private short value;

		private PacketType(short value) {
			this.value = value;
		}

		public short getValue() {
			return value;
		}

		public static PacketType getEnumByValue(short value) {
			for (PacketType packet : PacketType.values()) {
				if (packet.getValue() == value) {
					return packet;
				}
			}
			return null;
		}
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(payload.data));
		EntityPlayer sender = (EntityPlayer) player;

		try {
			short nType = data.readShort();
			switch (PacketType.getEnumByValue(nType)) {
			case SoulDetectorRange:
				processSoulDetectorRange(data, sender);
				break;
			case SoulDetectorRangeQuery:
				processSoulDetectorRangeQuery(data, sender);
				break;
			case SoulDetectionSettings:
				processSoulDetectorDetectionSettings(data, sender);
				break;
			case SoulDetectorMobDetectionSettings:
				processSoulDetectorMobDetectionSettings(data, sender);
				break;
			case NetherWoodPuddleSizeQuery:
				processNetherWoodPuddleSizeQuery(data, sender);
				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processNetherWoodPuddleSizeQuery(DataInputStream data, EntityPlayer sender) {
		try {
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();
			TileEntity tile_entity = sender.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileNetherWoodPuddle) {
				((TileNetherWoodPuddle)tile_entity).sendPuddleSizeToClient(sender);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processSoulDetectorDetectionSettings(DataInputStream data, EntityPlayer sender) {
		try {
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();
			TileEntity tile_entity = sender.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulDetector) {
				for (int i = 0; i < ((TileSoulDetector) tile_entity).detectEntities.length; i++)
					((TileSoulDetector) tile_entity).detectEntities[i] = data.readBoolean();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processSoulDetectorMobDetectionSettings(DataInputStream data, EntityPlayer sender) {
		try {
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();
			TileEntity tile_entity = sender.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulDetector) {
				for (int i = 0; i < ((TileSoulDetector) tile_entity).detectEntitiesMobs.length; i++)
					((TileSoulDetector) tile_entity).detectEntitiesMobs[i] = data.readBoolean();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processSoulDetectorRangeQuery(DataInputStream data, EntityPlayer sender) {
		try {
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();
			TileEntity tile_entity = sender.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulDetector) {
				((TileSoulDetector) tile_entity).sendToClient(sender);
				((TileSoulDetector) tile_entity).sendDetectToClient(sender);
				((TileSoulDetector) tile_entity).sendDetectMobsToClient(sender);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processSoulDetectorRange(DataInputStream data, EntityPlayer sender) {
		try {
			int nRange = data.readShort();
			int nDirection = data.readShort();
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();

			if (nRange >= 0 && nDirection >= 0) {

				TileEntity tile_entity = sender.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
				if (tile_entity instanceof TileSoulDetector) {
					ForgeDirection dir = ForgeDirection.UNKNOWN;
					switch (nDirection) {
					case TileSoulDetector.nRangeDown:
						dir = ForgeDirection.DOWN;
						break;
					case TileSoulDetector.nRangeUp:
						dir = ForgeDirection.UP;
						break;
					case TileSoulDetector.nRangeNorth:
						dir = ForgeDirection.NORTH;
						break;
					case TileSoulDetector.nRangeSouth:
						dir = ForgeDirection.SOUTH;
						break;
					case TileSoulDetector.nRangeEast:
						dir = ForgeDirection.EAST;
						break;
					case TileSoulDetector.nRangeWest:
						dir = ForgeDirection.WEST;
						break;
					}
					if (dir != ForgeDirection.UNKNOWN) {
						((TileSoulDetector) tile_entity).setRange(dir, nRange);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}