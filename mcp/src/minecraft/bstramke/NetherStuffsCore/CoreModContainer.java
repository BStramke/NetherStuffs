package bstramke.NetherStuffsCore;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class CoreModContainer extends DummyModContainer {
	public CoreModContainer() {
		super(new ModMetadata());
		/* ModMetadata is the same as mcmod.info */
		ModMetadata myMeta = super.getMetadata();
		myMeta.authorList = Arrays.asList(new String[] { "BStramke" });
		myMeta.description = "Core Mod for NetherStuffs";
		myMeta.modId = "NetherStuffsCore";
		myMeta.version = "0.11";
		myMeta.name = "NetherStuffsCore";
		myMeta.url = "http://netherstuffs.wikispaces.com/";
	}

	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	/*
	 * Use this in place of @Init, @Preinit, @Postinit in the file.
	 */
	// @Subscribe
	/* Remember to use the right event! */
	/*
	 * public void onServerStarting(FMLServerStartingEvent ev) { // ev.getServer().worldServerForDimension(0).spawnHostileMobs = false;
	 * 
	 * }
	 */
}