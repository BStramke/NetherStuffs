package NetherStuffs;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import NetherStuffs.Blocks.NetherBlocks;
import NetherStuffs.Blocks.NetherDemonicFurnace;
import NetherStuffs.Blocks.NetherLeavesItemBlock;
import NetherStuffs.Blocks.NetherOreItemBlock;
import NetherStuffs.Blocks.NetherPlankItemBlock;
import NetherStuffs.Blocks.NetherPuddleItemBlock;
import NetherStuffs.Blocks.NetherSaplingItemBlock;
import NetherStuffs.Blocks.NetherSoulGlass;
import NetherStuffs.Blocks.NetherSoulGlassPane;
import NetherStuffs.Blocks.NetherWoodItemBlock;
import NetherStuffs.Client.ClientPacketHandler;
import NetherStuffs.Common.CommonProxy;
import NetherStuffs.Common.DemonicFurnaceRecipes;
import NetherStuffs.Common.GuiHandler;
import NetherStuffs.Common.ServerPacketHandler;
import NetherStuffs.Common.TileDemonicFurnace;
import NetherStuffs.Items.NetherDemonicBarHandle;
import NetherStuffs.Items.NetherObsidianSword;
import NetherStuffs.Items.NetherOreIngot;
import NetherStuffs.Items.NetherSoulGlassBottle;
import NetherStuffs.Items.NetherStoneBowl;
import NetherStuffs.Items.NetherWoodStick;
import NetherStuffs.WorldGen.WorldGenNetherStuffsMinable;
import NetherStuffs.WorldGen.WorldGenNetherStuffsTrees;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "NetherStuffs", name = "NetherStuffs", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ServerPacketHandler.class))
public class NetherStuffs {
	@Instance
	public static NetherStuffs instance = new NetherStuffs();

	private GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(clientSide = "NetherStuffs.Client.ClientProxy", serverSide = "NetherStuffs.Common.CommonProxy")
	public static CommonProxy proxy;

	static EnumToolMaterial EnumToolMaterialDemonicIngot = EnumHelper.addToolMaterial("DemonicIngot", 2, 400, 6.0F, 6, 15);
	static EnumToolMaterial EnumToolMaterialObsidian = EnumHelper.addToolMaterial("Obsidian", 2, 400, 7.0F, 4, 15);

	public static Block NetherDemonicFurnace;
	public static Block NetherSoulGlass;
	public static Block NetherSoulGlassPane;

	public static Item NetherOreIngot;

	public static Item NetherObsidianSword;
	public static Item NetherDemonicBarHandle;
	public static Item NetherWoodStick;
	public static Item NetherStoneBowl;
	public static Item NetherSoulGlassBottle;

	public static int NetherOreBlockId;
	public static int NetherWoodBlockId;
	public static int NetherPlankBlockId;
	public static int NetherDemonicFurnaceBlockId;
	public static int NetherLeavesBlockId;
	public static int NetherSaplingBlockId;
	public static int NetherSoulGlassBlockid;
	public static int NetherSoulGlassPaneBlockid;
	public static int NetherPuddleBlockId;

	public static int NetherOreIngotItemId;
	public static int NetherDemonicBarHandleItemId;
	public static int NetherObsidianSwordItemId;
	public static int NetherWoodStickItemId;
	public static int NetherStoneBowlItemId;
	public static int NetherSoulGlassBottleItemId;

