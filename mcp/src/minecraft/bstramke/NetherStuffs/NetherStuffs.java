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
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Blocks.NetherLeavesItemBlock;
import bstramke.NetherStuffs.Blocks.NetherOre;
import bstramke.NetherStuffs.Blocks.NetherOreItemBlock;
import bstramke.NetherStuffs.Blocks.NetherPlank;
import bstramke.NetherStuffs.Blocks.NetherPlankItemBlock;
import bstramke.NetherStuffs.Blocks.NetherSapling;
import bstramke.NetherStuffs.Blocks.NetherSaplingItemBlock;
import bstramke.NetherStuffs.Blocks.NetherWood;
import bstramke.NetherStuffs.Blocks.NetherWoodItemBlock;
import bstramke.NetherStuffs.Blocks.NetherWoodPuddleItemBlock;
import bstramke.NetherStuffs.Blocks.SoulBlocker;
import bstramke.NetherStuffs.Blocks.SoulBlockerItemBlock;
import bstramke.NetherStuffs.Blocks.SoulBombItemBlock;
import bstramke.NetherStuffs.Blocks.SoulDetector;
import bstramke.NetherStuffs.Blocks.SoulDetectorItemBlock;
import bstramke.NetherStuffs.Blocks.SoulSiphon;
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
import bstramke.NetherStuffs.NetherWoodPuddle.TileNetherWoodPuddle;
import bstramke.NetherStuffs.SoulBlocker.TileSoulBlocker;
import bstramke.NetherStuffs.SoulDetector.TileSoulDetector;
import bstramke.NetherStuffs.SoulFurnace.TileSoulFurnace;
import bstramke.NetherStuffs.SoulSiphon.TileSoulSiphon;
import bstramke.NetherStuffs.SoulWorkBench.TileSoulWorkBench;
import bstramke.NetherStuffs.WorldGen.WorldGenDefaultMinable;
import bstramke.NetherStuffs.WorldGen.WorldGenNetherStuffsTrees;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
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

@Mod(name = "NetherStuffs", version = "0.15", modid = "NetherStuffs")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ServerPacketHandler.class))
public class NetherStuffs extends DummyModContainer {
	@Instance
	public static NetherStuffs instance = new NetherStuffs();

	private GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(clientSide = "bstramke.NetherStuffs.Client.ClientProxy", serverSide = "bstramke.NetherStuffs.Common.CommonProxy")
	public static CommonProxy proxy;

	public static boolean ShowOreDistributions = false;
	public static boolean DevSetCoreModAvailable = false;

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
	public static int NetherSkyBlockId;
	public static int NetherWoodPuddleBlockId;

	public static int NetherObsidianSwordAcidItemId;
	public static int NetherObsidianSwordDeathItemId;

	public static int NetherOreIngotItemId;
	public static int NetherWoodCharcoalItemId;
	public static int NetherDemonicBarHandleItemId;
	public static int NetherObsidianSwordItemId;
	public static int NetherDiamondSwordItemId;
	public static int NetherDiamondSwordAcidItemId;
	public static int NetherDiamondSwordDeathItemId;
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
	// public static int SoulEnergyLiquidId;

	public static int NetherSoulDetectorBlockId;
	public static int NetherSoulBlockerBlockId;
	public static int NetherSoulFurnaceBlockId;
	public static int NetherSoulSiphonBlockId;

	private static boolean SpawnSkeletonsAwayFromNetherFortresses;
	private static boolean IncreaseNetherrackHardness;
	private static boolean SpawnBlazesNaturally;
	public static boolean bShowCoreModMissingWarning;
	
	public static boolean bUseNetherOreDemonic;
	public static boolean bUseNetherOreCoal;
	public static boolean bUseNetherOreIron;
	public static boolean bUseNetherOreGold;
	public static boolean bUseNetherOreDiamond;
	public static boolean bUseNetherOreEmerald;
	public static boolean bUseNetherOreRedstone;
	public static boolean bUseNetherOreObsidian;
	public static boolean bUseNetherOreLapis;
	public static boolean bUseNetherOreCobblestone;

	public static boolean bOverrideBlockBreakable;
	public static boolean bOverrideBlockPane;
	public static boolean bOverrideChunk;

	public static LiquidStack SoulEnergyLiquid;

