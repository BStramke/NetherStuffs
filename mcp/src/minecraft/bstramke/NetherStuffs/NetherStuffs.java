package bstramke.NetherStuffs;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Blocks.NetherLeavesItemBlock;
import bstramke.NetherStuffs.Blocks.NetherOre;
import bstramke.NetherStuffs.Blocks.NetherOreItemBlock;
import bstramke.NetherStuffs.Blocks.NetherPlank;
import bstramke.NetherStuffs.Blocks.NetherPlankItemBlock;
import bstramke.NetherStuffs.Blocks.NetherPuddleItemBlock;
import bstramke.NetherStuffs.Blocks.NetherSaplingItemBlock;
import bstramke.NetherStuffs.Blocks.NetherWood;
import bstramke.NetherStuffs.Blocks.NetherWoodItemBlock;
import bstramke.NetherStuffs.Blocks.SoulBlocker;
import bstramke.NetherStuffs.Blocks.SoulBlockerItemBlock;
import bstramke.NetherStuffs.Blocks.SoulBombItemBlock;
import bstramke.NetherStuffs.Blocks.SoulDetectorItemBlock;
import bstramke.NetherStuffs.Blocks.SoulSiphonItemBlock;
import bstramke.NetherStuffs.Client.ClientPacketHandler;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.GuiHandler;
import bstramke.NetherStuffs.Common.NetherStuffsFuel;
import bstramke.NetherStuffs.Common.NetherStuffsPlayerTracker;
import bstramke.NetherStuffs.Common.ServerPacketHandler;
import bstramke.NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import bstramke.NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.NetherOreIngot;
import bstramke.NetherStuffs.Items.NetherPotionBottle;
import bstramke.NetherStuffs.Items.NetherSoulGlassBottle;
import bstramke.NetherStuffs.Items.NetherStoneBowl;
import bstramke.NetherStuffs.Items.NetherStonePotionBowl;
import bstramke.NetherStuffs.Items.NetherWoodCharcoal;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import bstramke.NetherStuffs.SoulBlocker.TileSoulBlocker;
import bstramke.NetherStuffs.SoulDetector.TileSoulDetector;
import bstramke.NetherStuffs.SoulFurnace.TileSoulFurnace;
import bstramke.NetherStuffs.SoulSiphon.TileSoulSiphon;
import bstramke.NetherStuffs.SoulWorkBench.TileSoulWorkBench;
import bstramke.NetherStuffs.WorldGen.WorldGenNetherStuffsMinable;
import bstramke.NetherStuffs.WorldGen.WorldGenNetherStuffsTrees;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(name = "NetherStuffs", version = "0.11", modid = "NetherStuffs")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ServerPacketHandler.class))
public class NetherStuffs extends DummyModContainer {	
	@Instance
	public static NetherStuffs instance = new NetherStuffs();

	private GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(clientSide = "bstramke.NetherStuffs.Client.ClientProxy", serverSide = "bstramke.NetherStuffs.Common.CommonProxy")
	public static CommonProxy proxy;

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

	public static int NetherBowItemId;
	public static int TorchArrowItemId;
	public static int SoulEnergyLiquidItemId;
	//public static int SoulEnergyLiquidId;

	public static int NetherSoulDetectorBlockId;
	public static int NetherSoulBlockerBlockId;
	public static int NetherSoulFurnaceBlockId;
	public static int NetherSoulSiphonBlockId;

	private static boolean SpawnSkeletonsAwayFromNetherFortresses;
	private static boolean IncreaseNetherrackHardness;
	private static boolean SpawnBlazesNaturally;

	public static LiquidStack SoulEnergyLiquid;
	
