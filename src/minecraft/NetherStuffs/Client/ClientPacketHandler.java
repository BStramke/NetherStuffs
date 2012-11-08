package NetherStuffs.Client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import NetherStuffs.SoulDetector.TileSoulDetector;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.WorldProvider;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(payload.data));
		EntityPlayer sender = (EntityPlayer) player;

		try {
			int nType = data.readShort();
			System.out.println(nType);
			if (nType == 1) {
				processSoulDetectorRange(data, sender);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void processSoulDetectorRange(DataInputStream data, EntityPlayer player) {
		try {
			int nRange = data.readShort();
			int nDirection = data.readShort();
			int xCoord = data.readInt();
			int yCoord = data.readInt();
			int zCoord = data.readInt();

			if (nRange >= 0 && nDirection >= 0) {
				TileEntity tile_entity = player.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
				if (tile_entity instanceof TileSoulDetector) {

					for (int i = 0; i < ((TileSoulDetector) tile_entity).detectionRanges.length; i++)
						((TileSoulDetector) tile_entity).detectionRanges[i] = data.readShort();
					for (int i = 0; i < ((TileSoulDetector) tile_entity).detectionRangesMax.length; i++)
						((TileSoulDetector) tile_entity).detectionRangesMax[i] = data.readShort();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
