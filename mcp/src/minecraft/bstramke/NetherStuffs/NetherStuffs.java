package bstramke.NetherStuffs;

import java.util.HashMap;
import java.util.List;

import mods.tinker.tconstruct.crafting.LiquidCasting;
import mods.tinker.tconstruct.crafting.Smeltery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Blocks.NetherDoubleSlabItemBlock;
import bstramke.NetherStuffs.Blocks.NetherFence;
import bstramke.NetherStuffs.Blocks.NetherFenceGate;
import bstramke.NetherStuffs.Blocks.NetherHalfSlabItemBlock;
import bstramke.NetherStuffs.Blocks.NetherLeavesItemBlock;
import bstramke.NetherStuffs.Blocks.NetherOre;
import bstramke.NetherStuffs.Blocks.NetherOreItemBlock;
import bstramke.NetherStuffs.Blocks.NetherPlank;
import bstramke.NetherStuffs.Blocks.NetherPlankItemBlock;
import bstramke.NetherStuffs.Blocks.NetherSapling;
import bstramke.NetherStuffs.Blocks.NetherSaplingItemBlock;
import bstramke.NetherStuffs.Blocks.NetherSlab;
import bstramke.NetherStuffs.Blocks.NetherStairs;
import bstramke.NetherStuffs.Blocks.NetherWood;
import bstramke.NetherStuffs.Blocks.NetherWoodItemBlock;
import bstramke.NetherStuffs.Blocks.NetherWoodPuddleItemBlock;
import bstramke.NetherStuffs.Blocks.SoulBlocker;
import bstramke.NetherStuffs.Blocks.SoulBlockerItemBlock;
import bstramke.NetherStuffs.Blocks.SoulBombItemBlock;
import bstramke.NetherStuffs.Blocks.SoulCondenser;
import bstramke.NetherStuffs.Blocks.SoulCondenserItemBlock;
import bstramke.NetherStuffs.Blocks.SoulDetector;
import bstramke.NetherStuffs.Blocks.SoulDetectorItemBlock;
import bstramke.NetherStuffs.Blocks.SoulEngine;
import bstramke.NetherStuffs.Blocks.SoulSiphon;
import bstramke.NetherStuffs.Blocks.SoulSiphonItemBlock;
import bstramke.NetherStuffs.Client.ClientPacketHandler;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.GuiHandler;
import bstramke.NetherStuffs.Common.NetherStuffsFuel;
import bstramke.NetherStuffs.Common.NetherWoodMaterial;
import bstramke.NetherStuffs.Common.ServerPacketHandler;
import bstramke.NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import bstramke.NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import bstramke.NetherStuffs.Items.NetherGear;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.NetherPotionBottle;
import bstramke.NetherStuffs.Items.NetherStonePotionBowl;
import bstramke.NetherStuffs.Items.NetherWoodCharcoal;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import bstramke.NetherStuffs.NetherWoodPuddle.TileNetherWoodPuddle;
import bstramke.NetherStuffs.SoulBlocker.TileSoulBlocker;
import bstramke.NetherStuffs.SoulBomb.EntitySoulBombPrimed;
import bstramke.NetherStuffs.SoulCondenser.TileSoulCondenser;
import bstramke.NetherStuffs.SoulDetector.TileSoulDetector;
import bstramke.NetherStuffs.SoulEngine.SoulEngineFuel;
import bstramke.NetherStuffs.SoulEngine.TileSoulEngine;
import bstramke.NetherStuffs.SoulFurnace.TileSoulFurnace;
import bstramke.NetherStuffs.SoulSiphon.TileSoulSiphon;
import bstramke.NetherStuffs.SoulWorkBench.SoulWorkBenchRecipes;
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

@Mod(name = "NetherStuffs", version = "0.16.1", modid = "NetherStuffs")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "NetherStuffs" }, packetHandler = ServerPacketHandler.class))
public class NetherStuffs extends DummyModContainer {
	@Instance
	public static NetherStuffs instance = new NetherStuffs();

	private GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(clientSide = "bstramke.NetherStuffs.Client.ClientProxy", serverSide = "bstramke.NetherStuffs.Common.CommonProxy")
	public static CommonProxy proxy;

	public static boolean bBuildcraftAvailable = false;
	public static boolean bHarkenScytheAvailable = false;
	public static boolean bThaumcraftAvailable = false;
	public static boolean bTConstructAvailable = false;

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
	public static int NetherWoodPuddleBlockId;

	public static int SoulEngineBlockId = 0;

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
	public static int DemonicIngotLiquidItemId;
	public static int NetherGearItemId;

	public static int NetherSoulDetectorBlockId;
	public static int NetherSoulBlockerBlockId;
	public static int NetherSoulFurnaceBlockId;
	public static int NetherSoulSiphonBlockId;
	public static int NetherStairHellfireBlockId;
	public static int NetherStairAcidBlockId;
	public static int NetherStairDeathBlockId;
	public static int NetherHalfSlabBlockId;
	public static int NetherDoubleSlabBlockId;
	public static int SoulCondenserBlockId = 0;
	public static int NetherFenceGateNetherBricksBlockId;
	public static int NetherFenceGateHellfireBlockId;
	public static int NetherFenceGateAcidBlockId;
	public static int NetherFenceGateDeathBlockId;
	public static int NetherFenceHellfireBlockId;
	public static int NetherFenceAcidBlockId;
	public static int NetherFenceDeathBlockId;

	private static boolean SpawnSkeletonsAwayFromNetherFortresses;
	private static boolean IncreaseNetherrackHardness;
	private static boolean SpawnBlazesNaturally;
	public static boolean bShowCoreModMissingWarning;
	private static boolean bUseSoulEngineBlock;
	private static boolean bUseHarkenScytheCondenserBlock;
	private static boolean bUseThaumcraft;
	public static boolean bUseForestry;
	public static boolean bUseTConstruct;
	public static boolean bUseStairBlocks;
	public static boolean bUseSlabBlocks;
	public static boolean bUseFenceGates;
	public static boolean bUseFences;

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