	@PreInit
	public void PreLoad(FMLPreInitializationEvent event) {
		FMLLog.info("[NetherStuffs] PreLoad");
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		NetherOreBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherOre", 1230).getInt();
		NetherWoodBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherWood", 1231).getInt();
		NetherPlankBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherPlank", 1232).getInt();
		NetherDemonicFurnaceBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "DemonicFurnace", 1233).getInt();
		NetherLeavesBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Leaves", 1234).getInt();
		NetherSaplingBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Saplings", 1235).getInt();
		NetherSoulGlassBlockid = config.getBlock(Configuration.CATEGORY_BLOCK, "Glass", 1236).getInt();
		NetherSoulGlassPaneBlockid = config.getBlock(Configuration.CATEGORY_BLOCK, "GlassPane", 1237).getInt();
		NetherPuddleBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Puddle", 1238).getInt();
		SoulWorkBenchBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulWorkbench", 1239).getInt();
		NetherSoulBombBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulBomb", 1240).getInt();
		NetherSoulDetectorBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulDetector", 1241).getInt();
		NetherSoulBlockerBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulBlocker", 1242).getInt();
		NetherSoulFurnaceBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulFurnace", 1243).getInt();
		NetherSoulSiphonBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulSiphon", 1244).getInt();

		NetherOreIngotItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherIngots", 5200).getInt();
		NetherDemonicBarHandleItemId = config.getItem(Configuration.CATEGORY_ITEM, "DemonicSwordHandle", 5201).getInt();
		NetherObsidianSwordItemId = config.getItem(Configuration.CATEGORY_ITEM, "ObsidianSword", 5202).getInt();
		NetherWoodStickItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherWoodStick", 5203).getInt();
		NetherStoneBowlItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherStoneBowl", 5204).getInt();
		NetherSoulGlassBottleItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulGlassBottle", 5205).getInt();

		NetherObsidianSwordAcidItemId = config.getItem(Configuration.CATEGORY_ITEM, "ObsidianSwordAcid", 5206).getInt();
		NetherObsidianSwordDeathItemId = config.getItem(Configuration.CATEGORY_ITEM, "ObsidianSwordDeath", 5207).getInt();

		NetherPotionBottleItemId = config.getItem(Configuration.CATEGORY_ITEM, "PotionBottle", 5208).getInt();
		NetherStonePotionBowlItemId = config.getItem(Configuration.CATEGORY_ITEM, "PotionBowl", 5209).getInt();

		NetherSoulglassSwordItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSword", 5210).getInt();
		NetherSoulglassSwordAcidItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSwordAcid", 5211).getInt();
		NetherSoulglassSwordDeathItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSwordDeath", 5212).getInt();
		NetherSoulglassSwordHellfireItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSwordHellfire", 5213).getInt();

		NetherWoodCharcoalItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherWoodCharcoal", 5214).getInt();
		SoulEnergyBottleItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyPotion", 5215).getInt();
		NetherBowItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherBow", 5216).getInt();
		TorchArrowItemId = config.getItem(Configuration.CATEGORY_ITEM, "TorchArrow", 5217).getInt();
		
		//SoulEnergyLiquidId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyLiquidID", 5018).getInt();
		SoulEnergyLiquidItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyLiquidItemID", 5218).getInt();

		SpawnSkeletonsAwayFromNetherFortresses = config.get(Configuration.CATEGORY_GENERAL, "SpawnSkeletonsAwayFromNetherFortresses", true).getBoolean(true);
		IncreaseNetherrackHardness = config.get(Configuration.CATEGORY_GENERAL, "IncreaseNetherrackHardness", true).getBoolean(true);
		SpawnBlazesNaturally = config.get(Configuration.CATEGORY_GENERAL, "SpawnBlazesNaturally", false).getBoolean(false);

		NetherStuffsEventHook.nDetectRadius = config.get(Configuration.CATEGORY_GENERAL, "SoulBlockerRadius", 8).getInt();