	@PreInit
	public void PreLoad(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		NetherOreBlockId = config.get(Configuration.CATEGORY_BLOCK, "NetherOre", 1230).getInt();
		NetherWoodBlockId = config.get(Configuration.CATEGORY_BLOCK, "NetherWood", 1231).getInt();
		NetherPlankBlockId = config.get(Configuration.CATEGORY_BLOCK, "NetherPlank", 1232).getInt();
		NetherDemonicFurnaceBlockId = config.get(Configuration.CATEGORY_BLOCK, "DemonicFurnace", 1233).getInt();
		NetherLeavesBlockId = config.get(Configuration.CATEGORY_BLOCK, "Leaves", 1234).getInt();
		NetherSaplingBlockId = config.get(Configuration.CATEGORY_BLOCK, "Saplings", 1235).getInt();
		NetherSoulGlassBlockid = config.get(Configuration.CATEGORY_BLOCK, "Glass", 1236).getInt();
		NetherSoulGlassPaneBlockid = config.get(Configuration.CATEGORY_BLOCK, "GlassPane", 1237).getInt();
		NetherPuddleBlockId = config.get(Configuration.CATEGORY_BLOCK, "Puddle", 1238).getInt();
		
		NetherOreIngotItemId = config.get(Configuration.CATEGORY_ITEM, "NetherIngots", 5000).getInt();
		NetherDemonicBarHandleItemId = config.get(Configuration.CATEGORY_ITEM, "DemonicSwordHandle", 5001).getInt();
		NetherObsidianSwordItemId = config.get(Configuration.CATEGORY_ITEM, "ObsidianSword", 5002).getInt();
		NetherWoodStickItemId = config.get(Configuration.CATEGORY_ITEM, "NetherWoodStick", 5003).getInt();
		NetherStoneBowlItemId = config.get(Configuration.CATEGORY_ITEM, "NetherStoneBowl", 5004).getInt();
		NetherSoulGlassBottleItemId = config.get(Configuration.CATEGORY_ITEM, "SoulGlassBottle", 5005).getInt();
		config.save();
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		NetherDemonicFurnace = new NetherDemonicFurnace(NetherDemonicFurnaceBlockId).setBlockName("NetherDemonicFurnace");
		NetherSoulGlass = new NetherSoulGlass(NetherSoulGlassBlockid, 112, Material.glass, false).setBlockName("NetherSoulGlass");
		NetherSoulGlassPane = new NetherSoulGlassPane(NetherSoulGlassPaneBlockid, 112, 113, Material.glass, false).setBlockName("NetherSoulGlassPane");

		GameRegistry.registerBlock(NetherSoulGlass);
		GameRegistry.registerBlock(NetherSoulGlassPane);
		GameRegistry.registerBlock(NetherDemonicFurnace);

		NetherOreIngot = new NetherOreIngot(NetherOreIngotItemId).setItemName("NetherOreIngot").setIconCoord(0, 0);

		NetherStoneBowl = new NetherStoneBowl(NetherStoneBowlItemId).setItemName("NetherStoneBowl").setIconCoord(3, 1);
		NetherWoodStick = new NetherWoodStick(NetherWoodStickItemId).setItemName("NetherWoodStick").setIconCoord(2, 1);
		NetherObsidianSword = new NetherObsidianSword(NetherObsidianSwordItemId, EnumToolMaterialObsidian).setItemName("NetherObsidianSword").setIconCoord(1, 1);
		NetherDemonicBarHandle = new NetherDemonicBarHandle(NetherDemonicBarHandleItemId).setItemName("NetherDemonicBarHandle").setIconCoord(4, 1);
		NetherSoulGlassBottle = new NetherSoulGlassBottle(NetherSoulGlassBottleItemId).setItemName("NetherSoulGlassBottle").setIconCoord(0, 1);

		Item.itemsList[NetherOreBlockId] = new NetherOreItemBlock(NetherOreBlockId - 256, NetherBlocks.netherOre).setItemName("NetherOreItemBlock");
		Item.itemsList[NetherWoodBlockId] = new NetherWoodItemBlock(NetherWoodBlockId - 256, NetherBlocks.netherWood).setItemName("NetherWoodItemBlock");
		Item.itemsList[NetherPlankBlockId] = new NetherPlankItemBlock(NetherPlankBlockId - 256, NetherBlocks.netherPlank).setItemName("NetherPlankItemBlock");
		Item.itemsList[NetherLeavesBlockId] = new NetherLeavesItemBlock(NetherLeavesBlockId - 256, NetherBlocks.netherLeaves).setItemName("NetherLeavesItemBlock");
		Item.itemsList[NetherSaplingBlockId] = new NetherSaplingItemBlock(NetherSaplingBlockId - 256).setItemName("NetherSaplingItemBlock");
		Item.itemsList[NetherPuddleBlockId] = new NetherPuddleItemBlock(NetherPuddleBlockId - 256).setItemName("NetherPuddleItemBlock");

		GameRegistry.registerTileEntity(TileDemonicFurnace.class, "tileEntityNetherStuffs");

		registerWorldGenerators();
		initRecipes();
		initLanguageRegistry();
		proxy.registerRenderThings();
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
	}

