package bstramke.NetherStuffs.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import bstramke.NetherStuffs.NetherStuffs;

public class NetherBlocks {
	public static final int sideBottom = 0; 
	public static final int sideTop = 1;
	public static final int sideNorth = 2;
	public static final int sideSouth = 3;
	public static final int sideWest = 4;
	public static final int sideEast = 5;

	public static final Block netherOre = new NetherOre(NetherStuffs.NetherOreBlockId, 0).setBlockName("NetherOre").setHardness(10.0F).setResistance(5.0F);
	public static final Block netherWood = new NetherWood(NetherStuffs.NetherWoodBlockId, 0).setBlockName("NetherWood").setHardness(4.0F).setResistance(5.0F);
	public static final Block netherLeaves = new NetherLeaves(NetherStuffs.NetherLeavesBlockId, 18).setBlockName("NetherLeaves").setHardness(0.2F).setLightOpacity(1);
	public static final Block netherPlank = new NetherPlank(NetherStuffs.NetherPlankBlockId, 0).setBlockName("NetherPlank").setHardness(2.0F).setResistance(5.0F);
	public static final Block netherWoodPuddle = new NetherWoodPuddle(NetherStuffs.NetherWoodPuddleBlockId, 0).setBlockName("NetherWoodPuddle").setHardness(4.0F).setResistance(5.0F);
	
	public static final BlockSapling netherSapling = (BlockSapling) new NetherSapling(NetherStuffs.NetherSaplingBlockId, 0).setBlockName("NetherSapling").setHardness(0.0F);
	public static final Block netherSoulWorkBench = new SoulWorkBench(NetherStuffs.SoulWorkBenchBlockId).setBlockName("NetherSoulWorkBench").setHardness(5.0F);
	public static final Block netherSoulBomb = new SoulBomb(NetherStuffs.NetherSoulBombBlockId, 8).setBlockName("NetherSoulBomb").setHardness(0.0F);
	
	public static final Block NetherDemonicFurnace = new NetherDemonicFurnace(NetherStuffs.NetherDemonicFurnaceBlockId).setBlockName("NetherDemonicFurnace").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulFurnace = new NetherSoulFurnace(NetherStuffs.NetherSoulFurnaceBlockId).setBlockName("NetherSoulFurnace").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulGlass = new NetherSoulGlass(NetherStuffs.NetherSoulGlassBlockid, 112, Material.glass, false).setBlockName("NetherSoulGlass").setHardness(0.6F).setResistance(10.0F);
	public static final Block NetherSoulGlassPane = new NetherSoulGlassPane(NetherStuffs.NetherSoulGlassPaneBlockid, 112, 113, Material.glass, false).setBlockName("NetherSoulGlassPane").setHardness(0.6F).setResistance(10.0F);
	
	public static final Block NetherSoulDetector = new SoulDetector(NetherStuffs.NetherSoulDetectorBlockId, 0).setBlockName("NetherSoulDetector").setHardness(3.5F).setResistance(10.0F);
	
	public static final Block NetherSoulBlocker = new SoulBlocker(NetherStuffs.NetherSoulBlockerBlockId, 114).setBlockName("NetherSoulBlocker").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulSiphon = new SoulSiphon(NetherStuffs.NetherSoulSiphonBlockId, 144).setBlockName("NetherSoulSiphon").setHardness(3.5F).setResistance(10.0F);
	
	public static final Block skyblock = new SkyBlock(NetherStuffs.NetherSkyBlockId).setBlockName("NetherSkyBlock").setHardness(3.5F).setResistance(10.0F);
}
