package NetherStuffs;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraftforge.common.Configuration;
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
import NetherStuffs.Blocks.SoulBombItemBlock;
import NetherStuffs.Client.ClientPacketHandler;
import NetherStuffs.Common.CommonProxy;
import NetherStuffs.Common.GuiHandler;
import NetherStuffs.Common.NetherStuffsFuel;
import NetherStuffs.Common.ServerPacketHandler;
import NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import NetherStuffs.Items.NetherDemonicBarHandle;
import NetherStuffs.Items.NetherItems;
import NetherStuffs.Items.NetherOreIngot;
import NetherStuffs.Items.NetherPotionBottle;
import NetherStuffs.Items.NetherSoulGlassBottle;
import NetherStuffs.Items.NetherStoneBowl;
import NetherStuffs.Items.NetherStonePotionBowl;
import NetherStuffs.Items.NetherWoodStick;
import NetherStuffs.Items.SoulEnergyBottle;
import NetherStuffs.SoulWorkBench.TileSoulWorkBench;
import NetherStuffs.WorldGen.WorldGenNetherStuffsMinable;
import NetherStuffs.WorldGen.WorldGenNetherStuffsTrees;
import cpw.mods.fml.common.DummyModContainer;
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

@Mod(name = "NetherStuffs", version = "1.0.1", modid = "NetherStuffs")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ServerPacketHandler.class))
public class NetherStuffs extends DummyModContainer {
	@Instance
	public static NetherStuffs instance = new NetherStuffs();

	private GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(clientSide = "NetherStuffs.Client.ClientProxy", serverSide = "NetherStuffs.Common.CommonProxy")
	public static CommonProxy proxy;

	public static Block NetherDemonicFurnace;
	public static Block NetherSoulGlass;
	public static Block NetherSoulGlassPane;

	public static Item NetherOreIngot;

	public static Item NetherDemonicBarHandle;
	public static Item NetherWoodStick;
	public static Item NetherStoneBowl;
	public static Item NetherSoulGlassBottleItem;

	public static int NetherOreBlockId;
	public static int NetherWoodBlockId;
	public static int NetherPlankBlockId;
	public static int NetherDemonicFurnaceBlockId;
	public static int NetherLeavesBlockId;
	public static int NetherSaplingBlockId;
	public static int NetherSoulGlassBlockid;
	public static int NetherSoulGlassPaneBlockid;
	public static int NetherPuddleBlockId;
	
	public static int SoulWorkBenchBlockId;
	public static int NetherSoulBombBlockId;

	public static int NetherObsidianSwordAcidItemId;
	public static int NetherObsidianSwordDeathItemId;

	public static int NetherOreIngotItemId;
	public static int NetherWoodCharcoalItemId;
	public static int NetherDemonicBarHandleItemId;
	public static int NetherObsidianSwordItemId;
	public static int NetherWoodStickItemId;
	public static int NetherStoneBowlItemId;
	public static int NetherStonePotionBowlItemId;
	public static int NetherSoulGlassBottleItemId;
	public static int NetherPotionBottleItemId;

	public static int SoulEnergyBottleItemId;

	public static int NetherSoulglassSwordItemId;
	public static int NetherSoulglassSwordAcidItemId;
	public static int NetherSoulglassSwordDeathItemId;
	public static int NetherSoulglassSwordHellfireItemId;

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
		SoulWorkBenchBlockId = config.get(Configuration.CATEGORY_BLOCK, "Soul Workbench", 1239).getInt();
		NetherSoulBombBlockId = config.get(Configuration.CATEGORY_BLOCK, "Soul Bomb", 1240).getInt();

