package NetherStuffs.Blocks;

import NetherStuffs.NetherStuffs;
import net.minecraft.src.Block;
import net.minecraft.src.BlockLeaves;

public class NetherBlocks {
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

}
