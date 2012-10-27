package NetherStuffs.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.BlockGlass;
import net.minecraft.src.BlockSapling;
import NetherStuffs.NetherStuffs;

public class NetherBlocks {
	public static final int sideBottom = 0; //fact?
	public static final int sideTop = 1; //fact?
	public static final int sideFront = 3; //fact?
	public static final int sideBack = 2; //fact? 
	public static final int sideRight = 5;
	public static final int sideLeft = 4;	
	
	public static final Block netherOre = new NetherOre(NetherStuffs.NetherOreBlockId, 0).setBlockName("NetherOre").setHardness(1F).setResistance(5F);
	public static final int demonicOre = NetherOre.demonicOre;
	public static final int netherStone = NetherOre.netherStone;
	public static final Block netherWood = new NetherWood(NetherStuffs.NetherWoodBlockId, 0).setBlockName("NetherWood");
	public static final int netherWoodHellfire = NetherWood.hellfire;
	public static final int netherWoodAcid = NetherWood.acid;
	public static final int netherWoodDeath = NetherWood.death;
	public static final Block netherLeaves = new NetherLeaves(NetherStuffs.NetherLeavesBlockId, 18).setBlockName("NetherLeaves").setHardness(0.2F).setLightOpacity(1);
	public static final int netherLeavesHellfire = NetherLeaves.hellfire;
	public static final int netherLeavesAcid = NetherLeaves.acid;
	public static final int netherLeavesDeath = NetherLeaves.death;
	public static final Block netherPlank = new NetherPlank(NetherStuffs.NetherPlankBlockId, 0).setBlockName("NetherPlank");
	public static final int netherPlankHellfire = NetherPlank.hellfire;
	public static final int netherPlankAcid = NetherPlank.acid;
	public static final int netherPlankDeath = NetherPlank.death;
	public static final BlockSapling netherSapling = (BlockSapling) new NetherSapling(NetherStuffs.NetherSaplingBlockId, 0).setBlockName("NetherSapling");
	public static final int netherSaplingHellfire = NetherSapling.hellfire;
	public static final int netherSaplingAcid = NetherSapling.acid;
	public static final int netherSaplingDeath = NetherSapling.death;	
}