		NetherOreIngotItemId = config.get(Configuration.CATEGORY_ITEM, "NetherIngots", 5000).getInt();
		NetherDemonicBarHandleItemId = config.get(Configuration.CATEGORY_ITEM, "DemonicSwordHandle", 5001).getInt();
		NetherObsidianSwordItemId = config.get(Configuration.CATEGORY_ITEM, "ObsidianSword", 5002).getInt();
		NetherWoodStickItemId = config.get(Configuration.CATEGORY_ITEM, "NetherWoodStick", 5003).getInt();
		NetherStoneBowlItemId = config.get(Configuration.CATEGORY_ITEM, "NetherStoneBowl", 5004).getInt();
		NetherSoulGlassBottleItemId = config.get(Configuration.CATEGORY_ITEM, "SoulGlassBottle", 5005).getInt();

		NetherObsidianSwordAcidItemId = config.get(Configuration.CATEGORY_ITEM, "ObsidianSwordAcid", 5006).getInt();
		NetherObsidianSwordDeathItemId = config.get(Configuration.CATEGORY_ITEM, "ObsidianSwordDeath", 5007).getInt();

		NetherPotionBottleItemId = config.get(Configuration.CATEGORY_ITEM, "PotionBottle", 5008).getInt();
		NetherStonePotionBowlItemId = config.get(Configuration.CATEGORY_ITEM, "PotionBowl", 5009).getInt();

		NetherSoulglassSwordItemId = config.get(Configuration.CATEGORY_ITEM, "SoulglassSword", 5010).getInt();
		NetherSoulglassSwordAcidItemId = config.get(Configuration.CATEGORY_ITEM, "SoulglassSwordAcid", 5011).getInt();
		NetherSoulglassSwordDeathItemId = config.get(Configuration.CATEGORY_ITEM, "SoulglassSwordDeath", 5012).getInt();
		NetherSoulglassSwordHellfireItemId = config.get(Configuration.CATEGORY_ITEM, "SoulglassSwordHellfire", 5013).getInt();

		NetherWoodCharcoalItemId = config.get(Configuration.CATEGORY_ITEM, "NetherWood Charcoal", 5014).getInt();
		SoulEnergyBottleItemId = config.get(Configuration.CATEGORY_ITEM, "Soul Energy Potion", 5015).getInt();

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
		GameRegistry.registerBlock(NetherBlocks.netherSoulWorkBench);

		NetherOreIngot = new NetherOreIngot(NetherOreIngotItemId).setItemName("NetherOreIngot").setIconCoord(0, 0);

		NetherStoneBowl = new NetherStoneBowl(NetherStoneBowlItemId).setItemName("NetherStoneBowl").setIconCoord(3, 1);
		NetherWoodStick = new NetherWoodStick(NetherWoodStickItemId).setItemName("NetherWoodStick").setIconCoord(2, 1);
		NetherDemonicBarHandle = new NetherDemonicBarHandle(NetherDemonicBarHandleItemId).setItemName("NetherDemonicBarHandle").setIconCoord(4, 1);
		NetherSoulGlassBottleItem = new NetherSoulGlassBottle(NetherSoulGlassBottleItemId).setItemName("NetherSoulGlassBottleItem").setIconCoord(0, 1);

		Item.itemsList[NetherOreBlockId] = new NetherOreItemBlock(NetherOreBlockId - 256, NetherBlocks.netherOre).setItemName("NetherOreItemBlock");
		Item.itemsList[NetherWoodBlockId] = new NetherWoodItemBlock(NetherWoodBlockId - 256, NetherBlocks.netherWood).setItemName("NetherWoodItemBlock");
		Item.itemsList[NetherPlankBlockId] = new NetherPlankItemBlock(NetherPlankBlockId - 256, NetherBlocks.netherPlank).setItemName("NetherPlankItemBlock");
		Item.itemsList[NetherLeavesBlockId] = new NetherLeavesItemBlock(NetherLeavesBlockId - 256, NetherBlocks.netherLeaves).setItemName("NetherLeavesItemBlock");
		Item.itemsList[NetherSaplingBlockId] = new NetherSaplingItemBlock(NetherSaplingBlockId - 256).setItemName("NetherSaplingItemBlock");
		Item.itemsList[NetherPuddleBlockId] = new NetherPuddleItemBlock(NetherPuddleBlockId - 256).setItemName("NetherPuddleItemBlock");
		Item.itemsList[NetherSoulBombBlockId] = new SoulBombItemBlock(NetherSoulBombBlockId - 256).setItemName("NetherSoulBombItemBlock");