	@PreInit
	public void PreLoad(FMLPreInitializationEvent event) {
		FMLLog.info("[NetherStuffs] PreLoad");
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		NetherOreBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherOre", 1230).getInt(1230);
		NetherWoodBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherWood", 1231).getInt(1231);
		NetherPlankBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherPlank", 1232).getInt(1232);
		NetherDemonicFurnaceBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "DemonicFurnace", 1233).getInt(1233);
		NetherLeavesBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Leaves", 1234).getInt(1234);
		NetherSaplingBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Saplings", 1235).getInt(1235);
		NetherSoulGlassBlockid = config.getBlock(Configuration.CATEGORY_BLOCK, "Glass", 1236).getInt(1236);
		NetherSoulGlassPaneBlockid = config.getBlock(Configuration.CATEGORY_BLOCK, "GlassPane", 1237).getInt(1237);
		NetherPuddleBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Puddle", 1238).getInt(1238);
		SoulWorkBenchBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulWorkbench", 1239).getInt(1239);
		NetherSoulBombBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulBomb", 1240).getInt(1240);
		NetherSoulDetectorBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulDetector", 1241).getInt(1241);
		NetherSoulBlockerBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulBlocker", 1242).getInt(1242);
		NetherSoulFurnaceBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulFurnace", 1243).getInt(1243);
		NetherSoulSiphonBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulSiphon", 1244).getInt(1244);
		NetherSkyBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SkyBlock", 1245).getInt(1245);
		NetherWoodPuddleBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherWoodPuddles", 1246).getInt(1246);

		NetherOreIngotItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherIngots", 5200).getInt(5200);
		NetherDemonicBarHandleItemId = config.getItem(Configuration.CATEGORY_ITEM, "DemonicSwordHandle", 5201).getInt(5201);
		NetherObsidianSwordItemId = config.getItem(Configuration.CATEGORY_ITEM, "ObsidianSword", 5202).getInt(5202);
		NetherWoodStickItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherWoodStick", 5203).getInt(5203);
		NetherStoneBowlItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherStoneBowl", 5204).getInt(5204);
		NetherSoulGlassBottleItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulGlassBottle", 5205).getInt(5205);

		NetherObsidianSwordAcidItemId = config.getItem(Configuration.CATEGORY_ITEM, "ObsidianSwordAcid", 5206).getInt(5206);
		NetherObsidianSwordDeathItemId = config.getItem(Configuration.CATEGORY_ITEM, "ObsidianSwordDeath", 5207).getInt(5207);

		NetherPotionBottleItemId = config.getItem(Configuration.CATEGORY_ITEM, "PotionBottle", 5208).getInt(5208);
		NetherStonePotionBowlItemId = config.getItem(Configuration.CATEGORY_ITEM, "PotionBowl", 5209).getInt(5209);

		NetherSoulglassSwordItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSword", 5210).getInt(5210);
		NetherSoulglassSwordAcidItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSwordAcid", 5211).getInt(5211);
		NetherSoulglassSwordDeathItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSwordDeath", 5212).getInt(5212);
		NetherSoulglassSwordHellfireItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulglassSwordHellfire", 5213).getInt(5213);

		NetherDiamondSwordItemId = config.getItem(Configuration.CATEGORY_ITEM, "DiamondSword", 5219).getInt(5219);
		NetherDiamondSwordAcidItemId = config.getItem(Configuration.CATEGORY_ITEM, "DiamondSwordAcid", 5220).getInt(5220);
		NetherDiamondSwordDeathItemId = config.getItem(Configuration.CATEGORY_ITEM, "DiamondSwordDeath", 5221).getInt(5221);

		NetherWoodCharcoalItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherWoodCharcoal", 5214).getInt(5214);
		SoulEnergyBottleItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyPotion", 5215).getInt(5215);
		NetherBowItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherBow", 5216).getInt(5216);
		TorchArrowItemId = config.getItem(Configuration.CATEGORY_ITEM, "TorchArrow", 5217).getInt(5217);

		// SoulEnergyLiquidId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyLiquidID", 5018).getInt();
		SoulEnergyLiquidItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyLiquidItemID", 5218).getInt(5218);

