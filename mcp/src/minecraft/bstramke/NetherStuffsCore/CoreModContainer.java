package bstramke.NetherStuffsCore;

import java.io.File;
import java.util.Arrays;

import net.minecraftforge.common.Configuration;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CoreModContainer extends DummyModContainer {
	public static int NetherSkyBlockId;
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
	
	public void PreLoad(FMLPreInitializationEvent event) {
		FMLLog.info("[NetherStuffsCore] PreLoad");	
		
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory() + "NetherStuffs.cfg"));
		config.load();
		NetherSkyBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SkyBlock", 1245).getInt(1245);
		config.save();	
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