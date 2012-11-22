package NetherStuffsCore;

import cpw.mods.fml.common.DummyModContainer;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.src.EntityPlayerMP;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CoreModContainer extends DummyModContainer {
	public CoreModContainer() {
		super(new ModMetadata());
		/* ModMetadata is the same as mcmod.info */
		ModMetadata myMeta = super.getMetadata();
		myMeta.authorList = Arrays.asList(new String[] { "BStramke" });
		myMeta.description = "Core Mod for NetherStuffs";
		myMeta.modId = "NetherStuffsCore";
		myMeta.version = "0.8.1";
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