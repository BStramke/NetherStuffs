package bstramke.NetherStuffs.Client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import bstramke.NetherStuffs.SoulDetector.TileSoulDetector;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(payload.data));
		EntityPlayer sender = (EntityPlayer) player;

		try {
			int nType = data.readShort();
			if (nType == 1) {
				processSoulDetectorRange(data, sender);
			} else if(nType == 3) {
				processSoulDetectorDetectionSettings(data, sender);
			} else if(nType == 4) {
				processSoulDetectorMobDetectionSettings(data, sender);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processSoulDetectorDetectionSettings(DataInputStream data, EntityPlayer player) {
		try {
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();

			TileEntity tile_entity = player.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulDetector) {
				for (int i = 0; i < ((TileSoulDetector) tile_entity).detectEntities.length; i++)
					((TileSoulDetector) tile_entity).detectEntities[i] = data.readBoolean();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processSoulDetectorMobDetectionSettings(DataInputStream data, EntityPlayer player) {
		try {
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();
			
			TileEntity tile_entity = player.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulDetector) {
				for (int i = 0; i < ((TileSoulDetector) tile_entity).detectEntitiesMobs.length; i++)
					((TileSoulDetector) tile_entity).detectEntitiesMobs[i] = data.readBoolean();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processSoulDetectorRange(DataInputStream data, EntityPlayer player) {
		try {
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();
			
			TileEntity tile_entity = player.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulDetector) {
				for (int i = 0; i < ((TileSoulDetector) tile_entity).detectionRanges.length; i++)
					((TileSoulDetector) tile_entity).detectionRanges[i] = data.readShort();
				for (int i = 0; i < ((TileSoulDetector) tile_entity).detectionRangesMax.length; i++)
					((TileSoulDetector) tile_entity).detectionRangesMax[i] = data.readShort();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}