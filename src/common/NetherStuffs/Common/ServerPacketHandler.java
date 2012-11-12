package NetherStuffs.Common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import NetherStuffs.SoulDetector.TileSoulDetector;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(payload.data));
		EntityPlayer sender = (EntityPlayer) player;

		try {
			int nType = data.readShort();
			if (nType == 1) {
				processSoulDetectorRange(data, sender);
			} else if (nType == 2) {
				processSoulDetectorRangeQuery(data, sender);
			} else if (nType == 3) {
				processSoulDetectorDetectionSettings(data, sender);
			}else if (nType == 4) {
				processSoulDetectorMobDetectionSettings(data, sender);
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
				for(int i = 0; i < ((TileSoulDetector) tile_entity).detectEntities.length; i++)
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
				for(int i = 0; i < ((TileSoulDetector) tile_entity).detectEntitiesMobs.length; i++)
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