	private void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, 0, 5));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsTrees(true, 4, false));
	}

	private void initRecipes() {
		addSmeltingMeta(Block.netherrack, 0, NetherBlocks.netherOre, NetherBlocks.netherStone); // this actually is the basic recipe to get started

		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, 0, new ItemStack(NetherOreIngot, 1, 0), 1.0F);
		DemonicFurnaceRecipes.smelting().addSmelting(Block.slowSand.blockID, 0, new ItemStack(NetherStuffs.NetherSoulGlass, 1, 0), 1.0F);

		// addSmeltingMeta(NetherBlocks.netherOre, NetherBlocks.demonicOre, NetherOreIngot, 0); // this has to be moved to DemonicFurnace Smelting

		GameRegistry.addRecipe(new ItemStack(NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', new ItemStack(NetherBlocks.netherOre, 1, NetherBlocks.netherStone) });

		GameRegistry.addRecipe(new ItemStack(NetherWoodStick, 4), new Object[] { "#", "#", '#', NetherBlocks.netherPlank });

		// Netherwood --> Netherlogs
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherPlank, 4, NetherBlocks.netherPlankHellfire), new Object[] { "#", '#',
				new ItemStack(NetherBlocks.netherWood, 1, NetherBlocks.netherWoodHellfire) });
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherPlank, 4, NetherBlocks.netherPlankAcid), new Object[] { "#", '#',
				new ItemStack(NetherBlocks.netherWood, 1, NetherBlocks.netherWoodAcid) });
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherPlank, 4, NetherBlocks.netherPlankDeath), new Object[] { "#", '#',
				new ItemStack(NetherBlocks.netherWood, 1, NetherBlocks.netherWoodDeath) });

		// Torches out of Netherwood Sticks
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 6), new Object[] { "X", "#", 'X', Item.coal, '#', NetherWoodStick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 6), new Object[] { "X", "#", 'X', new ItemStack(Item.coal, 1, 1), '#', NetherWoodStick });

		GameRegistry.addRecipe(new ItemStack(NetherDemonicBarHandle, 1), new Object[] { "NIN", " S ", 'N', new ItemStack(NetherBlocks.netherOre, 1, NetherBlocks.netherStone), 'I',
				new ItemStack(NetherOreIngot, 1, 0), 'S', NetherWoodStick });
		GameRegistry.addRecipe(new ItemStack(NetherObsidianSword, 1), new Object[] { " O ", " O ", " H ", 'O', Block.obsidian, 'H', NetherDemonicBarHandle });
		GameRegistry.addRecipe(new ItemStack(NetherStoneBowl, 3), new Object[] { "N N", " N ", 'N', new ItemStack(NetherBlocks.netherOre, 1, NetherBlocks.netherStone) });

		// vanilla stuffs (Workbench, bed etc)
		GameRegistry.addRecipe(new ItemStack(Item.bed, 1), new Object[] { "###", "XXX", '#', Block.cloth, 'X', NetherBlocks.netherPlank });
		GameRegistry.addRecipe(new ItemStack(Block.chest), new Object[] { "###", "# #", "###", '#', NetherBlocks.netherPlank });
		GameRegistry.addRecipe(new ItemStack(Block.workbench), new Object[] { "##", "##", '#', NetherBlocks.netherPlank });

		GameRegistry.addRecipe(new ItemStack(NetherSoulGlassPane, 16), new Object[] { "###", "###", '#', NetherSoulGlass });
		GameRegistry.addRecipe(new ItemStack(NetherSoulGlassBottle, 3), new Object[] { "# #", " # ", '#', NetherSoulGlass });
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Item result, int itemMetadata) {
		addSmeltingMeta(block, blockMetadata, result, itemMetadata, 1);
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Item result, int itemMetadata, int itemCount) {
		FurnaceRecipes.smelting().addSmelting(block.blockID, blockMetadata, new ItemStack(result, itemCount, itemMetadata));
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Block result, int itemMetadata) {
		addSmeltingMeta(block, blockMetadata, result, itemMetadata, 1);
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Block result, int itemMetadata, int itemCount) {
		FurnaceRecipes.smelting().addSmelting(block.blockID, blockMetadata, new ItemStack(result, itemCount, itemMetadata));
	}

	private void initLanguageRegistry() {

		for (int i = 0; i < NetherOreItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherOre." + NetherOreItemBlock.blockNames[i] + ".name", NetherOreItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < NetherWoodItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherWood." + NetherWoodItemBlock.blockNames[i] + ".name", NetherWoodItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < NetherPlankItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherPlank." + NetherPlankItemBlock.blockNames[i] + ".name", NetherPlankItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < NetherLeavesItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherLeaves." + NetherLeavesItemBlock.blockNames[i] + ".name", NetherLeavesItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < NetherSaplingItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSapling." + NetherSaplingItemBlock.blockNames[i] + ".name", NetherSaplingItemBlock.blockDisplayNames[i]);
		}
		
		for (int i = 0; i < NetherPuddleItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherPuddle." + NetherPuddleItemBlock.blockNames[i] + ".name", NetherPuddleItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < ((NetherOreIngot) NetherOreIngot).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherOreIngot." + ((NetherOreIngot) NetherOreIngot).itemNames[i] + ".name",
					((NetherOreIngot) NetherOreIngot).itemDisplayNames[i]);
		}
		
		for (int i = 0; i < ((NetherStoneBowl) NetherStoneBowl).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherStoneBowl." + ((NetherStoneBowl) NetherStoneBowl).itemNames[i] + ".name",
					((NetherStoneBowl) NetherStoneBowl).itemDisplayNames[i]);
		}
		
		for (int i = 0; i < ((NetherSoulGlassBottle) NetherSoulGlassBottle).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherSoulGlassBottle." + ((NetherSoulGlassBottle) NetherSoulGlassBottle).itemNames[i] + ".name",
					((NetherSoulGlassBottle) NetherSoulGlassBottle).itemDisplayNames[i]);
		}

		LanguageRegistry.instance().addStringLocalization("item.NetherObsidianSword.name", "Obsidian Sword");
		LanguageRegistry.instance().addStringLocalization("item.NetherDemonicBarHandle.name", "Demonic Haft");
		LanguageRegistry.instance().addStringLocalization("item.NetherWoodStick.name", "Nether Stick");
		//LanguageRegistry.instance().addStringLocalization("item.NetherStoneBowl.name", "Netherstone Bowl");
		//LanguageRegistry.instance().addStringLocalization("item.NetherSoulGlassBottle.name", "Soul Glass Bottle");

		LanguageRegistry.instance().addStringLocalization("tile.NetherDemonicFurnace.name", "Demonic Furnace");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlass.name", "Soul Glass");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlassPane.name", "Soul Glass Pane");
	}
}
