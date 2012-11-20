package NetherStuffs.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.BlockSapling;
import net.minecraft.src.Material;
import NetherStuffs.NetherStuffs;

public class NetherBlocks {
	public static final int sideBottom = 0; 
	public static final int sideTop = 1;
	public static final int sideNorth = 2;
	public static final int sideSouth = 3;
	public static final int sideWest = 4;
	public static final int sideEast = 5;
	
	@Deprecated
	public static final int sideFront = 3; 
	@Deprecated
	public static final int sideBack = 2; 
	@Deprecated
	public static final int sideRight = 5;
	@Deprecated
	public static final int sideLeft = 4;	

	public static final Block netherOre = new NetherOre(NetherStuffs.NetherOreBlockId, 0).setBlockName("NetherOre").setHardness(10.0F).setResistance(5.0F);
	public static final int demonicOre = NetherOre.demonicOre;
	public static final int netherStone = NetherOre.netherStone;
	public static final Block netherWood = new NetherWood(NetherStuffs.NetherWoodBlockId, 0).setBlockName("NetherWood").setHardness(4.0F).setResistance(5.0F);
	public static final int netherWoodHellfire = NetherWood.hellfire;
	public static final int netherWoodAcid = NetherWood.acid;
	public static final int netherWoodDeath = NetherWood.death;
	public static final Block netherLeaves = new NetherLeaves(NetherStuffs.NetherLeavesBlockId, 18).setBlockName("NetherLeaves").setHardness(0.2F).setLightOpacity(1);
	public static final int netherLeavesHellfire = NetherLeaves.hellfire;
	public static final int netherLeavesAcid = NetherLeaves.acid;
	public static final int netherLeavesDeath = NetherLeaves.death;
	public static final Block netherPlank = new NetherPlank(NetherStuffs.NetherPlankBlockId, 0).setBlockName("NetherPlank").setHardness(2.0F).setResistance(5.0F);
	public static final int netherPlankHellfire = NetherPlank.hellfire;
	public static final int netherPlankAcid = NetherPlank.acid;
	public static final int netherPlankDeath = NetherPlank.death;
	public static final BlockSapling netherSapling = (BlockSapling) new NetherSapling(NetherStuffs.NetherSaplingBlockId, 0).setBlockName("NetherSapling").setHardness(0.0F);
	public static final int netherSaplingHellfire = NetherSapling.hellfire;
	public static final int netherSaplingAcid = NetherSapling.acid;
	public static final int netherSaplingDeath = NetherSapling.death;
	public static final Block netherPuddle = new NetherPuddle(NetherStuffs.NetherPuddleBlockId, 0).setBlockName("NetherPuddle").setHardness(0.1F);
	public static final int netherPuddleHellfire = NetherPuddle.hellfire;
	public static final int netherPuddleAcid = NetherPuddle.acid;
	public static final int netherPuddleDeath = NetherPuddle.death;
	public static final Block netherSoulWorkBench = new SoulWorkBench(NetherStuffs.SoulWorkBenchBlockId).setBlockName("NetherSoulWorkBench").setHardness(5.0F);
	public static final Block netherSoulBomb = new SoulBomb(NetherStuffs.NetherSoulBombBlockId, 8).setBlockName("NetherSoulBomb").setHardness(0.0F);
	
	public static final Block NetherDemonicFurnace = new NetherDemonicFurnace(NetherStuffs.NetherDemonicFurnaceBlockId).setBlockName("NetherDemonicFurnace").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulFurnace = new NetherSoulFurnace(NetherStuffs.NetherSoulFurnaceBlockId).setBlockName("NetherSoulFurnace").setHardness(3.5F).setResistance(10.0F);
	public static final Block NetherSoulGlass = new NetherSoulGlass(NetherStuffs.NetherSoulGlassBlockid, 112, Material.glass, false).setBlockName("NetherSoulGlass").setHardness(0.3F).setResistance(10.0F);
	public static final Block NetherSoulGlassPane = new NetherSoulGlassPane(NetherStuffs.NetherSoulGlassPaneBlockid, 112, 113, Material.glass, false).setBlockName("NetherSoulGlassPane").setHardness(0.3F).setResistance(10.0F);
	
	public static final Block NetherSoulDetector = new SoulDetector(NetherStuffs.NetherSoulDetectorBlockId, 0).setBlockName("NetherSoulDetector");
	
	public static final Block NetherSoulBlocker = new SoulBlocker(NetherStuffs.NetherSoulBlockerBlockId, 114).setBlockName("NetherSoulBlocker");
}
