package NetherStuffs;

import net.minecraft.src.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid="NetherStuffs",name="NetherStuffs",version="1.0.0")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class NetherStuffs {
	@Instance
	public static NetherStuffs instance = new NetherStuffs();
	
	public static Block DemonicOre;
	
	@Init
	public void load(FMLInitializationEvent event) {
		DemonicOre = (new DemonicOre(255, 2)).setBlockName("DemonicOre").setHardness(1F).setResistance(5F);
		
		GameRegistry.registerBlock(DemonicOre);		
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(DemonicOre.blockID, 10));
		
		LanguageRegistry.addName(DemonicOre, "Demonic Ore");
	}
}
