package bstramke.NetherStuffs;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.LeafItemBlock;
import bstramke.NetherStuffs.Blocks.Ore;
import bstramke.NetherStuffs.Blocks.OreExtended;
import bstramke.NetherStuffs.Blocks.OreExtendedItemBlock;
import bstramke.NetherStuffs.Blocks.OreItemBlock;
import bstramke.NetherStuffs.Blocks.Plank;
import bstramke.NetherStuffs.Blocks.PlankItemBlock;
import bstramke.NetherStuffs.Blocks.SaplingItemBlock;
import bstramke.NetherStuffs.Blocks.Wood;
import bstramke.NetherStuffs.Blocks.WoodItemBlock;
import bstramke.NetherStuffs.Blocks.decorative.NetherDoubleSlabItemBlock;
import bstramke.NetherStuffs.Blocks.decorative.NetherFence;
import bstramke.NetherStuffs.Blocks.decorative.NetherFenceGate;
import bstramke.NetherStuffs.Blocks.decorative.NetherHalfSlabItemBlock;
import bstramke.NetherStuffs.Blocks.decorative.NetherSlab;
import bstramke.NetherStuffs.Blocks.decorative.NetherStairs;
import bstramke.NetherStuffs.Blocks.demonicFurnace.DemonicFurnaceRecipes;
import bstramke.NetherStuffs.Blocks.demonicFurnace.TileDemonicFurnace;
import bstramke.NetherStuffs.Blocks.soulBomb.EntitySoulBombPrimed;
import bstramke.NetherStuffs.Blocks.soulBomb.SoulBombItemBlock;
import bstramke.NetherStuffs.Blocks.soulRipper.SoulRipperItemBlock;
import bstramke.NetherStuffs.Blocks.soulRipper.TileSoulRipper;
import bstramke.NetherStuffs.Blocks.soulWorkBench.TileSoulWorkBench;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.GuiHandler;
import bstramke.NetherStuffs.Common.NetherStuffsFuel;
import bstramke.NetherStuffs.Common.Materials.NetherMaterials;
import bstramke.NetherStuffs.Enchantments.WeaponEnchantments;
import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.WorldGen.WorldGenDefaultMinable;
import bstramke.NetherStuffs.WorldGen.WorldGenNetherStuffsTrees;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(name = "NetherStuffs", version = "0.19", modid = "NetherStuffs", dependencies = ""/* "after:Thaumcraft; */)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class NetherStuffs extends DummyModContainer {
	@Instance
	public static NetherStuffs instance = new NetherStuffs();

	private GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(clientSide = "bstramke.NetherStuffs.Client.ClientProxy", serverSide = "bstramke.NetherStuffs.Common.CommonProxy")
	public static CommonProxy proxy;
	public static int EnchantmentHellfireId;
	public static int EnchantmentAcidId;
	public static int EnchantmentDeathId;

	public static int NetherOreBlockId;
	public static int NetherOreExtendedBlockId;
	public static int NetherWoodBlockId;
	public static int NetherPlankBlockId;
	public static int NetherDemonicFurnaceBlockId;
	public static int NetherLeavesBlockId;
	public static int NetherSaplingBlockId;
	public static int NetherSoulGlassBlockid;
	public static int NetherSoulGlassPaneBlockid;
	public static int SoulWorkBenchBlockId;
	public static int NetherSoulBombBlockId;

	public static int NetherOreIngotItemId;
	public static int NetherWoodCharcoalItemId;
	public static int NetherWoodStickItemId;

	public static int NetherSoulRipperBlockId;
	public static int NetherStairHellfireBlockId;
	public static int NetherStairAcidBlockId;
	public static int NetherStairDeathBlockId;
	public static int NetherHalfSlabBlockId;
	public static int NetherDoubleSlabBlockId;

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
	public static boolean bUseNetherOreCopper;
	public static boolean bUseNetherOreTin;
	public static boolean bUseNetherOreSilver;
	public static boolean bUseNetherOreLead;
	public static boolean bUseNetherOreCertusQuartz;
	public static boolean bUseNetherOreFerrous;
	public static boolean bUseNetherOreApatite;
	public static boolean bUseNetherOreUranium;

	public static boolean bForceGenCopper;
	public static boolean bForceGenTin;
	public static boolean bForceGenSilver;
	public static boolean bForceGenLead;
	public static boolean bForceGenCertusQuartz;
	public static boolean bForceGenFerrous;
	public static boolean bForceGenApatite;
	public static boolean bForceGenUranium;
	
	
	public static boolean bRegenerateDemonic;
	public static boolean bRegenerateCoal;
	public static boolean bRegenerateIron;
	public static boolean bRegenerateGold;
	public static boolean bRegenerateDiamond;
	public static boolean bRegenerateEmerald;
	public static boolean bRegenerateRedstone;
	public static boolean bRegenerateObsidian;
	public static boolean bRegenerateLapis;
	public static boolean bRegenerateCobblestone;
	public static boolean bRegenerateCopper;
	public static boolean bRegenerateTin;
	public static boolean bRegenerateSilver;
	public static boolean bRegenerateLead;
	public static boolean bRegenerateCertusQuartz;
	public static boolean bRegenerateFerrous;
	public static boolean bRegenerateApatite;
	public static boolean bRegenerateUranium;

	public static class WorldGen {
		public static WorldGenDefaultMinable demonicOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 8, Ore.demonicOre, 10);
		public static WorldGenDefaultMinable coalOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 16, Ore.netherOreCoal, 10);
		public static WorldGenDefaultMinable ironOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 8, Ore.netherOreIron, 15);
		public static WorldGenDefaultMinable goldOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 8, Ore.netherOreGold, 4);
		public static WorldGenDefaultMinable diamondOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 7, Ore.netherOreDiamond, 1);
		public static WorldGenDefaultMinable emeraldOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 7, Ore.netherOreEmerald, 1);
		public static WorldGenDefaultMinable redstoneOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 7, Ore.netherOreRedstone, 6);
		public static WorldGenDefaultMinable obsidianOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 8, Ore.netherOreObsidian, 8);
		public static WorldGenDefaultMinable lapisOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 8, Ore.netherOreLapis, 1);
		public static WorldGenDefaultMinable cobblestoneOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 13, Ore.netherOreCobblestone, 10);
		public static WorldGenDefaultMinable copperOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 6, Ore.netherOreCopper, 12);
		public static WorldGenDefaultMinable tinOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 6, Ore.netherOreTin, 12);
		public static WorldGenDefaultMinable silverOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 3, Ore.netherOreSilver, 8);
		public static WorldGenDefaultMinable leadOre = new WorldGenDefaultMinable(BlockRegistry.netherOre.blockID, 6, Ore.netherOreLead, 8);
		public static WorldGenDefaultMinable certusQuartzOre = new WorldGenDefaultMinable(BlockRegistry.netherOreExtended.blockID, 4, OreExtended.netherOreCertusQuartz, 8);
		public static WorldGenDefaultMinable nickelOre = new WorldGenDefaultMinable(BlockRegistry.netherOreExtended.blockID, 4, OreExtended.netherOreFerrous, 8);
		public static WorldGenDefaultMinable apatiteOre = new WorldGenDefaultMinable(BlockRegistry.netherOreExtended.blockID, 8, OreExtended.netherOreApatite, 8);
		public static WorldGenDefaultMinable uraniumOre = new WorldGenDefaultMinable(BlockRegistry.netherOreExtended.blockID, 2, OreExtended.netherOreUranium, 8);

		public static WorldGenNetherStuffsTrees trees = new WorldGenNetherStuffsTrees(false, 4, false);
	}

	public static CreativeTabs tabNetherStuffs = new CreativeTabs("tabNetherStuffs") {
		public ItemStack getIconItemStack() {
			return new ItemStack(BlockRegistry.SoulWorkBench, 1, 0);
		}
	};

	@EventHandler
	public void PreLoad(FMLPreInitializationEvent event) {
		FMLLog.log("NetherStuffs", Level.INFO, "PreLoad");
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		NetherOreBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherOre", 1230).getInt(1230);
		NetherOreExtendedBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherOreExtended", 1237).getInt(1237);
		NetherWoodBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherWood", 1231).getInt(1231);
		NetherPlankBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "NetherPlank", 1232).getInt(1232);
		NetherDemonicFurnaceBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "DemonicFurnace", 1233).getInt(1233);
		NetherLeavesBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Leaves", 1234).getInt(1234);
		NetherSaplingBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Saplings", 1235).getInt(1235);
		NetherSoulGlassBlockid = config.getBlock(Configuration.CATEGORY_BLOCK, "Glass", 1236).getInt(1236);
		NetherSoulGlassPaneBlockid = config.getBlock(Configuration.CATEGORY_BLOCK, "GlassPane", 1237).getInt(1237);
		SoulWorkBenchBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulWorkbench", 1239).getInt(1239);
		NetherSoulBombBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulBomb", 1240).getInt(1240);
		NetherSoulRipperBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "SoulRipper", 1244).getInt(1244);

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

		if (bUseFences) {
			NetherFenceHellfireBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Hellfire Fence", 1258).getInt(1258);
			NetherFenceAcidBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Acid Fence", 1259).getInt(1259);
			NetherFenceDeathBlockId = config.getBlock(Configuration.CATEGORY_BLOCK, "Death Fence", 1260).getInt(1260);
		}

		NetherOreIngotItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherIngots", 5200).getInt(5200);
		NetherWoodCharcoalItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherWoodCharcoal", 5214).getInt(5214);
		NetherWoodStickItemId = config.getItem(Configuration.CATEGORY_ITEM, "NetherWoodStick", 5203).getInt(5203);

		int startEnchantId = 52;
		for (; startEnchantId < Enchantment.enchantmentsList.length && Enchantment.enchantmentsList[startEnchantId] != null; startEnchantId++) {}
		EnchantmentHellfireId = config.get("Enchantments", "Hellfire", startEnchantId).getInt(startEnchantId);
		for (++startEnchantId; startEnchantId < Enchantment.enchantmentsList.length && Enchantment.enchantmentsList[startEnchantId] != null; startEnchantId++) {}
		EnchantmentAcidId = config.get("Enchantments", "Acid", startEnchantId).getInt(startEnchantId);
		for (++startEnchantId; startEnchantId < Enchantment.enchantmentsList.length && Enchantment.enchantmentsList[startEnchantId] != null; startEnchantId++) {}
		EnchantmentDeathId = config.get("Enchantments", "Death", startEnchantId).getInt(startEnchantId);

		SpawnSkeletonsAwayFromNetherFortresses = config.get(Configuration.CATEGORY_GENERAL, "SpawnSkeletonsAwayFromNetherFortresses", true).getBoolean(true);
		IncreaseNetherrackHardness = config.get(Configuration.CATEGORY_GENERAL, "IncreaseNetherrackHardness", true).getBoolean(true);
		SpawnBlazesNaturally = config.get(Configuration.CATEGORY_GENERAL, "SpawnBlazesNaturally", false).getBoolean(false);

		config.addCustomCategoryComment("NetherOreGeneration",
				"Here you can set the ores to not generate or generate. Keep in mind that they will only generate when you actually have a Mod that uses the Ore");
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
		bUseNetherOreCopper = config.get("NetherOreGeneration", "Copper", true).getBoolean(true);
		bUseNetherOreTin = config.get("NetherOreGeneration", "Tin", true).getBoolean(true);
		bUseNetherOreSilver = config.get("NetherOreGeneration", "Silver", true).getBoolean(true);
		bUseNetherOreLead = config.get("NetherOreGeneration", "Lead", true).getBoolean(true);
		bUseNetherOreCertusQuartz = config.get("NetherOreGeneration", "CertusQuartz", true).getBoolean(true);
		bUseNetherOreFerrous = config.get("NetherOreGeneration", "FerrousOre", true).getBoolean(true);
		bUseNetherOreApatite = config.get("NetherOreGeneration", "ApatiteOre", true).getBoolean(true);
		bUseNetherOreUranium = config.get("NetherOreGeneration", "Uranium Ore", true).getBoolean(true);

		config.addCustomCategoryComment("NetherOreGeneration",
				"If you want to forcefully generate an Ore in the Nether, for example you don't have that mod yet but plan on Using it later, you can enable that here");
		bForceGenCopper = config.get("ForceOreGeneration", "Copper", false).getBoolean(false);
		bForceGenTin = config.get("ForceOreGeneration", "Tin", false).getBoolean(false);
		bForceGenSilver = config.get("ForceOreGeneration", "Silver", false).getBoolean(false);
		bForceGenLead = config.get("ForceOreGeneration", "Lead", false).getBoolean(false);
		bForceGenCertusQuartz = config.get("ForceOreGeneration", "CertusQuartz", false).getBoolean(false);
		bForceGenFerrous = config.get("ForceOreGeneration", "Ferrous", false).getBoolean(false);
		bForceGenApatite = config.get("ForceOreGeneration", "Apatite", false).getBoolean(false);
		bForceGenUranium = config.get("ForceOreGeneration", "Uranium", false).getBoolean(false);
		
		config.addCustomCategoryComment("Nether Ore Regeneration", "In case you want to regenerate the existing Nether chunks, make that settings here.");
		
		bRegenerateDemonic = config.get("Nether Ore Regeneration", "Demonic", false).getBoolean(false);
		bRegenerateCoal = config.get("Nether Ore Regeneration", "NetherCoal", false).getBoolean(false);
		bRegenerateIron = config.get("Nether Ore Regeneration", "Iron", false).getBoolean(false);
		bRegenerateGold = config.get("Nether Ore Regeneration", "Gold", false).getBoolean(false);
		bRegenerateDiamond = config.get("Nether Ore Regeneration", "Diamond", false).getBoolean(false);
		bRegenerateEmerald = config.get("Nether Ore Regeneration", "Emerald", false).getBoolean(false);
		bRegenerateRedstone = config.get("Nether Ore Regeneration", "Redstone", false).getBoolean(false);
		bRegenerateObsidian = config.get("Nether Ore Regeneration", "Obsidian", false).getBoolean(false);
		bRegenerateLapis = config.get("Nether Ore Regeneration", "Lapis", false).getBoolean(false);
		bRegenerateCobblestone = config.get("Nether Ore Regeneration", "Cobblestone", false).getBoolean(false);
		
		bRegenerateCopper = config.get("Nether Ore Regeneration", "Copper", false).getBoolean(false);
		bRegenerateTin = config.get("Nether Ore Regeneration", "Tin", false).getBoolean(false);
		bRegenerateSilver = config.get("Nether Ore Regeneration", "Silver", false).getBoolean(false);
		bRegenerateLead = config.get("Nether Ore Regeneration", "Lead", false).getBoolean(false);
		bRegenerateCertusQuartz = config.get("Nether Ore Regeneration", "CertusQuartz", false).getBoolean(false);
		bRegenerateFerrous = config.get("Nether Ore Regeneration", "Ferrous", false).getBoolean(false);
		bRegenerateApatite = config.get("Nether Ore Regeneration", "Apatite", false).getBoolean(false);
		bRegenerateUranium = config.get("Nether Ore Regeneration", "Uranium", false).getBoolean(false);

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
				NetherLeavesBlockId + "," + SoulWorkBenchBlockId + "," + NetherDemonicFurnaceBlockId + "," + NetherSoulGlassBlockid + "," + NetherSoulGlassPaneBlockid + ","
						+ NetherSoulBombBlockId).getString();
		String[] tmp = tmpString.split(",");
		for (int i = 0; i < tmp.length; i++) {
			int intVal = Integer.parseInt(tmp[i].trim());
			if (intVal > 0 && intVal < 4096)
				NetherStuffsEventHook.lBlockSpawnListForced.add(intVal);
		}

		FMLLog.log("NetherStuffs", Level.INFO, "Blocked Nether Spawns on Block IDs: %s", NetherStuffsEventHook.lBlockSpawnListForced.toString());
		config.save();
	}

	private void initDecorativeBlocks() {

		if (bUseFences) {
			int nNetherFenceId = Block.netherFence.blockID;
			Block.blocksList[nNetherFenceId] = null;
			(new NetherFence(nNetherFenceId, "netherBrick", Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep)
					.setUnlocalizedName("netherFence");
			BlockRegistry.FenceHellfire = (new NetherFence(NetherFenceHellfireBlockId, "netherBrick", NetherMaterials.netherWood)).setUnlocalizedName("NetherFenceHellfire");
			BlockRegistry.FenceAcid = (new NetherFence(NetherFenceAcidBlockId, "netherBrick", NetherMaterials.netherWood)).setUnlocalizedName("NetherFenceAcid");
			BlockRegistry.FenceDeath = (new NetherFence(NetherFenceDeathBlockId, "netherBrick", NetherMaterials.netherWood)).setUnlocalizedName("NetherFenceDeath");
			GameRegistry.registerBlock(BlockRegistry.FenceHellfire, "NetherFenceHellfireItemBlock");
			GameRegistry.registerBlock(BlockRegistry.FenceAcid, "NetherFenceAcidItemBlock");
			GameRegistry.registerBlock(BlockRegistry.FenceDeath, "NetherFenceDeathItemBlock");
		}

		if (bUseFenceGates) {
			BlockRegistry.FenceGateNetherBricks = new NetherFenceGate(NetherFenceGateNetherBricksBlockId).setUnlocalizedName("NetherBrickFenceGate");
			BlockRegistry.FenceGateHellfire = new NetherFenceGate(NetherFenceGateHellfireBlockId).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep)
					.setUnlocalizedName("HellfireFenceGate");
			BlockRegistry.FenceGateAcid = new NetherFenceGate(NetherFenceGateAcidBlockId).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep)
					.setUnlocalizedName("AcidFenceGate");
			BlockRegistry.FenceGateDeath = new NetherFenceGate(NetherFenceGateDeathBlockId).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep)
					.setUnlocalizedName("DeathFenceGate");

			GameRegistry.registerBlock(BlockRegistry.FenceGateNetherBricks, "NetherFenceGateBrickItemBlock");
			GameRegistry.registerBlock(BlockRegistry.FenceGateHellfire, "NetherFenceGateHellfireItemBlock");
			GameRegistry.registerBlock(BlockRegistry.FenceGateAcid, "NetherFenceGateAcidItemBlock");
			GameRegistry.registerBlock(BlockRegistry.FenceGateDeath, "NetherFenceGateDeathItemBlock");
			LanguageRegistry.instance().addStringLocalization("tile.NetherBrickFenceGate.name", "Netherbrick Fence Gate");
			LanguageRegistry.instance().addStringLocalization("tile.HellfireFenceGate.name", "Hellfirewood Fence Gate");
			LanguageRegistry.instance().addStringLocalization("tile.AcidFenceGate.name", "Acidwood Fence Gate");
			LanguageRegistry.instance().addStringLocalization("tile.DeathFenceGate.name", "Deathwood Fence Gate");
		}

		if (bUseStairBlocks) {
			BlockRegistry.StairAcid = new NetherStairs(NetherStairAcidBlockId, BlockRegistry.netherPlank, Plank.acid).setUnlocalizedName("StairAcid");
			BlockRegistry.StairHellfire = new NetherStairs(NetherStairHellfireBlockId, BlockRegistry.netherPlank, Plank.hellfire).setUnlocalizedName("StairHellfire");
			BlockRegistry.StairDeath = new NetherStairs(NetherStairDeathBlockId, BlockRegistry.netherPlank, Plank.death).setUnlocalizedName("StairDeath");
			GameRegistry.registerBlock(BlockRegistry.StairAcid, "NetherStairAcidItemBlock");
			GameRegistry.registerBlock(BlockRegistry.StairHellfire, "NetherStairHellfireItemBlock");
			GameRegistry.registerBlock(BlockRegistry.StairDeath, "NetherStairDeathItemBlock");

			MinecraftForge.setBlockHarvestLevel(BlockRegistry.StairAcid, "axe", 1);
			MinecraftForge.setBlockHarvestLevel(BlockRegistry.StairHellfire, "axe", 1);
			MinecraftForge.setBlockHarvestLevel(BlockRegistry.StairDeath, "axe", 1);

			GameRegistry.addRecipe(new ItemStack(BlockRegistry.StairAcid, 4), new Object[] { "#  ", "## ", "###", '#', new ItemStack(BlockRegistry.netherPlank, 1, Plank.acid) });
			GameRegistry.addRecipe(new ItemStack(BlockRegistry.StairHellfire, 4),
					new Object[] { "#  ", "## ", "###", '#', new ItemStack(BlockRegistry.netherPlank, 1, Plank.hellfire) });
			GameRegistry.addRecipe(new ItemStack(BlockRegistry.StairDeath, 4), new Object[] { "#  ", "## ", "###", '#', new ItemStack(BlockRegistry.netherPlank, 1, Plank.death) });

			LanguageRegistry.instance().addStringLocalization("tile.StairAcid.name", "Acid Wood Stairs");
			LanguageRegistry.instance().addStringLocalization("tile.StairHellfire.name", "Hellfire Wood Stairs");
			LanguageRegistry.instance().addStringLocalization("tile.StairDeath.name", "Death Wood Stairs");

			OreDictionary.registerOre("stairWood", BlockRegistry.StairAcid);
			OreDictionary.registerOre("stairWood", BlockRegistry.StairHellfire);
			OreDictionary.registerOre("stairWood", BlockRegistry.StairDeath);
		}

		if (bUseSlabBlocks) {

			BlockRegistry.HalfSlab = (NetherSlab) new NetherSlab(NetherHalfSlabBlockId, false).setUnlocalizedName("HalfSlab");
			BlockRegistry.DoubleSlab = (NetherSlab) new NetherSlab(NetherDoubleSlabBlockId, true).setUnlocalizedName("DoubleSlab");

			GameRegistry.registerBlock(BlockRegistry.HalfSlab, NetherHalfSlabItemBlock.class, "NetherSlabHalfItemBlock");
			GameRegistry.registerBlock(BlockRegistry.DoubleSlab, NetherDoubleSlabItemBlock.class, "NetherSlabDoubleItemBlock");

			MinecraftForge.setBlockHarvestLevel(BlockRegistry.HalfSlab, "axe", 1);
			MinecraftForge.setBlockHarvestLevel(BlockRegistry.DoubleSlab, "axe", 1);

			OreDictionary.registerOre("slabWood", new ItemStack(BlockRegistry.HalfSlab, 1, OreDictionary.WILDCARD_VALUE));

			for (int i = 0; i < PlankItemBlock.blockNames.length; i++) {
				GameRegistry.addRecipe(new ItemStack(BlockRegistry.HalfSlab, 6, i), new Object[] { "###", '#', new ItemStack(BlockRegistry.netherPlank, 1, i) });

				MinecraftForge.setBlockHarvestLevel(BlockRegistry.HalfSlab, i, "axe", 1);
				MinecraftForge.setBlockHarvestLevel(BlockRegistry.DoubleSlab, i, "axe", 1);

				LanguageRegistry.instance().addStringLocalization("tile.HalfSlab." + PlankItemBlock.blockNames[i] + ".name", PlankItemBlock.blockNames[i] + " Slab");
				LanguageRegistry.instance().addStringLocalization("tile.DoubleSlab." + PlankItemBlock.blockNames[i] + ".name", PlankItemBlock.blockNames[i] + " Doubleslab");
			}
		}
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		GameRegistry.registerBlock(BlockRegistry.SoulGlass, "NetherSoulGlass");
		GameRegistry.registerBlock(BlockRegistry.SoulGlassPane, "NetherSoulGlassPane");
		GameRegistry.registerBlock(BlockRegistry.DemonicFurnace, "NetherDemonicFurnace");
		GameRegistry.registerBlock(BlockRegistry.SoulWorkBench, "NetherSoulWorkBench");

		GameRegistry.registerBlock(BlockRegistry.netherOre, OreItemBlock.class, "NetherOreItemBlock");
		GameRegistry.registerBlock(BlockRegistry.netherOreExtended, OreExtendedItemBlock.class, "NetherOreExtendedItemBlock");
		GameRegistry.registerBlock(BlockRegistry.netherWood, WoodItemBlock.class, "NetherWoodItemBlock");
		GameRegistry.registerBlock(BlockRegistry.netherPlank, PlankItemBlock.class, "NetherPlankItemBlock");
		GameRegistry.registerBlock(BlockRegistry.netherLeaves, LeafItemBlock.class, "NetherLeavesItemBlock");
		GameRegistry.registerBlock(BlockRegistry.Sapling, SaplingItemBlock.class, "NetherSaplingItemBlock");
		GameRegistry.registerBlock(BlockRegistry.SoulBomb, SoulBombItemBlock.class, "NetherSoulBombItemBlock");
		GameRegistry.registerBlock(BlockRegistry.SoulRipper, SoulRipperItemBlock.class, "NetherSoulRipperItemBlock");

		// set required Stuff to Gather
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreCobblestone, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherStone, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreCoal, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreIron, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.demonicOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreDiamond, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreEmerald, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreGold, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreRedstone, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreLapis, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreObsidian, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreSilver, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreTin, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreCopper, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOre, Ore.netherOreLead, "pickaxe", 2);

		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOreExtended, OreExtended.netherOreCertusQuartz, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOreExtended, OreExtended.netherOreFerrous, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOreExtended, OreExtended.netherOreApatite, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherOreExtended, OreExtended.netherOreUranium, "pickaxe", 1);

		MinecraftForge.setBlockHarvestLevel(BlockRegistry.DemonicFurnace, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.SoulRipper, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.SoulWorkBench, "axe", 0);

		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherWood, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherWood, Wood.acid, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherWood, Wood.hellfire, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherWood, Wood.death, "axe", 0);

		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherPlank, "axe", 0);

		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherPlank, Plank.acid, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherPlank, Plank.hellfire, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.netherPlank, Plank.death, "axe", 0);

		MinecraftForge.removeBlockEffectiveness(BlockRegistry.netherOre, "pickaxe");
		MinecraftForge.removeBlockEffectiveness(BlockRegistry.netherOreExtended, "pickaxe");

		GameRegistry.registerTileEntity(TileDemonicFurnace.class, "tileEntityNetherStuffsDemonicFurnace");
		GameRegistry.registerTileEntity(TileSoulWorkBench.class, "tileEntityNetherStuffsSoulWorkBench");
		GameRegistry.registerTileEntity(TileSoulRipper.class, "tileEntityNetherStuffsSoulRipper");

		GameRegistry.registerFuelHandler(new NetherStuffsFuel());
		EntityRegistry.registerModEntity(EntitySoulBombPrimed.class, "SoulBomb", 2, instance, 160, 10, true);

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

		WeaponEnchantments tmp = new WeaponEnchantments();
	}

	private void registerWorldGenerators() {
		if (bUseNetherOreDemonic)
			GameRegistry.registerWorldGenerator(WorldGen.demonicOre);
		if (bUseNetherOreCoal)
			GameRegistry.registerWorldGenerator(WorldGen.coalOre);
		if (bUseNetherOreIron)
			GameRegistry.registerWorldGenerator(WorldGen.ironOre);
		if (bUseNetherOreGold)
			GameRegistry.registerWorldGenerator(WorldGen.goldOre);
		if (bUseNetherOreDiamond)
			GameRegistry.registerWorldGenerator(WorldGen.diamondOre);
		if (bUseNetherOreEmerald)
			GameRegistry.registerWorldGenerator(WorldGen.emeraldOre);
		if (bUseNetherOreRedstone)
			GameRegistry.registerWorldGenerator(WorldGen.redstoneOre);
		if (bUseNetherOreObsidian)
			GameRegistry.registerWorldGenerator(WorldGen.obsidianOre);
		if (bUseNetherOreLapis)
			GameRegistry.registerWorldGenerator(WorldGen.lapisOre);
		if (bUseNetherOreCobblestone)
			GameRegistry.registerWorldGenerator(WorldGen.cobblestoneOre);

		GameRegistry.registerWorldGenerator(WorldGen.trees);
	}

	private void initRecipes() {
		initDefaultMinecraftRecipes();

		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.demonicOre, new ItemStack(ItemRegistry.NetherOreIngot, 1, 0), 1.0F);
		DemonicFurnaceRecipes.smelting().addSmelting(Block.slowSand.blockID, 0, new ItemStack(BlockRegistry.SoulGlass, 1, 0), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherWood.blockID, Wood.hellfire, new ItemStack(ItemRegistry.NetherCoal), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreCoal, new ItemStack(ItemRegistry.NetherCoal, 3, 0), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreIron, new ItemStack(Block.oreIron), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreGold, new ItemStack(Block.oreGold), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreDiamond, new ItemStack(Item.diamond, 1), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreEmerald, new ItemStack(Item.emerald, 1), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreRedstone, new ItemStack(Block.oreRedstone, 1, 0), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreObsidian, new ItemStack(Block.obsidian), 0.25F);
		DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreLapis, new ItemStack(Block.oreLapis), 0.25F);

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(BlockRegistry.SoulWorkBench, 1), true, new Object[] { "I#I", "#W#", "I#I", '#', new ItemStack(Item.netherrackBrick, 1), 'W',
						new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		GameRegistry.addRecipe(new ItemStack(BlockRegistry.SoulWorkBench, 1), new Object[] { "I#I", "#W#", "I#I", '#', new ItemStack(Item.netherrackBrick, 1), 'W',
				new ItemStack(Block.workbench), 'I', new ItemStack(ItemRegistry.NetherOreIngot, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.SoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#', new ItemStack(Item.netherrackBrick, 1), 'W',
				new ItemStack(Block.workbench), 'I', new ItemStack(ItemRegistry.NetherOreIngot, 1, 0) });

		GameRegistry.addRecipe(new ItemStack(BlockRegistry.DemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', new ItemStack(Item.netherrackBrick, 1) });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(BlockRegistry.SoulWorkBench, 1), new Object[] { "#I#", "IWI", "#I#", '#', Item.netherrackBrick, 'W',
						new ItemStack(Block.workbench), 'I', "ingotDemonic" }));

		CraftingManager.getInstance().getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(BlockRegistry.DemonicFurnace, 1), new Object[] { "NNN", "N N", "NNN", 'N', Item.netherrackBrick }));

		// Netherwood --> Netherlogs
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.netherPlank, 4, Plank.hellfire), new Object[] { "#", '#', new ItemStack(BlockRegistry.netherWood, 1, Wood.hellfire) });
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.netherPlank, 4, Plank.acid), new Object[] { "#", '#', new ItemStack(BlockRegistry.netherWood, 1, Wood.acid) });
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.netherPlank, 4, Plank.death), new Object[] { "#", '#', new ItemStack(BlockRegistry.netherWood, 1, Wood.death) });

		// Torches out of Netherwood Sticks
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 6), new Object[] { "X", "#", 'X', Item.coal, '#', ItemRegistry.NetherWoodStick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 6), new Object[] { "X", "#", 'X', new ItemStack(Item.coal, 1, 1), '#', ItemRegistry.NetherWoodStick });

		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 8), new Object[] { "X", "#", 'X', new ItemStack(ItemRegistry.NetherCoal), '#', Item.stick });
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 8), new Object[] { "X", "#", 'X', new ItemStack(ItemRegistry.NetherCoal), '#', Item.stick });

		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 10), new Object[] { "X", "#", 'X', new ItemStack(ItemRegistry.NetherCoal, 1, 0), '#', ItemRegistry.NetherWoodStick });

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.NetherCoal, 1), new Object[] { new ItemStack(Item.coal, 1, 1), new ItemStack(Item.gunpowder, 1) });

		GameRegistry.addRecipe(new ItemStack(BlockRegistry.SoulGlassPane, 16), new Object[] { "###", "###", '#', BlockRegistry.SoulGlass });

		GameRegistry.addRecipe(new ItemStack(Item.flintAndSteel, 1), new Object[] { "BB", "QQ", 'B', Item.netherrackBrick, 'Q', Item.netherQuartz });

		List recipes = CraftingManager.getInstance().getRecipeList();
		addShapedRecipeFirst(recipes, new ItemStack(ItemRegistry.NetherWoodStick, 4), new Object[] { "#", "#", '#', new ItemStack(BlockRegistry.netherPlank, 1, Plank.hellfire) });
		addShapedRecipeFirst(recipes, new ItemStack(ItemRegistry.NetherWoodStick, 4), new Object[] { "#", "#", '#', new ItemStack(BlockRegistry.netherPlank, 1, Plank.acid) });
		addShapedRecipeFirst(recipes, new ItemStack(ItemRegistry.NetherWoodStick, 4), new Object[] { "#", "#", '#', new ItemStack(BlockRegistry.netherPlank, 1, Plank.death) });

		FurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreCoal, new ItemStack(Item.coal, 1), 0.2F);
	}

	public static void addShapedRecipeFirst(ItemStack itemstack, Object... objArray) {
		List recipes = CraftingManager.getInstance().getRecipeList();
		addShapedRecipeFirst(recipes, itemstack, objArray);
	}

	/**
	 * @author mDiyo
	 */
	public static void addShapedRecipeFirst(List recipeList, ItemStack itemstack, Object... objArray) {
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

	/**
	 * Returns Default Minecraft Items from Mod Items
	 */
	private void initDefaultMinecraftRecipes() {
		GameRegistry.addShapelessRecipe(new ItemStack(Item.netherrackBrick), new Object[] { new ItemStack(BlockRegistry.netherOre, 1, Ore.netherStone) });
		GameRegistry.addShapelessRecipe(new ItemStack(BlockRegistry.netherOre, 1, Ore.netherStone), new Object[] { Item.netherrackBrick });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.stick), new Object[] { new ItemStack(ItemRegistry.NetherWoodStick) });
	}

	private void initLanguageRegistry() {
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabNetherStuffs", "en_US", "NetherStuffs");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		BlockRegistry.initOreDictionary();
		ItemRegistry.initOreDictionary();

		if (OreDictionary.getOres("oreCopper").isEmpty() == false) {
			if (bUseNetherOreCopper) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding Copper to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreCopper").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.copperOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreCopper, result, 0.25F);
				OreDictionary.registerOre("oreNetherCopper", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreCopper));
			}
		}
		else if(bForceGenCopper)
		{
			if(bUseNetherOreCopper)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED Copper Ore to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.copperOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED Copper Ore generation to the Nether, Copper Ore generation is disabled");
		}

		if (OreDictionary.getOres("oreTin").isEmpty() == false) {
			if (bUseNetherOreTin) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding Tin to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreTin").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.tinOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreTin, result, 0.25F);
				OreDictionary.registerOre("oreNetherTin", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreTin));
			}
		}
		else if(bForceGenTin)
		{
			if(bUseNetherOreTin)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED Tin Ore to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.tinOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED Tin Ore generation to the Nether, Tin Ore generation is disabled");
		}

		if (OreDictionary.getOres("oreSilver").isEmpty() == false) {
			if (bUseNetherOreSilver) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding Silver to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreSilver").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.silverOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreSilver, result, 0.25F);
				OreDictionary.registerOre("oreNetherSilver", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreSilver));
			}
		}
		else if(bForceGenSilver)
		{
			if(bUseNetherOreSilver)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED Silver Ore to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.silverOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED Silver Ore generation to the Nether, Silver Ore generation is disabled");
		}

		if (OreDictionary.getOres("oreLead").isEmpty() == false) {
			if (bUseNetherOreLead) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding Lead to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreLead").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.leadOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOre.blockID, Ore.netherOreLead, result, 0.25F);
				OreDictionary.registerOre("oreNetherLead", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreLead));
			}
		}
		else if(bForceGenLead)
		{
			if(bUseNetherOreLead)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED Lead Ore to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.leadOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED Lead Ore generation to the Nether, Lead Ore generation is disabled");
		}

		if (OreDictionary.getOres("oreCertusQuartz").isEmpty() == false) {
			if (bUseNetherOreCertusQuartz) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding CertusQuartz to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreCertusQuartz").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.certusQuartzOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOreExtended.blockID, OreExtended.netherOreCertusQuartz, result, 0.25F);
				OreDictionary.registerOre("oreNetherCertusQuartz", new ItemStack(BlockRegistry.netherOreExtended, 1, OreExtended.netherOreCertusQuartz));
			}
		}
		else if(bForceGenCertusQuartz)
		{
			if(bUseNetherOreCertusQuartz)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED CertusQuartz to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.certusQuartzOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED CertusQuartz generation to the Nether, CertusQuartz Ore generation is disabled");
		}

		if (OreDictionary.getOres("oreNickel").isEmpty() == false) {
			if (bUseNetherOreFerrous) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding Ferrous Ore to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreNickel").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.nickelOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOreExtended.blockID, OreExtended.netherOreFerrous, result, 0.25F);
				OreDictionary.registerOre("oreNetherFerrous", new ItemStack(BlockRegistry.netherOreExtended, 1, OreExtended.netherOreFerrous));
			}
		}
		else if(bForceGenFerrous)
		{
			if(bUseNetherOreFerrous)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED Ferrous Ore to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.nickelOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED Ferrous Ore generation to the Nether, Ferrous Ore generation is disabled");
		}

		if (OreDictionary.getOres("oreApatite").isEmpty() == false) {
			if (bUseNetherOreApatite) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding Apatite to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreApatite").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.apatiteOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOreExtended.blockID, OreExtended.netherOreApatite, result, 0.25F);
				OreDictionary.registerOre("oreNetherApatite", new ItemStack(BlockRegistry.netherOreExtended, 1, OreExtended.netherOreApatite));
			}
		}
		else if(bForceGenApatite)
		{
			if(bUseNetherOreApatite)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED Apatite to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.apatiteOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED Apatite generation to the Nether, Apatite Ore generation is disabled");
		}

		if (OreDictionary.getOres("oreUranium").isEmpty() == false) {
			if (bUseNetherOreUranium) {
				FMLLog.log("NetherStuffs", Level.INFO, "Adding Uranium to Nether Worldgenerator");
				ItemStack result = OreDictionary.getOres("oreUranium").get(0);
				result.stackSize = 1;
				GameRegistry.registerWorldGenerator(WorldGen.uraniumOre);
				DemonicFurnaceRecipes.smelting().addSmelting(BlockRegistry.netherOreExtended.blockID, OreExtended.netherOreUranium, result, 0.25F);
				OreDictionary.registerOre("oreNetherUranium", new ItemStack(BlockRegistry.netherOreExtended, 1, OreExtended.netherOreUranium));
			}
		}
		else if(bForceGenUranium)
		{
			if(bUseNetherOreUranium)
			{
				FMLLog.log("NetherStuffs", Level.INFO, "Adding FORCED Uranium to Nether Worldgenerator");
				GameRegistry.registerWorldGenerator(WorldGen.uraniumOre);
			}
			else
				FMLLog.log("NetherStuffs", Level.INFO, "Can't Add FORCED Uranium generation to the Nether, Uranium Ore generation is disabled");
		}
	}
}
