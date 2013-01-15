package bstramke.NetherStuffs.Common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class PlayerDummy extends EntityPlayer {

	public PlayerDummy(World par1World) {
		super(par1World);
		this.username = "PlayerDummy";
	}
	
	@Override
	public void sendChatToPlayer(String var1) {}

	@Override
	public boolean canCommandSenderUseCommand(int var1, String var2) {
		return false;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return new ChunkCoordinates(0, 0, 0);
	}
	
	@Override
	public boolean isPlayerSleeping() {
		return true;
	}
}
