package NetherStuffs.Common;

import net.minecraft.src.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid="NetherStuffs",name="NetherStuffs",version="1.0.0")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class mod_NetherStuffs {
	
	public static Block DemonicOre;
	
	@Init
	public void load(FMLInitializationEvent event) {
		DemonicOre = (new DemonicOre(255, 2)).setBlockName("DemonicOre").setHardness(1F).setResistance(5F);
		
		GameRegistry.registerBlock(DemonicOre);
		GameRegistry.registerWorldGenerator(new WorldGeneratorNetherStuffs());
		
		
		LanguageRegistry.addName(DemonicOre, "Demonic Ore");
	}
}