		SpawnSkeletonsAwayFromNetherFortresses = config.get(Configuration.CATEGORY_GENERAL, "SpawnSkeletonsAwayFromNetherFortresses", true).getBoolean(true);
		IncreaseNetherrackHardness = config.get(Configuration.CATEGORY_GENERAL, "IncreaseNetherrackHardness", true).getBoolean(true);
		SpawnBlazesNaturally = config.get(Configuration.CATEGORY_GENERAL, "SpawnBlazesNaturally", false).getBoolean(false);
		bShowCoreModMissingWarning = config.get(Configuration.CATEGORY_GENERAL, "ShowCoreModMissingWarning", true).getBoolean(true);
		
		bUseNetherOreDemonic = config.get("NetherOreGeneration", "Demonic", true).getBoolean(true);
		bUseNetherOreCoal = config.get("NetherOreGeneration", "NetherCoal", true).getBoolean(true);
		bUseNetherOreIron = config.get("NetherOreGeneration", "Iron", true).getBoolean(true);
		bUseNetherOreGold = config.get("NetherOreGeneration", "Gold", true).getBoolean(true);
		bUseNetherOreDiamond = config.get("NetherOreGeneration", "Diamond", true).getBoolean(true);
		bUseNetherOreEmerald = config.get("NetherOreGeneration", "Emerald", true).getBoolean(true);
		bUseNetherOreRedstone = config.get("NetherOreGeneration", "Redstone", true).getBoolean(true);
		bUseNetherOreObsidian = config.get("NetherOreGeneration", "Obsidian", true).getBoolean(true);
		bUseNetherOreLapis = config.get("NetherOreGeneration", "Lapis", true).getBoolean(true);
		bUseNetherOreCobblestone = config.get("NetherOreGeneration", "Cobblestone", true).getBoolean(true);

