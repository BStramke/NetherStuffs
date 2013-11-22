package bstramke.NetherStuffs.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.decorative.NetherSlab;
import bstramke.NetherStuffs.Blocks.demonicFurnace.DemonicFurnace;
import bstramke.NetherStuffs.Blocks.soulBomb.SoulBomb;
import bstramke.NetherStuffs.Blocks.soulRipper.SoulRipper;
import bstramke.NetherStuffs.Blocks.soulWorkBench.SoulWorkBench;
import bstramke.NetherStuffs.NetherStuffs.IDs;

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

	public static final Block netherOre = new Ore(NetherStuffs.IDs.Blocks.NetherOreBlockId);
	public static final Block netherOreExtended = new OreExtended(NetherStuffs.IDs.Blocks.NetherOreExtendedBlockId);
	public static final Block netherWood = new Wood(NetherStuffs.IDs.Blocks.NetherWoodBlockId);
	public static final Block netherLeaves = new Leaf(NetherStuffs.IDs.Blocks.NetherLeavesBlockId);
	public static final Block netherPlank = new Plank(NetherStuffs.IDs.Blocks.NetherPlankBlockId);

	public static final BlockSapling Sapling = (BlockSapling) new Sapling(NetherStuffs.IDs.Blocks.NetherSaplingBlockId);
	public static final Block SoulWorkBench = new SoulWorkBench(NetherStuffs.IDs.Blocks.SoulWorkBenchBlockId);
	public static final Block SoulBomb = new SoulBomb(NetherStuffs.IDs.Blocks.NetherSoulBombBlockId);

	public static final Block DemonicFurnace = new DemonicFurnace(NetherStuffs.IDs.Blocks.NetherDemonicFurnaceBlockId);
	public static final Block SoulGlass = new SoulGlass(NetherStuffs.IDs.Blocks.NetherSoulGlassBlockid, Material.glass, false);
	public static final Block SoulGlassPane = new SoulGlassPane(NetherStuffs.IDs.Blocks.NetherSoulGlassPaneBlockid, Material.glass, false);

	public static final Block SoulRipper = new SoulRipper(NetherStuffs.IDs.Blocks.NetherSoulRipperBlockId);
	
	public static void initOreDictionary() {
		OreDictionary.registerOre("oreDemonic", new ItemStack(BlockRegistry.netherOre, 1, Ore.demonicOre));
		OreDictionary.registerOre("oreNetherIron", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreIron));
		OreDictionary.registerOre("oreNetherGold", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreGold));
		OreDictionary.registerOre("oreNetherDiamond", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreDiamond));
		OreDictionary.registerOre("oreNetherRedstone", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreRedstone));
		OreDictionary.registerOre("oreNetherEmerald", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreEmerald));
		OreDictionary.registerOre("oreNetherCoal", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreCoal));
		OreDictionary.registerOre("oreNetherObsidian", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreObsidian));
		OreDictionary.registerOre("oreNetherLapis", new ItemStack(BlockRegistry.netherOre, 1, Ore.netherOreLapis));

		OreDictionary.registerOre("logWood", new ItemStack(BlockRegistry.netherWood, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("plankWood", new ItemStack(BlockRegistry.netherPlank, 1, OreDictionary.WILDCARD_VALUE));
	}

}
