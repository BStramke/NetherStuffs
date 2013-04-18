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

	public static Block StairAcid;
	public static Block StairHellfire;
	public static Block StairDeath;
	public static NetherSlab HalfSlab;
	public static NetherSlab DoubleSlab;
	public static Block FenceGateNetherBricks;
	public static Block FenceGateHellfire;
	public static Block FenceGateAcid;
	public static Block FenceGateDeath;
	
	public static Block FenceHellfire;
	public static Block FenceAcid;
	public static Block FenceDeath;
	
	public static final Block netherOre = new NetherOre(NetherStuffs.NetherOreBlockId).setUnlocalizedName("NetherOre").setHardness(10.0F).setResistance(5.0F);
	public static final Block netherWood = new NetherWood(NetherStuffs.NetherWoodBlockId).setUnlocalizedName("NetherWood").setHardness(4.0F).setResistance(5.0F);
	public static final Block netherLeaves = new NetherLeaves(NetherStuffs.NetherLeavesBlockId).setUnlocalizedName("NetherLeaves").setHardness(0.2F).setLightOpacity(1);
	public static final Block netherPlank = new NetherPlank(NetherStuffs.NetherPlankBlockId).setUnlocalizedName("NetherPlank").setHardness(2.0F).setResistance(5.0F);
	public static final Block netherWoodPuddle = new NetherWoodPuddle(NetherStuffs.NetherWoodPuddleBlockId).setUnlocalizedName("NetherWoodPuddle").setHardness(4.0F).setResistance(5.0F);
	
	public static final BlockSapling netherSapling = (BlockSapling) new NetherSapling(NetherStuffs.NetherSaplingBlockId).setUnlocalizedName("NetherSapling").setHardness(0.0F);
	public static final Block netherSoulWorkBench = new SoulWorkBench(NetherStuffs.SoulWorkBenchBlockId).setUnlocalizedName("NetherSoulWorkBench").setHardness(5.0F);
	public static final Block netherSoulBomb = new SoulBomb(NetherStuffs.NetherSoulBombBlockId).setUnlocalizedName("NetherSoulBomb").setHardness(0.0F);
	
	public static final Block NetherDemonicFurnace = new NetherDemonicFurnace(NetherStuffs.NetherDemonicFurnaceBlockId).setUnlocalizedName("NetherDemonicFurnace").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulFurnace = new NetherSoulFurnace(NetherStuffs.NetherSoulFurnaceBlockId).setUnlocalizedName("NetherSoulFurnace").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulGlass = new NetherSoulGlass(NetherStuffs.NetherSoulGlassBlockid, Material.glass, false).setUnlocalizedName("NetherSoulGlass").setHardness(0.6F).setResistance(10.0F);
	public static final Block NetherSoulGlassPane = new NetherSoulGlassPane(NetherStuffs.NetherSoulGlassPaneBlockid, Material.glass, false).setUnlocalizedName("NetherSoulGlassPane").setHardness(0.6F).setResistance(10.0F);
	
	public static final Block NetherSoulDetector = new SoulDetector(NetherStuffs.NetherSoulDetectorBlockId).setUnlocalizedName("NetherSoulDetector").setHardness(3.5F).setResistance(10.0F);
	
	public static final Block NetherSoulBlocker = new SoulBlocker(NetherStuffs.NetherSoulBlockerBlockId).setUnlocalizedName("NetherSoulBlocker").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulSiphon = new SoulSiphon(NetherStuffs.NetherSoulSiphonBlockId).setUnlocalizedName("NetherSoulSiphon").setHardness(3.5F).setResistance(10.0F);
	
	
}