		NetherStuffsEventHook.SpawnSkeletonsOnlyOnNaturalNetherBlocks = config.get(Configuration.CATEGORY_GENERAL, "SpawnSkeletonsOnlyOnNaturalNetherBlocks", false)
				.getBoolean(false);
		// Add the Toggleable Spawn Allowing Blocks here (aka. Natural Nether
		// Blocks)
		NetherStuffsEventHook.lAllowedSpawnNetherBlockIds.add(Block.netherrack.blockID);
		NetherStuffsEventHook.lAllowedSpawnNetherBlockIds.add(Block.slowSand.blockID);
		NetherStuffsEventHook.lAllowedSpawnNetherBlockIds.add(Block.glowStone.blockID);
		NetherStuffsEventHook.lAllowedSpawnNetherBlockIds.add(Block.netherBrick.blockID);
		NetherStuffsEventHook.lAllowedSpawnNetherBlockIds.add(Block.netherFence.blockID);
		NetherStuffsEventHook.lAllowedSpawnNetherBlockIds.add(Block.stairsNetherBrick.blockID);
		NetherStuffsEventHook.lAllowedSpawnNetherBlockIds.add(Block.gravel.blockID);

		String tmpString = config.get(Configuration.CATEGORY_GENERAL, "BlockIDNetherSpawningBlacklist", NetherLeavesBlockId + "," + NetherSoulDetectorBlockId + ","
				+ NetherSoulBlockerBlockId + "," + NetherSoulFurnaceBlockId + "," + SoulWorkBenchBlockId + "," + NetherDemonicFurnaceBlockId + "," + NetherSoulGlassBlockid + ","
				+ NetherSoulGlassPaneBlockid + "," + NetherSoulBombBlockId).value;
		String[] tmp = tmpString.split(",");
		for (int i = 0; i < tmp.length; i++) {
			int intVal = Integer.parseInt(tmp[i].trim());
			if (intVal > 0 && intVal < 4096)
				NetherStuffsEventHook.lBlockSpawnListForced.add(intVal);
		}

