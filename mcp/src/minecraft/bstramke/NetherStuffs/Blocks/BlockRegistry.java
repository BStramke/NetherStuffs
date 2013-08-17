package bstramke.NetherStuffs.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.decorative.NetherSlab;
import bstramke.NetherStuffs.Blocks.demonicFurnace.DemonicFurnace;
import bstramke.NetherStuffs.Blocks.soulBomb.SoulBomb;
import bstramke.NetherStuffs.Blocks.soulSiphon.SoulSiphon;
import bstramke.NetherStuffs.Blocks.soulWorkBench.SoulWorkBench;

public class BlockRegistry {
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
	
	public static final Block netherOre = new Ore(NetherStuffs.NetherOreBlockId);
	public static final Block netherWood = new Wood(NetherStuffs.NetherWoodBlockId);
	public static final Block netherLeaves = new Leaf(NetherStuffs.NetherLeavesBlockId);
	public static final Block netherPlank = new Plank(NetherStuffs.NetherPlankBlockId);
	
	public static final BlockSapling Sapling = (BlockSapling) new Sapling(NetherStuffs.NetherSaplingBlockId);
	public static final Block SoulWorkBench = new SoulWorkBench(NetherStuffs.SoulWorkBenchBlockId);
	public static final Block SoulBomb = new SoulBomb(NetherStuffs.NetherSoulBombBlockId);
	
	public static final Block DemonicFurnace = new DemonicFurnace(NetherStuffs.NetherDemonicFurnaceBlockId);
	public static final Block SoulGlass = new SoulGlass(NetherStuffs.NetherSoulGlassBlockid, Material.glass, false);
	public static final Block SoulGlassPane = new SoulGlassPane(NetherStuffs.NetherSoulGlassPaneBlockid, Material.glass, false);
	
	public static final Block SoulSiphon = new SoulSiphon(NetherStuffs.NetherSoulSiphonBlockId);	
}
