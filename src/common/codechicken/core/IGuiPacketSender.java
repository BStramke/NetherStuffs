package codechicken.core;

import net.minecraft.src.EntityPlayerMP;

public interface IGuiPacketSender
{

	void sendPacket(EntityPlayerMP player, int windowId);

}