		FMLLog.info("[NetherStuffs] Blocked Nether Spawns on Block IDs: %s", NetherStuffsEventHook.lBlockSpawnListForced.toString());
		config.save();
	}

	@Init
	public void load(FMLInitializationEvent event) {
		//LiquidContainerData data = new LiquidContainerData(NetherItems.SoulEnergyLiquid, null, NetherStuffs.SoulSiphon.ContainerSoulSiphon);
		//LiquidContainerRegistry.registerLiquid(data);
		
		GameRegistry.registerBlock(NetherBlocks.NetherSoulGlass, "NetherSoulGlass");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulGlassPane, "NetherSoulGlassPane");
		GameRegistry.registerBlock(NetherBlocks.NetherDemonicFurnace, "NetherDemonicFurnace");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulFurnace, "NetherSoulFurnace");
		GameRegistry.registerBlock(NetherBlocks.netherSoulWorkBench, "NetherSoulWorkBench");

		Item.itemsList[NetherOreBlockId] = new NetherOreItemBlock(NetherOreBlockId - 256, NetherBlocks.netherOre).setItemName("NetherOreItemBlock");
		Item.itemsList[NetherWoodBlockId] = new NetherWoodItemBlock(NetherWoodBlockId - 256, NetherBlocks.netherWood).setItemName("NetherWoodItemBlock");
		Item.itemsList[NetherPlankBlockId] = new NetherPlankItemBlock(NetherPlankBlockId - 256, NetherBlocks.netherPlank).setItemName("NetherPlankItemBlock");
		Item.itemsList[NetherLeavesBlockId] = new NetherLeavesItemBlock(NetherLeavesBlockId - 256, NetherBlocks.netherLeaves).setItemName("NetherLeavesItemBlock");
		Item.itemsList[NetherSaplingBlockId] = new NetherSaplingItemBlock(NetherSaplingBlockId - 256).setItemName("NetherSaplingItemBlock");
		Item.itemsList[NetherPuddleBlockId] = new NetherPuddleItemBlock(NetherPuddleBlockId - 256).setItemName("NetherPuddleItemBlock");
		Item.itemsList[NetherSoulBombBlockId] = new SoulBombItemBlock(NetherSoulBombBlockId - 256).setItemName("NetherSoulBombItemBlock");
		Item.itemsList[NetherSoulDetectorBlockId] = new SoulDetectorItemBlock(NetherSoulDetectorBlockId - 256).setItemName("NetherSoulDetectorItemBlock");
		Item.itemsList[NetherSoulBlockerBlockId] = new SoulBlockerItemBlock(NetherSoulBlockerBlockId - 256).setItemName("NetherSoulBlockerItemBlock");
		Item.itemsList[NetherSoulSiphonBlockId] = new SoulSiphonItemBlock(NetherSoulSiphonBlockId - 256).setItemName("NetherSoulSiphonItemBlock");
		
		//Item.itemsList[SoulEnergyLiquidItemId] = NetherItems.SoulEnergyLiquidItem;

		// set required Stuff to Gather
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreCobblestone, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherStone, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreCoal, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreIron, "pickaxe", 1);

		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.demonicOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreDiamond, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreEmerald, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreGold, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreRedstone, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreLapis, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherOre, NetherOre.netherOreObsidian, "pickaxe", 3);

		MinecraftForge.setBlockHarvestLevel(NetherBlocks.NetherDemonicFurnace, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.NetherSoulBlocker, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.NetherSoulDetector, "pickaxe", 1);

		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherSoulWorkBench, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherWood, "axe", 2);

		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherWood, NetherWood.acid, "axe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherWood, NetherWood.hellfire, "axe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherWood, NetherWood.death, "axe", 2);

		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherPlank, "axe", 1);

		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherPlank, NetherPlank.acid, "axe", 1);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherPlank, NetherPlank.hellfire, "axe", 1);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherPlank, NetherPlank.death, "axe", 1);

		MinecraftForge.removeBlockEffectiveness(NetherBlocks.netherOre, "pickaxe");

		GameRegistry.registerTileEntity(TileDemonicFurnace.class, "tileEntityNetherStuffsDemonicFurnace");
		GameRegistry.registerTileEntity(TileSoulFurnace.class, "tileEntityNetherStuffsSoulFurnace");
		GameRegistry.registerTileEntity(TileSoulWorkBench.class, "tileEntityNetherStuffsSoulWorkBench");
		GameRegistry.registerTileEntity(TileSoulDetector.class, "tileEntityNetherStuffsSoulDetector");
		GameRegistry.registerTileEntity(TileSoulBlocker.class, "tileEntityNetherStuffsSoulBlocker");
		GameRegistry.registerTileEntity(TileSoulSiphon.class, "tileEntityNetherStuffsSoulSiphon");

		GameRegistry.registerFuelHandler(new NetherStuffsFuel());
		EntityRegistry.registerModEntity(EntityTorchArrow.class, "TorchArrow", 1, instance, 128, 3, true);

		OreDictionary.registerOre("oreDemonic", new ItemStack(NetherBlocks.netherOre, 1, NetherOre.demonicOre));
		OreDictionary.registerOre("oreNetherStone", new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone));
		OreDictionary.registerOre("ingotDemonic", new ItemStack(NetherItems.NetherOreIngot));
		
		SoulEnergyLiquid = LiquidDictionary.getOrCreateLiquid("SoulEnergy", new LiquidStack(NetherItems.SoulEnergyLiquidItem, 1));
				
		registerWorldGenerators();
		initRecipes();
		initLanguageRegistry();
		proxy.registerRenderThings();
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);

		MinecraftForge.EVENT_BUS.register(new NetherStuffsEventHook());

		// set Netherrack hardness to different values
		if (IncreaseNetherrackHardness) {
			Block.netherrack.setHardness(1.5F);
			Block.netherrack.setResistance(2.0F);
		}
	
		GameRegistry.registerPlayerTracker(new NetherStuffsPlayerTracker());
		/*
		 * This lets Skeletons Spawn away from NetherFortresses. Actual Idea by ErasmoGnome, made on the MinecraftForums: http://www.minecraftforum.net
		 * /topic/1493398-move-wither-skeletons-out- of-fortresses-over-125-supporters/
		 */
		if (SpawnSkeletonsAwayFromNetherFortresses)
			EntityRegistry.addSpawn(EntitySkeleton.class, 40, 4, 4, EnumCreatureType.monster, BiomeGenBase.hell);
		if (SpawnBlazesNaturally)
			EntityRegistry.addSpawn(EntityBlaze.class, 5, 1, 1, EnumCreatureType.monster, BiomeGenBase.hell);
	}
	
	private void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.demonicOre, 3, 40));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreCoal, 5, 65));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreIron, 4, 40));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreGold, 3, 25));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreDiamond, 2, 15));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreEmerald, 2, 15));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreRedstone, 4, 20));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreObsidian, 4, 30));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreLapis, 3, 20));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsMinable(NetherBlocks.netherOre.blockID, NetherOre.netherOreCobblestone, 5, 50));
		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsTrees(true, 4, false));
	}

	private void initRecipes() {
		initDefaultMinecraftRecipes();

		addSmeltingMeta(Block.netherrack, 0, NetherBlocks.netherOre, NetherOre.netherStone); // this actually is the basic recipe
		// to get started

		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.demonicOre, new ItemStack(NetherItems.NetherOreIngot, 1, 0), 1.0F);
		DemonicFurnaceRecipes.smelting().addSmelting(Block.slowSand.blockID, 0, new ItemStack(NetherBlocks.NetherSoulGlass, 1, 0), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherWood.blockID, NetherWood.hellfire, new ItemStack(NetherItems.NetherWoodCharcoal), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreCoal,
				new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.coal), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreIron, new ItemStack(Item.ingotIron, 1), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreGold, new ItemStack(Item.ingotGold, 1), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreDiamond, new ItemStack(Item.diamond, 1), 0.5F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreEmerald, new ItemStack(Item.emerald, 1), 0.5F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreRedstone, new ItemStack(Item.redstone, 4), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreObsidian, new ItemStack(Block.obsidian, 1, 0), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreLapis, new ItemStack(Item.dyePowder, 4, 4), 0.25F);

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), true, new Object[] { "I#I", "#W#", "I#I", '#', "oreNetherStone", 'W',
						new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "I#I", "#W#", "I#I", '#',
				new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone), 'W', new ItemStack(Block.workbench), 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#',
				new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone), 'W', new ItemStack(Block.workbench), 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0) });

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N',
				new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone) });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.torchArrow, 1), new Object[] { new ItemStack(Item.arrow, 1), new ItemStack(Block.torchWood, 1, 0) });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#', "oreNetherStone", 'W',
						new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		CraftingManager.getInstance().getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', "oreNetherStone" }));

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherWoodStick, 4), new Object[] { "#", "#", '#', NetherBlocks.netherPlank });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.NetherSoulBlocker, 1, SoulBlocker.NetherSoulBlocker), new Object[] { "IPI", "SBS", "XXX", 'I', "ingotDemonic", 'P',
						NetherBlocks.netherPlank, 'S', NetherItems.NetherWoodStick, 'B', NetherItems.NetherPotionBottle, 'X', "oreNetherStone" }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new Object[] {
						new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.hellfire), new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.acid),
						new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death), NetherItems.NetherSoulGlassBottleItem, "ingotDemonic" }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new Object[] {
						new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.hellfire),
						new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid),
						new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death),
						new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.hellfire),
						new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid),
						new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death), NetherItems.NetherSoulGlassBottleItem, "ingotDemonic" }));

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new Object[] {
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.hellfire), new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.acid),
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death), NetherItems.NetherSoulGlassBottleItem, new ItemStack(NetherItems.NetherOreIngot, 1, 0) });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new Object[] {
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.hellfire), new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid),
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death), new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.hellfire),
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid), new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death),
				NetherItems.NetherSoulGlassBottleItem, new ItemStack(NetherItems.NetherOreIngot, 1, 0) });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1, 1), new Object[] { new ItemStack(NetherItems.SoulEnergyBottle, 1, 0),
				new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new ItemStack(NetherItems.SoulEnergyBottle, 1, 0),
				new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new ItemStack(NetherItems.SoulEnergyBottle, 1, 0),
				new ItemStack(NetherItems.SoulEnergyBottle, 1, 0), new ItemStack(NetherItems.SoulEnergyBottle, 1, 0) });
		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.SoulEnergyBottle, 1, 2), new Object[] { new ItemStack(NetherItems.SoulEnergyBottle, 1, 1),
				new ItemStack(NetherItems.SoulEnergyBottle, 1, 1), new ItemStack(NetherItems.SoulEnergyBottle, 1, 1), new ItemStack(NetherItems.SoulEnergyBottle, 1, 1),
				new ItemStack(NetherItems.SoulEnergyBottle, 1, 1), new ItemStack(NetherItems.SoulEnergyBottle, 1, 1), new ItemStack(NetherItems.SoulEnergyBottle, 1, 1),
				new ItemStack(NetherItems.SoulEnergyBottle, 1, 1), new ItemStack(NetherItems.SoulEnergyBottle, 1, 1) });

		// Netherwood --> Netherlogs
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherPlank, 4, NetherPlank.hellfire), new Object[] { "#", '#',
				new ItemStack(NetherBlocks.netherWood, 1, NetherWood.hellfire) });
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherPlank, 4, NetherPlank.acid), new Object[] { "#", '#', new ItemStack(NetherBlocks.netherWood, 1, NetherWood.acid) });
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherPlank, 4, NetherPlank.death), new Object[] { "#", '#', new ItemStack(NetherBlocks.netherWood, 1, NetherWood.death) });

		// Torches out of Netherwood Sticks
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 6), new Object[] { "X", "#", 'X', Item.coal, '#', NetherItems.NetherWoodStick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 6), new Object[] { "X", "#", 'X', new ItemStack(Item.coal, 1, 1), '#', NetherItems.NetherWoodStick });

		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 8), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.charcoal), '#',
				Item.stick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 8), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.coal), '#',
				Item.stick });

		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 10), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.charcoal), '#',
				NetherItems.NetherWoodStick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 10), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.coal), '#',
				NetherItems.NetherWoodStick });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherItems.NetherDemonicBarHandle, 1), new Object[] { "NIN", " S ", 'N', "oreNetherStone", 'I', "ingotDemonic", 'S',
						NetherItems.NetherWoodStick }));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(NetherItems.NetherStoneBowl, 3), new Object[] { "N N", " N ", 'N', "oreNetherStone" }));

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherDemonicBarHandle, 1), new Object[] { "NIN", " S ", 'N',
				new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone), 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0), 'S', NetherItems.NetherWoodStick });

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherStoneBowl, 3), new Object[] { "N N", " N ", 'N', new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone) });

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.NetherSoulGlassPane, 16), new Object[] { "###", "###", '#', NetherBlocks.NetherSoulGlass });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulGlassBottleItem, 3), new Object[] { "# #", " # ", '#', NetherBlocks.NetherSoulGlass });

		/**
		 * Swords
		 */
		initSwordRecipes();

	}

	private void initSwordRecipes() {
		/**
		 * Obsidian Sword & Obsidian Acid/Death Sword
		 */
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSword, 1), new Object[] { " O ", " O ", " H ", 'O', Block.obsidian, 'H', NetherItems.NetherDemonicBarHandle });

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
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulglassSword, 1), new Object[] { " # ", " # ", " H ", '#', NetherBlocks.NetherSoulGlass, 'H',
				NetherItems.NetherDemonicBarHandle });

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

		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(Block.netherBrick, 2), true, new Object[] { "NN", "NN", 'N', "oreNetherStone" }));
		GameRegistry.addRecipe(new ItemStack(Block.netherBrick, 2), new Object[] { "NN", "NN", 'N', new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.stick), new Object[] { new ItemStack(NetherItems.NetherWoodStick) });

		// Default Wooden Tools
		GameRegistry.addRecipe(new ItemStack(Item.axeWood, 1), new Object[] { "##.", "#S.", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S', new ItemStack(Item.stick) });
		GameRegistry.addRecipe(new ItemStack(Item.axeWood, 1), new Object[] { ".##", ".S#", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S', new ItemStack(Item.stick) });
		GameRegistry
				.addRecipe(new ItemStack(Item.pickaxeWood, 1), new Object[] { "###", ".S.", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S', new ItemStack(Item.stick) });
		GameRegistry.addRecipe(new ItemStack(Item.shovelWood, 1), new Object[] { ".#.", ".S.", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S', new ItemStack(Item.stick) });

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

		for (int i = 0; i < SoulDetectorItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulDetector." + SoulDetectorItemBlock.blockNames[i] + ".name", SoulDetectorItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < SoulBlockerItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulBlocker." + SoulBlockerItemBlock.blockNames[i] + ".name", SoulBlockerItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < SoulSiphonItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulSiphon." + SoulSiphonItemBlock.blockNames[i] + ".name", SoulSiphonItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < ((NetherOreIngot) NetherItems.NetherOreIngot).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherOreIngot." + ((NetherOreIngot) NetherItems.NetherOreIngot).itemNames[i] + ".name",
					((NetherOreIngot) NetherItems.NetherOreIngot).itemDisplayNames[i]);
		}

		for (int i = 0; i < ((NetherStoneBowl) NetherItems.NetherStoneBowl).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherStoneBowl." + ((NetherStoneBowl) NetherItems.NetherStoneBowl).itemNames[i] + ".name",
					((NetherStoneBowl) NetherItems.NetherStoneBowl).itemDisplayNames[i]);
		}

		for (int i = 0; i < ((NetherSoulGlassBottle) NetherItems.NetherSoulGlassBottleItem).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization(
					"item.NetherSoulGlassBottleItem." + ((NetherSoulGlassBottle) NetherItems.NetherSoulGlassBottleItem).itemNames[i] + ".name",
					((NetherSoulGlassBottle) NetherItems.NetherSoulGlassBottleItem).itemDisplayNames[i]);
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

		for (int i = 0; i < ((NetherWoodCharcoal) NetherItems.NetherWoodCharcoal).getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("item.NetherWoodCharcoal." + ((NetherWoodCharcoal) NetherItems.NetherWoodCharcoal).itemNames[i] + ".name",
					((NetherWoodCharcoal) NetherItems.NetherWoodCharcoal).itemDisplayNames[i]);
		}

		LanguageRegistry.instance().addStringLocalization("item.NetherObsidianSword.name", "Obsidian Sword");
		LanguageRegistry.instance().addStringLocalization("item.NetherSoulglassSword.name", "Soulglass Sword");

		LanguageRegistry.instance().addStringLocalization("item.NetherDemonicBarHandle.name", "Demonic Haft");
		LanguageRegistry.instance().addStringLocalization("item.NetherWoodStick.name", "Nether Stick");

		LanguageRegistry.instance().addStringLocalization("tile.NetherDemonicFurnace.name", "Demonic Furnace");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulFurnace.name", "Soul Furnace");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulWorkBench.name", "Soul Workbench");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlass.name", "Soul Glass");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlassPane.name", "Soul Glass Pane");

		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulBomb.NetherSoulBomb.name", "Soul Bomb");
		
		// LanguageRegistry.instance().addStringLocalization("item.NetherWoodCharcoal.name", "Nether Charcoal");

		LanguageRegistry.instance().addStringLocalization("item.NetherBow.name", "Nether Bow");
		LanguageRegistry.instance().addStringLocalization("item.torchArrow.name", "Torch Arrow");
		LanguageRegistry.instance().addStringLocalization("item.SoulEnergyLiquidItem.name", "Soul Energy Liquid");
	}

	@PostInit
	public static void postInit(FMLPostInitializationEvent event) {
		FMLLog.info("[NetherStuffs] postInit");
	}
}
