package NetherStuffs;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.EnumHelper;
import NetherStuffs.Blocks.NetherOre;
import NetherStuffs.Blocks.NetherOreItemBlock;
import NetherStuffs.Blocks.NetherPlank;
import NetherStuffs.Blocks.NetherPlankItemBlock;
import NetherStuffs.Blocks.NetherWood;
import NetherStuffs.Blocks.NetherWoodItemBlock;
import NetherStuffs.Common.CommonProxyNetherStuffs;
import NetherStuffs.Items.NetherOreIngot;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid="NetherStuffs",name="NetherStuffs",version="1.0.0")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class NetherStuffs {
	@Instance
	public static NetherStuffs instance = new NetherStuffs();
	
	static EnumToolMaterial EnumToolMaterialDemonicIngot = EnumHelper.addToolMaterial("DemonicIngot", 2, 400, 6.0F, 6, 15);
	static EnumToolMaterial EnumToolMaterialObsidian = EnumHelper.addToolMaterial("Obsidian", 2, 400, 6.0F, 6, 15);
	
	/*
	 * Todo (Blocks): 
	 * - Demonic Furnace
	 * - Soulglass & Soulglass Panels
	 * - NetherTree Saplings
	 * - NetherStone
	 */
	public static Block NetherOre;
	public static Block NetherWood;
	public static Block NetherPlank;
	
	public static Item NetherOreIngot;
	
	public static int NetherOreBlockId = 230;
	public static int NetherWoodBlockId = 231;
	public static int NetherPlankBlockId = 232;
	public static int NetherOreIngotItemId = 5000;
	
	@SidedProxy(clientSide="NetherStuffs.Client.ClientProxy", serverSide="NetherStuffs.Common.CommonProxyNetherStuffs")
	public static CommonProxyNetherStuffs proxy;
	
	@Init
	public void load(FMLInitializationEvent event) {
		NetherOre = new NetherOre(NetherOreBlockId, 0).setBlockName("NetherOre").setHardness(1F).setResistance(5F);
		NetherWood = new NetherWood(NetherWoodBlockId, 0).setBlockName("NetherWood");
		NetherPlank = new NetherPlank(NetherPlankBlockId, 0).setBlockName("NetherPlank");
		NetherOreIngot = new NetherOreIngot(NetherOreIngotItemId).setItemName("NetherOreIngot").setIconCoord(0,0);
		
		Item.itemsList[NetherOreBlockId] = new NetherOreItemBlock(NetherOreBlockId-256, NetherOre).setItemName("NetherOreItemBlock");
		Item.itemsList[NetherWoodBlockId] = new NetherWoodItemBlock(NetherWoodBlockId-256, NetherWood).setItemName("NetherWoodItemBlock");
		Item.itemsList[NetherPlankBlockId] = new NetherPlankItemBlock(NetherPlankBlockId-256, NetherPlank).setItemName("NetherPlankItemBlock");
		
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherOre.blockID, 0, 10));
		
		initRecipes();
		initLanguageRegistry();
		
		proxy.registerRenderThings();
	}
	
	private void initRecipes() {
		
		FurnaceRecipes.smelting().addSmelting(NetherOre.blockID, 0, new ItemStack(NetherOreIngot,1,0));
	}
	
	private void initLanguageRegistry() {
			
		for(int i = 0; i<NetherOreItemBlock.getMetadataSize();i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherOre."+NetherOreItemBlock.blockNames[i]+".name", NetherOreItemBlock.blockDisplayNames[i]);	
		}
			
		for(int i = 0; i<NetherWoodItemBlock.getMetadataSize();i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherWood."+NetherWoodItemBlock.blockNames[i]+".name", NetherWoodItemBlock.blockDisplayNames[i]);	
		}
		
		for(int i = 0; i<NetherPlankItemBlock.getMetadataSize();i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherPlank."+NetherPlankItemBlock.blockNames[i]+".name", NetherPlankItemBlock.blockDisplayNames[i]);	
		}
		
		/*LanguageRegistry.instance().addStringLocalization("tile.NetherOre.DemonicOre.name", "Demonic Ore");
		
		LanguageRegistry.instance().addStringLocalization("tile.NetherWood.Hellfire.name", "Hellfire Wood");
		LanguageRegistry.instance().addStringLocalization("tile.NetherWood.Acid.name", "Acid Wood");
		LanguageRegistry.instance().addStringLocalization("tile.NetherWood.Death.name", "Death Wood");
		
		LanguageRegistry.instance().addStringLocalization("tile.NetherPlank.Hellfire.name", "Hellfire Log");
		LanguageRegistry.instance().addStringLocalization("tile.NetherPlank.Acid.name", "Acid Log");
		LanguageRegistry.instance().addStringLocalization("tile.NetherPlank.Death.name", "Death Log");
		*/
		LanguageRegistry.instance().addStringLocalization("item.NetherOreIngot.DemonicIngot.name", "Demonic Ingot");		
	}
}