	public static LiquidStack SoulEnergyLiquid;
	public static LiquidStack DemonicIngotLiquid;

	public static CreativeTabs tabNetherStuffs = new CreativeTabs("tabNetherStuffs") {
		public ItemStack getIconItemStack() {
			return new ItemStack(NetherBlocks.netherSoulWorkBench, 1, 0);
		}
	};

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
		NetherWoodPuddleBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherWoodPuddles", 1246).getInt(1246);

		config.addCustomCategoryComment(
				"ModCompatibility",
				"Here you can set if you want to use compatibility Blocks and or APIs inside NetherStuffs.\nDont worry, if these mods are not found the Blocks/APIs wont be loaded anyways even if they are set to true.\nBuildCraft = Soul Engine\nHarken Scythe = SoulenergyCondenser");
		bUseThaumcraft = config.get("ModCompatibility", "UseThaumcraft", true).getBoolean(true);
		bUseSoulEngineBlock = config.get("ModCompatibility", "UseBuildcraft", true).getBoolean(true);
		bUseHarkenScytheCondenserBlock = config.get("ModCompatibility", "UseHarkenScythe", true).getBoolean(true);
		bUseForestry = config.get("ModCompatibility", "UseForestry", true).getBoolean(true);
		bUseTConstruct = config.get("ModCompatibility", "Use Tinkers Construct", true).getBoolean(true);

		if (bUseSoulEngineBlock)
			SoulEngineBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulEngine", 1247).getInt(1247);

		if (bUseHarkenScytheCondenserBlock) {
			SoulCondenserBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulenergyCondenser", 1248).getInt(1248);
			TileSoulCondenser.nToHarkenScytheRate = config.get("ModCompatibility", "NetherStuffs SoulEnergy to Harken Scythe Souls", 250).getInt(250);
			TileSoulCondenser.nFromHarkenScytheRate = config.get("ModCompatibility", "HarkenScythe Souls to SoulEnergy Rate", 150).getInt(150);
		}

		bUseStairBlocks = config.get(Configuration.CATEGORY_GENERAL, "Use Stair Blocks", true).getBoolean(true);
		bUseSlabBlocks = config.get(Configuration.CATEGORY_GENERAL, "Use Slab Blocks", true).getBoolean(true);
		bUseFenceGates = config.get(Configuration.CATEGORY_GENERAL, "Use new Fence Gate Blocks", true).getBoolean(true);
		bUseFences = config.get(Configuration.CATEGORY_GENERAL, "Use new Fence Blocks", true).getBoolean(true);

