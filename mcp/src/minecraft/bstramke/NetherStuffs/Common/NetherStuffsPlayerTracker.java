package bstramke.NetherStuffs.Common;

import bstramke.NetherStuffs.NetherStuffs;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.Loader;

public class NetherStuffsPlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		if (!Loader.isModLoaded("NetherStuffsCore")) {
			player.addChatMessage("You don't have NetherStuffsCore installed, you may encounter graphic glitches with Glass/GlassPanes");
		}
		if(NetherStuffs.DevSetCoreModAvailable) {
			player.addChatMessage("DEV ENVIRONMENT SET COREMOD IS ENABLED!!!!");
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
	}

}