		GameRegistry.registerTileEntity(TileDemonicFurnace.class, "tileEntityNetherStuffsDemonicFurnace");
		GameRegistry.registerTileEntity(TileSoulWorkBench.class, "tileEntityNetherStuffsSoulWorkBench");
		
		GameRegistry.registerFuelHandler(new NetherStuffsFuel());

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
		DemonicFurnaceRecipes.smelting().addSmelting(Block.slowSand.blockID, 0, new ItemStack(NetherStuffs.NetherSoulGlass, 1, 0), 0.5F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherWood.blockID, 0, new ItemStack(NetherItems.NetherWoodCharcoal, 1, 0), 0.25F);

		GameRegistry.addRecipe(new ItemStack(NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', new ItemStack(NetherBlocks.netherOre, 1, NetherBlocks.netherStone) });

		GameRegistry.addRecipe(new ItemStack(NetherWoodStick, 4), new Object[] { "#", "#", '#', NetherBlocks.netherPlank });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1), new Object[] { new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.hellfire),
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.acid), new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death),
				NetherSoulGlassBottleItem, new ItemStack(NetherOreIngot, 1, 0) });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1), new Object[] {
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.hellfire), new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid),
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death), new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.hellfire),
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid), new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death),
				NetherSoulGlassBottleItem, new ItemStack(NetherOreIngot, 1, 0) });

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

		GameRegistry.addRecipe(new ItemStack(NetherStoneBowl, 3), new Object[] { "N N", " N ", 'N', new ItemStack(NetherBlocks.netherOre, 1, NetherBlocks.netherStone) });

		GameRegistry.addRecipe(new ItemStack(NetherSoulGlassPane, 16), new Object[] { "###", "###", '#', NetherSoulGlass });
		GameRegistry.addRecipe(new ItemStack(NetherSoulGlassBottleItem, 3), new Object[] { "# #", " # ", '#', NetherSoulGlass });

		/**
		 * Swords
		 */
		initSwordRecipes();

	}

	private void initSwordRecipes() {
		/**
		 * Obsidian Sword & Obsidian Acid/Death Sword
		 */
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSword, 1), new Object[] { " O ", " O ", " H ", 'O', Block.obsidian, 'H', NetherDemonicBarHandle });

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordAcid, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.acid), 'S', NetherItems.NetherObsidianSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordDeath, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death), 'S', NetherItems.NetherObsidianSword });

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordAcid, 1), new Object[] { " # ", "#S#", " # ", '#',
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid), 'S', NetherItems.NetherObsidianSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordDeath, 1), new Object[] { " # ", "#S#", " # ", '#',
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death), 'S', NetherItems.NetherObsidianSword });

		/**
		 * Soulglass Swords
		 */
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSword, 1), new Object[] { " # ", " # ", " H ", '#', NetherSoulGlass, 'H', NetherDemonicBarHandle });

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSwordHellfire, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.hellfire), 'S', NetherItems.NetherSoulglassSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSwordDeath, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death), 'S', NetherItems.NetherSoulglassSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSwordAcid, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.acid), 'S', NetherItems.NetherSoulglassSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSwordDeath, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death), 'S', NetherItems.NetherSoulglassSword });

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSwordHellfire, 1), new Object[] { " # ", "#S#", " # ", '#',
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.hellfire), 'S', NetherItems.NetherSoulglassSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSwordAcid, 1), new Object[] { " # ", "#S#", " # ", '#',
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid), 'S', NetherItems.NetherSoulglassSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSwordDeath, 1), new Object[] { " # ", "#S#", " # ", '#',
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death), 'S', NetherItems.NetherSoulglassSword });
	}

	/**
	 * Returns Default Minecraft Items from Mod Items
	 */
	private void initDefaultMinecraftRecipes() {
		GameRegistry.addRecipe(new ItemStack(Item.bed, 1), new Object[] { "###", "XXX", '#', Block.cloth, 'X', NetherBlocks.netherPlank });
		GameRegistry.addRecipe(new ItemStack(Block.chest), new Object[] { "###", "# #", "###", '#', NetherBlocks.netherPlank });
		GameRegistry.addRecipe(new ItemStack(Block.workbench), new Object[] { "##", "##", '#', NetherBlocks.netherPlank });
		GameRegistry.addRecipe(new ItemStack(Block.netherBrick, 4), new Object[] { "##", "##", '#', NetherBlocks.netherStone }); // this actually is not existant in MC yet
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Item result, int itemMetadata) {
		addSmeltingMeta(block, blockMetadata, result, itemMetadata, 1);
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Item result, int itemMetadata, int itemCount) {
		FurnaceRecipes.smelting().addSmelting(block.blockID, blockMetadata, new ItemStack(result, itemCount, itemMetadata), 0);
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Block result, int itemMetadata) {
		addSmeltingMeta(block, blockMetadata, result, itemMetadata, 1);
	}

	private void addSmeltingMeta(Block block, int blockMetadata, Block result, int itemMetadata, int itemCount) {
		FurnaceRecipes.smelting().addSmelting(block.blockID, blockMetadata, new ItemStack(result, itemCount, itemMetadata), 0);
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

		for (int i = 0; i < ((NetherSoulGlassBottle) NetherSoulGlassBottleItem).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherSoulGlassBottleItem." + ((NetherSoulGlassBottle) NetherSoulGlassBottleItem).itemNames[i] + ".name",
					((NetherSoulGlassBottle) NetherSoulGlassBottleItem).itemDisplayNames[i]);
		}

		for (int i = 0; i < ((NetherPotionBottle) NetherItems.NetherPotionBottle).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherPotionBottle." + ((NetherPotionBottle) NetherItems.NetherPotionBottle).itemNames[i] + ".name",
					((NetherPotionBottle) NetherItems.NetherPotionBottle).itemDisplayNames[i]);
		}

		for (int i = 0; i < ((NetherStonePotionBowl) NetherItems.NetherStonePotionBowl).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherStonePotionBowl." + ((NetherStonePotionBowl) NetherItems.NetherStonePotionBowl).itemNames[i] + ".name",
					((NetherStonePotionBowl) NetherItems.NetherStonePotionBowl).itemDisplayNames[i]);
		}

		for (int i = 0; i < ((SoulEnergyBottle) NetherItems.SoulEnergyBottle).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.SoulEnergyBottle." + ((SoulEnergyBottle) NetherItems.SoulEnergyBottle).itemNames[i] + ".name",
					((SoulEnergyBottle) NetherItems.SoulEnergyBottle).itemDisplayNames[i]);
		}

		LanguageRegistry.instance().addStringLocalization("item.NetherObsidianSword.name", "Obsidian Sword");
		LanguageRegistry.instance().addStringLocalization("item.NetherSoulglassSword.name", "Soulglass Sword");

		LanguageRegistry.instance().addStringLocalization("item.NetherDemonicBarHandle.name", "Demonic Haft");
		LanguageRegistry.instance().addStringLocalization("item.NetherWoodStick.name", "Nether Stick");

		LanguageRegistry.instance().addStringLocalization("tile.NetherDemonicFurnace.name", "Demonic Furnace");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulWorkBench.name", "Soul Workbench");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlass.name", "Soul Glass");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlassPane.name", "Soul Glass Pane");

		LanguageRegistry.instance().addStringLocalization("item.NetherWoodCharcoal.name", "Nether Charcoal");

	}
}
