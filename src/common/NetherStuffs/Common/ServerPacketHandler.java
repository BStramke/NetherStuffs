package NetherStuffs.Common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload payload, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				payload.data));
		EntityPlayer sender = (EntityPlayer) player;
		int var1 = 0;
		int var2 = 0;
		int var3 = 0;
		try {
			var1 = data.readInt();
			var2 = data.readInt();
			var3 = data.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(var1 + "," + var2 + "," + var3);
	}
}