		//set and save 2 overrides for display purposes inside the config
		bOverrideBlockBreakable = config.get(Configuration.CATEGORY_GENERAL, "OverrideBlockBreakableClass", true).getBoolean(true);
		bOverrideBlockPane = config.get(Configuration.CATEGORY_GENERAL, "OverrideBlockPaneClass", true).getBoolean(true);
		bOverrideChunk = config.get(Configuration.CATEGORY_GENERAL, "OverrideChunkClass", true).getBoolean(true);

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
				+ NetherSoulGlassPaneBlockid + "," + NetherSoulBombBlockId).getString();
		String[] tmp = tmpString.split(",");
		for (int i = 0; i < tmp.length; i++) {
			int intVal = Integer.parseInt(tmp[i].trim());
			if (intVal > 0 && intVal < 4096)
				NetherStuffsEventHook.lBlockSpawnListForced.add(intVal);
		}

		FMLLog.info("[NetherStuffs] Blocked Nether Spawns on Block IDs: %s", NetherStuffsEventHook.lBlockSpawnListForced.toString());
		config.save();
		
		if (ShowOreDistributions) {
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.demonicOre), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreCoal), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreIron), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreGold), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreDiamond), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreEmerald), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreRedstone), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreObsidian), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreLapis), 1);
			WorldGenDefaultMinable.arrMap.put(new String(NetherBlocks.netherOre.blockID + ":" + NetherOre.netherOreCobblestone), 1);
		}
	}

	@Init
	public void load(FMLInitializationEvent event) {
		// LiquidContainerData data = new LiquidContainerData(NetherItems.SoulEnergyLiquid, null, NetherStuffs.SoulSiphon.ContainerSoulSiphon);
		// LiquidContainerRegistry.registerLiquid(data);

		GameRegistry.registerBlock(NetherBlocks.NetherSoulGlass, "NetherSoulGlass");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulGlassPane, "NetherSoulGlassPane");
		GameRegistry.registerBlock(NetherBlocks.NetherDemonicFurnace, "NetherDemonicFurnace");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulFurnace, "NetherSoulFurnace");
		GameRegistry.registerBlock(NetherBlocks.netherSoulWorkBench, "NetherSoulWorkBench");

		if ((Loader.isModLoaded("NetherStuffsCore") || NetherStuffs.DevSetCoreModAvailable) && NetherStuffs.bOverrideChunk) {
			FMLLog.info("[NetherStuffs] SkyBlock is set available because NetherStuffsCore was found.");
			GameRegistry.registerBlock(NetherBlocks.skyblock, "NetherSkyBlock");
		}

		GameRegistry.registerBlock(NetherBlocks.netherOre, NetherOreItemBlock.class, "NetherOreItemBlock");
		GameRegistry.registerBlock(NetherBlocks.netherWood, NetherWoodItemBlock.class, "NetherWoodItemBlock");
		GameRegistry.registerBlock(NetherBlocks.netherPlank, NetherPlankItemBlock.class, "NetherPlankItemBlock");
		GameRegistry.registerBlock(NetherBlocks.netherLeaves, NetherLeavesItemBlock.class, "NetherLeavesItemBlock");
		GameRegistry.registerBlock(NetherBlocks.netherSapling, NetherSaplingItemBlock.class, "NetherSaplingItemBlock");
		GameRegistry.registerBlock(NetherBlocks.netherSoulBomb, SoulBombItemBlock.class, "NetherSoulBombItemBlock");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulDetector, SoulDetectorItemBlock.class, "NetherSoulDetectorItemBlock");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulBlocker, SoulBlockerItemBlock.class, "NetherSoulBlockerItemBlock");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulSiphon, SoulSiphonItemBlock.class, "NetherSoulSiphonItemBlock");
		GameRegistry.registerBlock(NetherBlocks.netherWoodPuddle, NetherWoodPuddleItemBlock.class, "NetherWoodPuddleItemBlock");

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
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.NetherSoulSiphon, "pickaxe", 1);

		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherSoulWorkBench, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherWood, "axe", 2);
		MinecraftForge.setBlockHarvestLevel(NetherBlocks.netherWoodPuddle, "axe", 2);

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
		GameRegistry.registerTileEntity(TileNetherWoodPuddle.class, "tileEntityNetherWood");

		GameRegistry.registerFuelHandler(new NetherStuffsFuel());
		EntityRegistry.registerModEntity(EntityTorchArrow.class, "TorchArrow", 1, instance, 128, 3, true);

		OreDictionary.registerOre("oreDemonic", new ItemStack(NetherBlocks.netherOre, 1, NetherOre.demonicOre));
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

		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.demonicOre, (new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1)
				.add(EnumTag.EVIL, 4).add(EnumTag.METAL, 2).add(EnumTag.FLUX, 2));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreCoal,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.FIRE, 3));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreIron,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.METAL, 6));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherStone, (new ObjectTags()).add(EnumTag.ROCK, 2));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreGold,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.METAL, 6).add(EnumTag.VALUABLE, 4));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreDiamond,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.VALUABLE, 4));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreEmerald,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.VALUABLE, 2));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreRedstone, (new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.POWER, 1).add(EnumTag.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreObsidian,
				(new ObjectTags()).add(EnumTag.ROCK, 5).add(EnumTag.DESTRUCTION, 1).add(EnumTag.DARK, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreLapis,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.VALUABLE, 4));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreCobblestone, (new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1));

		ThaumcraftApi.registerObjectTag(NetherBlocks.netherWood.blockID, NetherWood.hellfire, (new ObjectTags()).add(EnumTag.FIRE, 4).add(EnumTag.WOOD, 8).add(EnumTag.EVIL, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherWood.blockID, NetherWood.death, (new ObjectTags()).add(EnumTag.DEATH, 4).add(EnumTag.WOOD, 8).add(EnumTag.EVIL, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherWood.blockID, NetherWood.acid, (new ObjectTags()).add(EnumTag.POISON, 4).add(EnumTag.WOOD, 8).add(EnumTag.EVIL, 1));
		
		//ThaumcraftApi.registerObjectTag(NetherBlocks.NetherSoulGlass, -1, (new ObjectTags()).add(EnumTag.CRYSTAL, 2).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulGlass.blockID, -1, (new ObjectTags()).add(EnumTag.CRYSTAL, 3).add(EnumTag.SPIRIT, 1));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulGlassPane.blockID, -1, (new ObjectTags()).add(EnumTag.CRYSTAL, 1));
		
		ThaumcraftApi
				.registerObjectTag(NetherBlocks.netherSapling.blockID, NetherSapling.hellfire, (new ObjectTags()).add(EnumTag.FIRE, 1).add(EnumTag.WOOD, 2).add(EnumTag.EVIL, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherSapling.blockID, NetherSapling.death, (new ObjectTags()).add(EnumTag.DEATH, 1).add(EnumTag.WOOD, 2).add(EnumTag.EVIL, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherSapling.blockID, NetherSapling.acid, (new ObjectTags()).add(EnumTag.POISON, 1).add(EnumTag.WOOD, 2).add(EnumTag.EVIL, 1));

		ThaumcraftApi.registerComplexObjectTag(NetherItems.NetherOreIngot.itemID, -1, (new ObjectTags()).add(EnumTag.EVIL, 1).add(EnumTag.METAL, 4).add(EnumTag.FLUX, 1));
		
		ThaumcraftApi.registerObjectTag(NetherItems.NetherStonePotionBowl.itemID, 0, (new ObjectTags()).add(EnumTag.EVIL, 1).add(EnumTag.FIRE, 5));
		ThaumcraftApi.registerObjectTag(NetherItems.NetherStonePotionBowl.itemID, 1, (new ObjectTags()).add(EnumTag.EVIL, 1).add(EnumTag.POISON, 5));
		ThaumcraftApi.registerObjectTag(NetherItems.NetherStonePotionBowl.itemID, 2, (new ObjectTags()).add(EnumTag.EVIL, 1).add(EnumTag.DEATH, 5));

		ThaumcraftApi.registerObjectTag(NetherItems.NetherPotionBottle.itemID, NetherPotionBottle.hellfire, (new ObjectTags()).add(EnumTag.EVIL, 2).add(EnumTag.FIRE, 10));
		ThaumcraftApi.registerObjectTag(NetherItems.NetherPotionBottle.itemID, NetherPotionBottle.acid, (new ObjectTags()).add(EnumTag.EVIL, 2).add(EnumTag.POISON, 10));
		ThaumcraftApi.registerObjectTag(NetherItems.NetherPotionBottle.itemID, NetherPotionBottle.death, (new ObjectTags()).add(EnumTag.EVIL, 2).add(EnumTag.DEATH, 10));
		
		ThaumcraftApi.registerObjectTag(NetherItems.SoulEnergyBottle.itemID, SoulEnergyBottle.small, (new ObjectTags()).add(EnumTag.EVIL, 2).add(EnumTag.POWER, 2).add(EnumTag.SPIRIT, 4));
		ThaumcraftApi.registerObjectTag(NetherItems.SoulEnergyBottle.itemID, SoulEnergyBottle.medium, (new ObjectTags()).add(EnumTag.EVIL, 3).add(EnumTag.POWER, 3).add(EnumTag.SPIRIT, 8));
		ThaumcraftApi.registerObjectTag(NetherItems.SoulEnergyBottle.itemID, SoulEnergyBottle.large, (new ObjectTags()).add(EnumTag.EVIL, 4).add(EnumTag.POWER, 4).add(EnumTag.SPIRIT, 16));
		
		ThaumcraftApi.registerComplexObjectTag(NetherItems.NetherSoulGlassBottleItem.itemID, -1, (new ObjectTags()).add(EnumTag.CRYSTAL, 1).add(EnumTag.VOID, 1));
		ThaumcraftApi.registerComplexObjectTag(NetherItems.NetherStoneBowl.itemID, -1, (new ObjectTags()).add(EnumTag.VOID, 1));
		
		ThaumcraftApi.registerObjectTag(NetherBlocks.NetherSoulFurnace.blockID, -1, (new ObjectTags()).add(EnumTag.TRAP, 2).add(EnumTag.EVIL, 5).add(EnumTag.SPIRIT, 5));
		ThaumcraftApi.registerObjectTag(NetherBlocks.NetherDemonicFurnace.blockID, -1, (new ObjectTags()).add(EnumTag.TRAP, 1).add(EnumTag.EVIL, 2).add(EnumTag.SPIRIT, 2));
		
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherSoulBomb.blockID, -1, (new ObjectTags()).add(EnumTag.TRAP, 5).add(EnumTag.EVIL, 5).add(EnumTag.SPIRIT, 5).add(EnumTag.DEATH, 3));

		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk1, (new ObjectTags()).add(EnumTag.MECHANISM, 2).add(EnumTag.CONTROL, 2).add(EnumTag.VISION, 1));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk2, (new ObjectTags()).add(EnumTag.MECHANISM, 4).add(EnumTag.CONTROL, 4).add(EnumTag.VISION, 2));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk3, (new ObjectTags()).add(EnumTag.MECHANISM, 6).add(EnumTag.CONTROL, 6).add(EnumTag.VISION, 3));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk4, (new ObjectTags()).add(EnumTag.MECHANISM, 8).add(EnumTag.CONTROL, 8).add(EnumTag.VISION, 4));
		
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk1, (new ObjectTags()).add(EnumTag.TRAP, 3).add(EnumTag.WEAPON, 3).add(EnumTag.SPIRIT, 3).add(EnumTag.VOID, 1));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk2, (new ObjectTags()).add(EnumTag.TRAP, 5).add(EnumTag.WEAPON, 5).add(EnumTag.SPIRIT, 6).add(EnumTag.VOID, 2));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk3, (new ObjectTags()).add(EnumTag.TRAP, 7).add(EnumTag.WEAPON, 7).add(EnumTag.SPIRIT, 9).add(EnumTag.VOID, 3));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk4, (new ObjectTags()).add(EnumTag.TRAP, 10).add(EnumTag.WEAPON, 10).add(EnumTag.SPIRIT, 12).add(EnumTag.VOID, 4));
		
		
		
		// ThaumcraftApi.registerObjectTag(NetherItems.NetherWoodStick, -1, (new ObjectTags()).add(EnumTag.WOOD, 1).add())

	}

	private void registerWorldGenerators() {
		if(bUseNetherOreDemonic)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.demonicOre, 27));
		if(bUseNetherOreCoal)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 16, NetherOre.netherOreCoal, 20));
		if(bUseNetherOreIron)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreIron, 32));
		if(bUseNetherOreGold)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreGold, 8));
		if(bUseNetherOreDiamond)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 7, NetherOre.netherOreDiamond, 3));
		if(bUseNetherOreEmerald)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 7, NetherOre.netherOreEmerald, 3));
		if(bUseNetherOreRedstone)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 7, NetherOre.netherOreRedstone, 14));
		if(bUseNetherOreObsidian)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreObsidian, 20));
		if(bUseNetherOreLapis)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreLapis, 4));
		if(bUseNetherOreCobblestone)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 13, NetherOre.netherOreCobblestone, 20));

		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsTrees(true, 4, false));
	}


	private void initRecipes() {
		initDefaultMinecraftRecipes();

		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.demonicOre, new ItemStack(NetherItems.NetherOreIngot, 1, 0), 1.0F);
		DemonicFurnaceRecipes.smelting().addSmelting(Block.slowSand.blockID, 0, new ItemStack(NetherBlocks.NetherSoulGlass, 1, 0), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherWood.blockID, NetherWood.hellfire, new ItemStack(NetherItems.NetherWoodCharcoal), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreCoal,
				new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.charcoal), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreIron, new ItemStack(Item.ingotIron, 1), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreGold, new ItemStack(Item.ingotGold, 1), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreDiamond, new ItemStack(Item.diamond, 1), 0.5F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreEmerald, new ItemStack(Item.emerald, 1), 0.5F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreRedstone, new ItemStack(Item.redstone, 4), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreObsidian, new ItemStack(Block.obsidian, 1, 0), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreLapis, new ItemStack(Item.dyePowder, 4, 4), 0.25F);

		Item netherBrick = Item.field_94584_bZ;
		Item netherQuartz = Item.field_94583_ca;
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), true, new Object[] { "I#I", "#W#", "I#I", '#', new ItemStack(netherBrick, 1),
						'W', new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "I#I", "#W#", "I#I", '#', new ItemStack(netherBrick, 1), 'W',
				new ItemStack(Block.workbench), 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#', new ItemStack(netherBrick, 1), 'W',
				new ItemStack(Block.workbench), 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0) });

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', new ItemStack(netherBrick, 1) });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.torchArrow, 1), new Object[] { new ItemStack(Item.arrow, 1), new ItemStack(Block.torchWood, 1, 0) });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#', netherBrick, 'W',
						new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		CraftingManager.getInstance().getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', netherBrick }));

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherWoodStick, 4), new Object[] { "#", "#", '#', NetherBlocks.netherPlank });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.NetherSoulBlocker, 1, SoulBlocker.NetherSoulBlocker), new Object[] { "IPI", "SBS", "XXX", 'I', "ingotDemonic", 'P',
						NetherBlocks.netherPlank, 'S', NetherItems.NetherWoodStick, 'B', NetherItems.NetherPotionBottle, 'X', netherBrick }));

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

		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 8), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal), '#', Item.stick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 8), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal), '#', Item.stick });

		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 10), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.charcoal), '#',
				NetherItems.NetherWoodStick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 10), new Object[] { "X", "#", 'X', new ItemStack(NetherItems.NetherWoodCharcoal, 1, NetherWoodCharcoal.coal), '#',
				NetherItems.NetherWoodStick });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.NetherWoodCharcoal, 1), new Object[]{new ItemStack(Item.coal, 1, 1), new ItemStack(Item.gunpowder, 1)});
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherItems.NetherDemonicBarHandle, 1), new Object[] { "NIN", " S ", 'N', netherBrick, 'I', "ingotDemonic", 'S',
						NetherItems.NetherWoodStick }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherItems.NetherStoneBowl, 3), new Object[] { "N N", " N ", 'N', netherBrick }));

		/*
		 * GameRegistry.addRecipe(new ItemStack(NetherItems.NetherDemonicBarHandle, 1), new Object[] { "NIN", " S ", 'N', CompatItem.netherBrick, 'I', new
		 * ItemStack(NetherItems.NetherOreIngot, 1, 0), 'S', NetherItems.NetherWoodStick });
		 */

		// GameRegistry.addRecipe(new ItemStack(NetherItems.NetherStoneBowl, 3), new Object[] { "N N", " N ", 'N', CompatItem.netherBrick });

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.NetherSoulGlassPane, 16), new Object[] { "###", "###", '#', NetherBlocks.NetherSoulGlass });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulGlassBottleItem, 3), new Object[] { "# #", " # ", '#', NetherBlocks.NetherSoulGlass });
		GameRegistry.addRecipe(new ItemStack(Item.flintAndSteel, 1), new Object[] { "BB", "QQ", 'B', netherBrick, 'Q', netherQuartz });

		FurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreCoal, new ItemStack(Item.coal, 1), 0.2F);

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

		/**
		 * Diamond Swords
		 */
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherDiamondSword, 1), new Object[] { " D ", " D ", " H ", 'D', Item.diamond, 'H', NetherItems.NetherDemonicBarHandle });

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherDiamondSwordAcid, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.acid), 'S', NetherItems.NetherDiamondSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherDiamondSwordDeath, 1), new Object[] { "#S#", '#',
				new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death), 'S', NetherItems.NetherDiamondSword });

		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherDiamondSwordAcid, 1), new Object[] { " # ", "#S#", " # ", '#',
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid), 'S', NetherItems.NetherDiamondSword });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherDiamondSwordDeath, 1), new Object[] { " # ", "#S#", " # ", '#',
				new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death), 'S', NetherItems.NetherDiamondSword });

	}

	/**
	 * Returns Default Minecraft Items from Mod Items
	 */
	private void initDefaultMinecraftRecipes() {
		GameRegistry.addRecipe(new ItemStack(Item.bed, 1), new Object[] { "###", "XXX", '#', Block.cloth, 'X', NetherBlocks.netherPlank });
		GameRegistry.addRecipe(new ItemStack(Block.chest), new Object[] { "###", "# #", "###", '#', NetherBlocks.netherPlank });
		GameRegistry.addRecipe(new ItemStack(Block.workbench), new Object[] { "##", "##", '#', NetherBlocks.netherPlank });

		Item netherBrick = Item.field_94584_bZ;
		
		GameRegistry.addShapelessRecipe(new ItemStack(netherBrick), new Object[] { new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone) });
		GameRegistry.addShapelessRecipe(new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone), new Object[] { netherBrick });

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
		
		for (int i = 0; i < NetherWoodPuddleItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherWoodPuddle." + NetherWoodPuddleItemBlock.blockNames[i] + ".name", NetherWoodPuddleItemBlock.blockDisplayNames[i]);
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

		for (int i = 0; i < SoulDetectorItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulDetector." + SoulDetectorItemBlock.blockNames[i] + ".name", SoulDetectorItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < SoulBlockerItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulBlocker." + SoulBlockerItemBlock.blockNames[i] + ".name", SoulBlockerItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < SoulSiphonItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulSiphon." + SoulSiphonItemBlock.blockNames[i] + ".name", SoulSiphonItemBlock.blockDisplayNames[i]);
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

		LanguageRegistry.instance().addStringLocalization("item.NetherBow.name", "Torch Bow");
		LanguageRegistry.instance().addStringLocalization("item.torchArrow.name", "Torch Arrow");
		LanguageRegistry.instance().addStringLocalization("item.SoulEnergyLiquidItem.name", "Soul Energy Liquid");
	}

	@PostInit
	public static void postInit(FMLPostInitializationEvent event) {
		FMLLog.info("[NetherStuffs] postInit");
	}
}