		if (bUseStairBlocks) {
			NetherStairHellfireBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "HellfireStair", 1249).getInt(1249);
			NetherStairAcidBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "AcidStair", 1250).getInt(1250);
			NetherStairDeathBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "DeathStair", 1251).getInt(1251);
		}

		if (bUseSlabBlocks) {
			NetherHalfSlabBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "HalfSlab BlockId", 1252).getInt(1252);
			NetherDoubleSlabBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "DoubleSlab BlockId", 1253).getInt(1253);
		}
		
		if (bUseFenceGates) {
			NetherFenceGateNetherBricksBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherBrick Fence Gate", 1254).getInt(1254);
			NetherFenceGateHellfireBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Hellfire Fence Gate", 1255).getInt(1255);
			NetherFenceGateAcidBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Acid Fence Gate", 1256).getInt(1256);
			NetherFenceGateDeathBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Death Fence Gate", 1257).getInt(1257);
		}
		
		if(bUseFences) {
			NetherFenceHellfireBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Hellfire Fence", 1258).getInt(1258);
			NetherFenceAcidBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Acid Fence", 1259).getInt(1259);
			NetherFenceDeathBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Death Fence", 1260).getInt(1260);
		}

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

		NetherWoodCharcoalItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherWoodCharcoal", 5214).getInt(5214);
		SoulEnergyBottleItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyPotion", 5215).getInt(5215);
		NetherBowItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherBow", 5216).getInt(5216);
		TorchArrowItemId = config.getItem(Configuration.CATEGORY_ITEM, "TorchArrow", 5217).getInt(5217);
		SoulEnergyLiquidItemId = config.getItem(Configuration.CATEGORY_ITEM, "SoulEnergyLiquidItemID", 5218).getInt(5218);

		NetherDiamondSwordItemId = config.getItem(Configuration.CATEGORY_ITEM, "DiamondSword", 5219).getInt(5219);
		NetherDiamondSwordAcidItemId = config.getItem(Configuration.CATEGORY_ITEM, "DiamondSwordAcid", 5220).getInt(5220);
		NetherDiamondSwordDeathItemId = config.getItem(Configuration.CATEGORY_ITEM, "DiamondSwordDeath", 5221).getInt(5221);

		NetherGearItemId = config.getItem(Configuration.CATEGORY_ITEM, "Gears", 5222).getInt(5222);
		DemonicIngotLiquidItemId = config.getItem(Configuration.CATEGORY_ITEM, "DemonicIngotLiquidItemId", 5223).getInt(5223);

		SpawnSkeletonsAwayFromNetherFortresses = config.get(Configuration.CATEGORY_GENERAL, "SpawnSkeletonsAwayFromNetherFortresses", true).getBoolean(true);
		IncreaseNetherrackHardness = config.get(Configuration.CATEGORY_GENERAL, "IncreaseNetherrackHardness", true).getBoolean(true);
		SpawnBlazesNaturally = config.get(Configuration.CATEGORY_GENERAL, "SpawnBlazesNaturally", false).getBoolean(false);

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

		String tmpString = config.get(
				Configuration.CATEGORY_GENERAL,
				"BlockIDNetherSpawningBlacklist",
				NetherLeavesBlockId + "," + NetherSoulDetectorBlockId + "," + NetherSoulBlockerBlockId + "," + NetherSoulFurnaceBlockId + "," + SoulWorkBenchBlockId + ","
						+ NetherDemonicFurnaceBlockId + "," + NetherSoulGlassBlockid + "," + NetherSoulGlassPaneBlockid + "," + NetherSoulBombBlockId + "," + SoulEngineBlockId + ","
						+ SoulCondenserBlockId).getString();
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

	private void initHarkenScytheCompatibility() {
		if (bUseHarkenScytheCondenserBlock == false)
			return;

		FMLLog.info("[NetherStuffs] adding Stuff for HarkenScythe use");
		// 90 SoulEnergy should be 1 Soul, but as its working differently i use 250 Energy = 1 Soul
		int nSoulVessel = 0;
		int nSoulKeeper = 0;
		int nEssenceVessel = 0;
		int nEssenceKeeper = 0;
		for (int itemID = 255; itemID < Item.itemsList.length; itemID++) {
			if (Item.itemsList[itemID] != null) {
				if (Item.itemsList[itemID].getUnlocalizedName().equalsIgnoreCase("item.HSSoulkeeper"))
					nSoulKeeper = itemID;
				else if (Item.itemsList[itemID].getUnlocalizedName().equalsIgnoreCase("item.HSSoulVessel"))
					nSoulVessel = itemID;
				else if (Item.itemsList[itemID].getUnlocalizedName().equalsIgnoreCase("item.HSEssenceKeeper"))
					nEssenceKeeper = itemID;
				else if (Item.itemsList[itemID].getUnlocalizedName().equalsIgnoreCase("item.HSEssenceVessel"))
					nEssenceVessel = itemID;

				if (nSoulVessel != 0 && nSoulKeeper != 0 && nEssenceKeeper != 0 && nEssenceVessel != 0)
					break;
			}
		}

		if (nSoulVessel == 0 || nSoulKeeper == 0 || nEssenceKeeper == 0 || nEssenceVessel == 0) {
			FMLLog.warning("[NetherStuffs] Could not find HarkenScythe items - Soulenergy Condenser disabled");
			return;
		}

		Block SoulCondenser = new SoulCondenser(SoulCondenserBlockId, nSoulKeeper, nSoulVessel, nEssenceKeeper, nEssenceVessel).setUnlocalizedName("SoulCondenser").setHardness(0.5F)
				.setResistance(5.0F);
		GameRegistry.registerBlock(SoulCondenser, SoulCondenserItemBlock.class, "SoulCondenser");
		GameRegistry.registerTileEntity(TileSoulCondenser.class, "tileEntitySoulCondenser");

		SoulWorkBenchRecipes.instance.addRecipe(new ItemStack(SoulCondenser, 1, 0), 250, new Object[] { "III", "G G", "GPG", 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0),
				'G', new ItemStack(Block.ice, 1, 0), 'P', new ItemStack(Block.pistonBase, 1, 0) });

		SoulWorkBenchRecipes.instance.addRecipe(new ItemStack(SoulCondenser, 1, 1), 250, new Object[] { "III", "F F", "GPG", 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0),
				'F', new ItemStack(Item.flintAndSteel), 'G', new ItemStack(nEssenceKeeper, 1, 0), 'P', new ItemStack(Block.pistonBase, 1, 0) });
	}

	private void initTConstruct() {
		if (bUseTConstruct == false)
			return;

		FMLLog.info("[NetherStuffs] Trying to register Ores for TConstruct Smelter Recipes");

		LiquidStack liquid = LiquidDictionary.getLiquid("Molten Iron", 216); // actually 1.5 ingots
		if (liquid != null)
			Smeltery.instance.addMelting(NetherBlocks.netherOre, NetherOre.netherOreIron, 800, liquid);

		liquid = LiquidDictionary.getLiquid("Molten Gold", 216); // actually 1.5 ingots
		if (liquid != null)
			Smeltery.instance.addMelting(NetherBlocks.netherOre, NetherOre.netherOreGold, 800, liquid);

		liquid = LiquidDictionary.getLiquid("Molten Obsidian", 216); // actually 1.5 ingots
		if (liquid != null)
			Smeltery.instance.addMelting(NetherBlocks.netherOre, NetherOre.netherOreObsidian, 800, liquid);

		liquid = LiquidDictionary.getLiquid("Molten DemonicIngot", 216); // actually 1.5 ingots
		if (liquid != null) {
			int nIngotPatternItemId = 0;
			for (int itemID = 255; itemID < Item.itemsList.length; itemID++) {
				if (Item.itemsList[itemID] != null) {
					if (Item.itemsList[itemID].getUnlocalizedName().equalsIgnoreCase("item.tconstruct.MetalPattern")) {
						Smeltery.instance.addMelting(NetherBlocks.netherOre, NetherOre.demonicOre, 800, liquid);
						nIngotPatternItemId = itemID;
						liquid = LiquidDictionary.getLiquid("Molten DemonicIngot", 144);
						LiquidCasting.addCastingRecipe(new ItemStack(NetherItems.NetherOreIngot, 1, 0), liquid, new ItemStack(nIngotPatternItemId, 1, 0), 100);
						break;
					}
				}
			}
		}
	}

	private void initDecorativeBlocks() {
		
		if(bUseFences) {
			int nNetherFenceId = Block.netherFence.blockID;
			Block.blocksList[nNetherFenceId] = null;
			(new NetherFence(nNetherFenceId, "netherBrick", Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("netherFence");
			NetherBlocks.FenceHellfire =  (new NetherFence(NetherFenceHellfireBlockId, "netherBrick", NetherWoodMaterial.netherWood)).setUnlocalizedName("NetherFenceHellfire");
			NetherBlocks.FenceAcid =  (new NetherFence(NetherFenceAcidBlockId, "netherBrick", NetherWoodMaterial.netherWood)).setUnlocalizedName("NetherFenceAcid");
			NetherBlocks.FenceDeath =  (new NetherFence(NetherFenceDeathBlockId, "netherBrick", NetherWoodMaterial.netherWood)).setUnlocalizedName("NetherFenceDeath");
			GameRegistry.registerBlock(NetherBlocks.FenceHellfire, "NetherFenceHellfireItemBlock");
			GameRegistry.registerBlock(NetherBlocks.FenceAcid, "NetherFenceAcidItemBlock");
			GameRegistry.registerBlock(NetherBlocks.FenceDeath, "NetherFenceDeathItemBlock");
			
			LanguageRegistry.instance().addStringLocalization("tile.NetherFenceHellfire.name", "Hellfirewood Fence");
			LanguageRegistry.instance().addStringLocalization("tile.NetherFenceAcid.name", "Acidwood Fence");
			LanguageRegistry.instance().addStringLocalization("tile.NetherFenceDeath.name", "Deathwood Fence");
		}
		
		if(bUseFenceGates) {		
			NetherBlocks.FenceGateNetherBricks = new NetherFenceGate(NetherFenceGateNetherBricksBlockId).setUnlocalizedName("NetherBrickFenceGate");
			NetherBlocks.FenceGateHellfire = new NetherFenceGate(NetherFenceGateHellfireBlockId).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("HellfireFenceGate");
			NetherBlocks.FenceGateAcid = new NetherFenceGate(NetherFenceGateAcidBlockId).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("AcidFenceGate");
			NetherBlocks.FenceGateDeath = new NetherFenceGate(NetherFenceGateDeathBlockId).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("DeathFenceGate");
			
			GameRegistry.registerBlock(NetherBlocks.FenceGateNetherBricks, "NetherFenceGateBrickItemBlock");
			GameRegistry.registerBlock(NetherBlocks.FenceGateHellfire, "NetherFenceGateHellfireItemBlock");
			GameRegistry.registerBlock(NetherBlocks.FenceGateAcid, "NetherFenceGateAcidItemBlock");
			GameRegistry.registerBlock(NetherBlocks.FenceGateDeath, "NetherFenceGateDeathItemBlock");
			LanguageRegistry.instance().addStringLocalization("tile.NetherBrickFenceGate.name", "Netherbrick Fence Gate");
			LanguageRegistry.instance().addStringLocalization("tile.HellfireFenceGate.name", "Hellfirewood Fence Gate");
			LanguageRegistry.instance().addStringLocalization("tile.AcidFenceGate.name", "Acidwood Fence Gate");
			LanguageRegistry.instance().addStringLocalization("tile.DeathFenceGate.name", "Deathwood Fence Gate");
		}
		
		if (bUseStairBlocks) {
			NetherBlocks.StairAcid = new NetherStairs(NetherStairAcidBlockId, NetherBlocks.netherPlank, NetherPlank.acid).setUnlocalizedName("StairAcid");
			NetherBlocks.StairHellfire = new NetherStairs(NetherStairHellfireBlockId, NetherBlocks.netherPlank, NetherPlank.hellfire).setUnlocalizedName("StairHellfire");
			NetherBlocks.StairDeath = new NetherStairs(NetherStairDeathBlockId, NetherBlocks.netherPlank, NetherPlank.death).setUnlocalizedName("StairDeath");
			GameRegistry.registerBlock(NetherBlocks.StairAcid, "NetherStairAcidItemBlock");
			GameRegistry.registerBlock(NetherBlocks.StairHellfire, "NetherStairHellfireItemBlock");
			GameRegistry.registerBlock(NetherBlocks.StairDeath, "NetherStairDeathItemBlock");

			MinecraftForge.setBlockHarvestLevel(NetherBlocks.StairAcid, "axe", 1);
			MinecraftForge.setBlockHarvestLevel(NetherBlocks.StairHellfire, "axe", 1);
			MinecraftForge.setBlockHarvestLevel(NetherBlocks.StairDeath, "axe", 1);

			GameRegistry.addRecipe(new ItemStack(NetherBlocks.StairAcid, 4), new Object[] { "#  ", "## ", "###", '#', new ItemStack(NetherBlocks.netherPlank, 1, NetherPlank.acid) });
			GameRegistry.addRecipe(new ItemStack(NetherBlocks.StairHellfire, 4), new Object[] { "#  ", "## ", "###", '#',
					new ItemStack(NetherBlocks.netherPlank, 1, NetherPlank.hellfire) });
			GameRegistry
					.addRecipe(new ItemStack(NetherBlocks.StairDeath, 4), new Object[] { "#  ", "## ", "###", '#', new ItemStack(NetherBlocks.netherPlank, 1, NetherPlank.death) });

			LanguageRegistry.instance().addStringLocalization("tile.StairAcid.name", "Acid Wood Stairs");
			LanguageRegistry.instance().addStringLocalization("tile.StairHellfire.name", "Hellfire Wood Stairs");
			LanguageRegistry.instance().addStringLocalization("tile.StairDeath.name", "Death Wood Stairs");

			OreDictionary.registerOre("stairWood", NetherBlocks.StairAcid);
			OreDictionary.registerOre("stairWood", NetherBlocks.StairHellfire);
			OreDictionary.registerOre("stairWood", NetherBlocks.StairDeath);
		}

		if (bUseSlabBlocks) {

			NetherBlocks.HalfSlab = (NetherSlab) new NetherSlab(NetherHalfSlabBlockId, false).setUnlocalizedName("HalfSlab");
			NetherBlocks.DoubleSlab = (NetherSlab) new NetherSlab(NetherDoubleSlabBlockId, true).setUnlocalizedName("DoubleSlab");

			GameRegistry.registerBlock(NetherBlocks.HalfSlab, NetherHalfSlabItemBlock.class, "NetherSlabHalfItemBlock");
			GameRegistry.registerBlock(NetherBlocks.DoubleSlab, NetherDoubleSlabItemBlock.class, "NetherSlabDoubleItemBlock");

			MinecraftForge.setBlockHarvestLevel(NetherBlocks.HalfSlab, "axe", 1);
			MinecraftForge.setBlockHarvestLevel(NetherBlocks.DoubleSlab, "axe", 1);

			OreDictionary.registerOre("slabWood", new ItemStack(NetherBlocks.HalfSlab, 1, OreDictionary.WILDCARD_VALUE));

			for (int i = 0; i < NetherPlankItemBlock.blockNames.length; i++) {
				GameRegistry.addRecipe(new ItemStack(NetherBlocks.HalfSlab, 6, i), new Object[] { "###", '#', new ItemStack(NetherBlocks.netherPlank, 1, i) });

				MinecraftForge.setBlockHarvestLevel(NetherBlocks.HalfSlab, i, "axe", 1);
				MinecraftForge.setBlockHarvestLevel(NetherBlocks.DoubleSlab, i, "axe", 1);

				LanguageRegistry.instance().addStringLocalization("tile.HalfSlab." + NetherPlankItemBlock.blockNames[i] + ".name", NetherPlankItemBlock.blockNames[i] + " Slab");
				LanguageRegistry.instance()
						.addStringLocalization("tile.DoubleSlab." + NetherPlankItemBlock.blockNames[i] + ".name", NetherPlankItemBlock.blockNames[i] + " Doubleslab");
			}
		}

	}

	private void initThaumcraftAPI() {
		if (bUseThaumcraft == false)
			return;

		FMLLog.info("[NetherStuffs] adding Stuff for Thaumcraft use");

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
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreRedstone,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.POWER, 1).add(EnumTag.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreObsidian,
				(new ObjectTags()).add(EnumTag.ROCK, 5).add(EnumTag.DESTRUCTION, 1).add(EnumTag.DARK, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreLapis,
				(new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1).add(EnumTag.VALUABLE, 4));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherOre.blockID, NetherOre.netherOreCobblestone, (new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1));

		ThaumcraftApi.registerObjectTag(NetherBlocks.netherWood.blockID, NetherWood.hellfire, (new ObjectTags()).add(EnumTag.FIRE, 4).add(EnumTag.WOOD, 8).add(EnumTag.EVIL, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherWood.blockID, NetherWood.death, (new ObjectTags()).add(EnumTag.DEATH, 4).add(EnumTag.WOOD, 8).add(EnumTag.EVIL, 1));
		ThaumcraftApi.registerObjectTag(NetherBlocks.netherWood.blockID, NetherWood.acid, (new ObjectTags()).add(EnumTag.POISON, 4).add(EnumTag.WOOD, 8).add(EnumTag.EVIL, 1));

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

		ThaumcraftApi.registerObjectTag(NetherItems.SoulEnergyBottle.itemID, SoulEnergyBottle.small,
				(new ObjectTags()).add(EnumTag.EVIL, 2).add(EnumTag.POWER, 2).add(EnumTag.SPIRIT, 4));
		ThaumcraftApi.registerObjectTag(NetherItems.SoulEnergyBottle.itemID, SoulEnergyBottle.medium,
				(new ObjectTags()).add(EnumTag.EVIL, 3).add(EnumTag.POWER, 3).add(EnumTag.SPIRIT, 8));
		ThaumcraftApi.registerObjectTag(NetherItems.SoulEnergyBottle.itemID, SoulEnergyBottle.large,
				(new ObjectTags()).add(EnumTag.EVIL, 4).add(EnumTag.POWER, 4).add(EnumTag.SPIRIT, 16));

		ThaumcraftApi.registerComplexObjectTag(NetherItems.NetherSoulGlassBottleItem.itemID, -1, (new ObjectTags()).add(EnumTag.CRYSTAL, 1).add(EnumTag.VOID, 1));
		ThaumcraftApi.registerComplexObjectTag(NetherItems.NetherStoneBowl.itemID, -1, (new ObjectTags()).add(EnumTag.VOID, 1));

		ThaumcraftApi.registerObjectTag(NetherBlocks.NetherSoulFurnace.blockID, -1, (new ObjectTags()).add(EnumTag.TRAP, 2).add(EnumTag.EVIL, 5).add(EnumTag.SPIRIT, 5));
		ThaumcraftApi.registerObjectTag(NetherBlocks.NetherDemonicFurnace.blockID, -1, (new ObjectTags()).add(EnumTag.TRAP, 1).add(EnumTag.EVIL, 2).add(EnumTag.SPIRIT, 2));

		ThaumcraftApi.registerObjectTag(NetherBlocks.netherSoulBomb.blockID, -1,
				(new ObjectTags()).add(EnumTag.TRAP, 5).add(EnumTag.EVIL, 5).add(EnumTag.SPIRIT, 5).add(EnumTag.DEATH, 3));

		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk1,
				(new ObjectTags()).add(EnumTag.MECHANISM, 2).add(EnumTag.CONTROL, 2).add(EnumTag.VISION, 1));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk2,
				(new ObjectTags()).add(EnumTag.MECHANISM, 4).add(EnumTag.CONTROL, 4).add(EnumTag.VISION, 2));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk3,
				(new ObjectTags()).add(EnumTag.MECHANISM, 6).add(EnumTag.CONTROL, 6).add(EnumTag.VISION, 3));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulDetector.blockID, SoulDetector.mk4,
				(new ObjectTags()).add(EnumTag.MECHANISM, 8).add(EnumTag.CONTROL, 8).add(EnumTag.VISION, 4));

		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk1,
				(new ObjectTags()).add(EnumTag.TRAP, 3).add(EnumTag.WEAPON, 3).add(EnumTag.SPIRIT, 3).add(EnumTag.VOID, 1));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk2,
				(new ObjectTags()).add(EnumTag.TRAP, 5).add(EnumTag.WEAPON, 5).add(EnumTag.SPIRIT, 6).add(EnumTag.VOID, 2));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk3,
				(new ObjectTags()).add(EnumTag.TRAP, 7).add(EnumTag.WEAPON, 7).add(EnumTag.SPIRIT, 9).add(EnumTag.VOID, 3));
		ThaumcraftApi.registerComplexObjectTag(NetherBlocks.NetherSoulSiphon.blockID, SoulSiphon.mk4,
				(new ObjectTags()).add(EnumTag.TRAP, 10).add(EnumTag.WEAPON, 10).add(EnumTag.SPIRIT, 12).add(EnumTag.VOID, 4));
	}

	private void initBuildcraftStuff() {
		if (bUseSoulEngineBlock == false)
			return;

		FMLLog.info("[NetherStuffs] adding Stuff for Buildcraft use");
		Block SoulEngine = new SoulEngine(NetherStuffs.SoulEngineBlockId).setUnlocalizedName("NetherSoulEngine").setHardness(0.5F).setResistance(5.0F);
		Item NetherGear = new NetherGear(NetherGearItemId).setUnlocalizedName("NetherGear");

		GameRegistry.registerBlock(SoulEngine, "SoulEngine");
		SoulEngineFuel.fuels.add(new SoulEngineFuel(SoulEnergyLiquid, 1, 10000)); // 20000 = Lava
		GameRegistry.registerTileEntity(TileSoulEngine.class, "tileEntitySoulEnergyEngine");

		SoulWorkBenchRecipes.instance.addRecipe(new ItemStack(NetherGear, 1, 0), 50, " S ", "SIS", " S ", 'S', new ItemStack(NetherItems.NetherWoodStick, 1, 0), 'I', new ItemStack(
				NetherItems.NetherOreIngot, 1, 0));

		SoulWorkBenchRecipes.instance.addRecipe(new ItemStack(SoulEngine, 1, 0), 250, new Object[] { "III", " S ", "GPG", 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0), 'S',
				new ItemStack(NetherBlocks.NetherSoulGlass, 1, 0), 'G', new ItemStack(NetherGear, 1, 0), 'P', new ItemStack(Block.pistonBase, 1, 0) });
	}

	@Init
	public void load(FMLInitializationEvent event) {
		bBuildcraftAvailable = Loader.isModLoaded("BuildCraft|Core") && Loader.isModLoaded("BuildCraft|Energy");
		bHarkenScytheAvailable = Loader.isModLoaded("HarkenScythe_Core");
		bThaumcraftAvailable = Loader.isModLoaded("Thaumcraft");
		bTConstructAvailable = Loader.isModLoaded("TConstruct");

		NetherItems.init();

		GameRegistry.registerBlock(NetherBlocks.NetherSoulGlass, "NetherSoulGlass");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulGlassPane, "NetherSoulGlassPane");
		GameRegistry.registerBlock(NetherBlocks.NetherDemonicFurnace, "NetherDemonicFurnace");
		GameRegistry.registerBlock(NetherBlocks.NetherSoulFurnace, "NetherSoulFurnace");
		GameRegistry.registerBlock(NetherBlocks.netherSoulWorkBench, "NetherSoulWorkBench");

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
		EntityRegistry.registerModEntity(EntitySoulBombPrimed.class, "SoulBomb", 2, instance, 160, 10, true);

		OreDictionary.registerOre("oreDemonic", new ItemStack(NetherBlocks.netherOre, 1, NetherOre.demonicOre));
		OreDictionary.registerOre("ingotDemonic", new ItemStack(NetherItems.NetherOreIngot));
		OreDictionary.registerOre("logWood", new ItemStack(NetherBlocks.netherWood, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("plankWood", new ItemStack(NetherBlocks.netherPlank, 1, OreDictionary.WILDCARD_VALUE));

		SoulEnergyLiquid = LiquidDictionary.getOrCreateLiquid("SoulEnergy", new LiquidStack(NetherItems.SoulEnergyLiquidItem, LiquidContainerRegistry.BUCKET_VOLUME));
		DemonicIngotLiquid = LiquidDictionary.getOrCreateLiquid("Molten DemonicIngot", new LiquidStack(NetherItems.DemonicIngotLiquidItem, LiquidContainerRegistry.BUCKET_VOLUME));

		registerWorldGenerators();
		initRecipes();
		initDecorativeBlocks();

		initLanguageRegistry();
		proxy.registerRenderThings();
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);

		MinecraftForge.EVENT_BUS.register(new NetherStuffsEventHook());

		// set Netherrack hardness to different values
		if (IncreaseNetherrackHardness) {
			Block.netherrack.setHardness(1.5F);
			Block.netherrack.setResistance(2.0F);
		}
		/*
		 * This lets Skeletons Spawn away from NetherFortresses. Actual Idea by ErasmoGnome, made on the MinecraftForums: http://www.minecraftforum.net
		 * /topic/1493398-move-wither-skeletons-out- of-fortresses-over-125-supporters/
		 */
		if (SpawnSkeletonsAwayFromNetherFortresses)
			EntityRegistry.addSpawn(EntitySkeleton.class, 40, 4, 4, EnumCreatureType.monster, BiomeGenBase.hell);
		if (SpawnBlazesNaturally)
			EntityRegistry.addSpawn(EntityBlaze.class, 5, 1, 1, EnumCreatureType.monster, BiomeGenBase.hell);

		/**
		 * Init Mod Compatibility
		 */

		if (bBuildcraftAvailable)
			initBuildcraftStuff();

		if (bHarkenScytheAvailable)
			initHarkenScytheCompatibility();

		if (bThaumcraftAvailable)
			initThaumcraftAPI();

		if (bTConstructAvailable)
			initTConstruct();
	}

	private void registerWorldGenerators() {
		if (bUseNetherOreDemonic)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.demonicOre, 10));
		if (bUseNetherOreCoal)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 16, NetherOre.netherOreCoal, 10));
		if (bUseNetherOreIron)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreIron, 15));
		if (bUseNetherOreGold)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreGold, 4));
		if (bUseNetherOreDiamond)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 7, NetherOre.netherOreDiamond, 1));
		if (bUseNetherOreEmerald)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 7, NetherOre.netherOreEmerald, 1));
		if (bUseNetherOreRedstone)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 7, NetherOre.netherOreRedstone, 6));
		if (bUseNetherOreObsidian)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreObsidian, 8));
		if (bUseNetherOreLapis)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 8, NetherOre.netherOreLapis, 1));
		if (bUseNetherOreCobblestone)
			GameRegistry.registerWorldGenerator(new WorldGenDefaultMinable(NetherBlocks.netherOre.blockID, 13, NetherOre.netherOreCobblestone, 10));

		GameRegistry.registerWorldGenerator(new WorldGenNetherStuffsTrees(false, 4, false));
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

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), true, new Object[] { "I#I", "#W#", "I#I", '#', new ItemStack(Item.netherrackBrick, 1),
						'W', new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "I#I", "#W#", "I#I", '#', new ItemStack(Item.netherrackBrick, 1), 'W',
				new ItemStack(Block.workbench), 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#', new ItemStack(Item.netherrackBrick, 1), 'W',
				new ItemStack(Block.workbench), 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0) });

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', new ItemStack(Item.netherrackBrick, 1) });

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.torchArrow, 1), new Object[] { new ItemStack(Item.arrow, 1), new ItemStack(Block.torchWood, 1, 0) });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.netherSoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#', Item.netherrackBrick, 'W',
						new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		CraftingManager.getInstance().getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.NetherDemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', Item.netherrackBrick }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherBlocks.NetherSoulBlocker, 1, SoulBlocker.NetherSoulBlocker), new Object[] { "IPI", "SBS", "XXX", 'I', "ingotDemonic", 'P',
						NetherBlocks.netherPlank, 'S', NetherItems.NetherWoodStick, 'B', NetherItems.NetherPotionBottle, 'X', Item.netherrackBrick }));

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

		GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.NetherWoodCharcoal, 1), new Object[] { new ItemStack(Item.coal, 1, 1), new ItemStack(Item.gunpowder, 1) });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherItems.NetherDemonicBarHandle, 1), new Object[] { "NIN", " S ", 'N', Item.netherrackBrick, 'I', "ingotDemonic", 'S',
						NetherItems.NetherWoodStick }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(NetherItems.NetherStoneBowl, 3), new Object[] { "N N", " N ", 'N', Item.netherrackBrick }));

		GameRegistry.addRecipe(new ItemStack(NetherBlocks.NetherSoulGlassPane, 16), new Object[] { "###", "###", '#', NetherBlocks.NetherSoulGlass });
		GameRegistry.addRecipe(new ItemStack(NetherItems.NetherSoulGlassBottleItem, 3), new Object[] { "# #", " # ", '#', NetherBlocks.NetherSoulGlass });
		GameRegistry.addRecipe(new ItemStack(Item.flintAndSteel, 1), new Object[] { "BB", "QQ", 'B', Item.netherrackBrick, 'Q', Item.netherQuartz });

		List recipes = CraftingManager.getInstance().getRecipeList();
		addShapedRecipeFirst(recipes, new ItemStack(NetherItems.NetherWoodStick, 4), new Object[] { "#", "#", '#', NetherBlocks.netherPlank });

		FurnaceRecipes.smelting().addSmelting(NetherBlocks.netherOre.blockID, NetherOre.netherOreCoal, new ItemStack(Item.coal, 1), 0.2F);

		/**
		 * Swords
		 */
		initSwordRecipes();

	}

	/**
	 * @author mDiyo
	 */
	public void addShapedRecipeFirst(List recipeList, ItemStack itemstack, Object... objArray) {
		String var3 = "";
		int var4 = 0;
		int var5 = 0;
		int var6 = 0;

		if (objArray[var4] instanceof String[]) {
			String[] var7 = (String[]) ((String[]) objArray[var4++]);

			for (int var8 = 0; var8 < var7.length; ++var8) {
				String var9 = var7[var8];
				++var6;
				var5 = var9.length();
				var3 = var3 + var9;
			}
		} else {
			while (objArray[var4] instanceof String) {
				String var11 = (String) objArray[var4++];
				++var6;
				var5 = var11.length();
				var3 = var3 + var11;
			}
		}

		HashMap var12;

		for (var12 = new HashMap(); var4 < objArray.length; var4 += 2) {
			Character var13 = (Character) objArray[var4];
			ItemStack var14 = null;

			if (objArray[var4 + 1] instanceof Item) {
				var14 = new ItemStack((Item) objArray[var4 + 1]);
			} else if (objArray[var4 + 1] instanceof Block) {
				var14 = new ItemStack((Block) objArray[var4 + 1], 1, -1);
			} else if (objArray[var4 + 1] instanceof ItemStack) {
				var14 = (ItemStack) objArray[var4 + 1];
			}

			var12.put(var13, var14);
		}

		ItemStack[] var15 = new ItemStack[var5 * var6];

		for (int var16 = 0; var16 < var5 * var6; ++var16) {
			char var10 = var3.charAt(var16);

			if (var12.containsKey(Character.valueOf(var10))) {
				var15[var16] = ((ItemStack) var12.get(Character.valueOf(var10))).copy();
			} else {
				var15[var16] = null;
			}
		}

		ShapedRecipes var17 = new ShapedRecipes(var5, var6, var15, itemstack);
		recipeList.add(0, var17);
	}

	private void initSwordRecipes() {
		/**
		 * Obsidian Sword & Obsidian Acid/Death Sword
		 */
		if (NetherItems.NetherObsidianSword != null) {
			GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSword, 1),
					new Object[] { " O ", " O ", " H ", 'O', Block.obsidian, 'H', NetherItems.NetherDemonicBarHandle });

			GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordAcid, 1), new Object[] { "#S#", '#',
					new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.acid), 'S', NetherItems.NetherObsidianSword });
			GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordDeath, 1), new Object[] { "#S#", '#',
					new ItemStack(NetherItems.NetherPotionBottle, 1, NetherPotionBottle.death), 'S', NetherItems.NetherObsidianSword });

			GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordAcid, 1), new Object[] { " # ", "#S#", " # ", '#',
					new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.acid), 'S', NetherItems.NetherObsidianSword });
			GameRegistry.addRecipe(new ItemStack(NetherItems.NetherObsidianSwordDeath, 1), new Object[] { " # ", "#S#", " # ", '#',
					new ItemStack(NetherItems.NetherStonePotionBowl, 1, NetherStonePotionBowl.death), 'S', NetherItems.NetherObsidianSword });
		}
		/**
		 * Soulglass Swords
		 */
		if (NetherItems.NetherSoulglassSword != null) {
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
		 * Diamond Swords
		 */
		if (NetherItems.NetherDiamondSword != null) {
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
	}

	/**
	 * Returns Default Minecraft Items from Mod Items
	 */
	private void initDefaultMinecraftRecipes() {
		/*
		 * GameRegistry.addRecipe(new ItemStack(Item.bed, 1), new Object[] { "###", "XXX", '#', Block.cloth, 'X', NetherBlocks.netherPlank }); GameRegistry.addRecipe(new
		 * ItemStack(Block.chest), new Object[] { "###", "# #", "###", '#', NetherBlocks.netherPlank }); GameRegistry.addRecipe(new ItemStack(Block.workbench), new Object[] { "##",
		 * "##", '#', NetherBlocks.netherPlank });
		 */
		GameRegistry.addShapelessRecipe(new ItemStack(Item.netherrackBrick), new Object[] { new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone) });
		GameRegistry.addShapelessRecipe(new ItemStack(NetherBlocks.netherOre, 1, NetherOre.netherStone), new Object[] { Item.netherrackBrick });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.stick), new Object[] { new ItemStack(NetherItems.NetherWoodStick) });

		// Default Wooden Tools
		/*
		 * GameRegistry.addRecipe(new ItemStack(Item.axeWood, 1), new Object[] { "##.", "#S.", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S', new ItemStack(Item.stick) });
		 * GameRegistry.addRecipe(new ItemStack(Item.axeWood, 1), new Object[] { ".##", ".S#", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S', new ItemStack(Item.stick) });
		 * GameRegistry .addRecipe(new ItemStack(Item.pickaxeWood, 1), new Object[] { "###", ".S.", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S', new
		 * ItemStack(Item.stick) }); GameRegistry.addRecipe(new ItemStack(Item.shovelWood, 1), new Object[] { ".#.", ".S.", ".S.", '#', new ItemStack(NetherBlocks.netherPlank), 'S',
		 * new ItemStack(Item.stick) });
		 */
	}

	private void initLanguageRegistry() {

		for (int i = 0; i < NetherOreItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherOre." + NetherOreItemBlock.blockNames[i] + ".name", NetherOreItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < NetherWoodItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherWood." + NetherWoodItemBlock.blockNames[i] + ".name", NetherWoodItemBlock.blockDisplayNames[i]);
		}

		for (int i = 0; i < NetherWoodPuddleItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherWoodPuddle." + NetherWoodPuddleItemBlock.blockNames[i] + ".name",
					NetherWoodPuddleItemBlock.blockDisplayNames[i]);
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

		// LanguageRegistry.instance().addStringLocalization("item.NetherWoodCharcoal.name",
		// "Nether Charcoal");

		LanguageRegistry.instance().addStringLocalization("item.NetherBow.name", "Torch Bow");
		LanguageRegistry.instance().addStringLocalization("item.torchArrow.name", "Torch Arrow");
		LanguageRegistry.instance().addStringLocalization("item.SoulEnergyLiquidItem.name", "Soul Energy Liquid");
		LanguageRegistry.instance().addStringLocalization("item.DemonicIngotLiquidItem.name", "Demonic Ore Liquid");

		LanguageRegistry.instance().addStringLocalization("itemGroup.tabNetherStuffs", "en_US", "NetherStuffs");

		if (bBuildcraftAvailable)
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulEngine.name", "Soul Engine");

		if (bHarkenScytheAvailable) {
			for (int i = 0; i < SoulCondenserItemBlock.getMetadataSize(); i++) {
				LanguageRegistry.instance().addStringLocalization("tile.SoulCondenser." + SoulCondenserItemBlock.blockNames[i] + ".name", SoulCondenserItemBlock.blockNames[i]);
			}
		}
	}

	@PostInit
	public static void postInit(FMLPostInitializationEvent event) {
		// FMLLog.info("[NetherStuffs] postInit");
	}
}
