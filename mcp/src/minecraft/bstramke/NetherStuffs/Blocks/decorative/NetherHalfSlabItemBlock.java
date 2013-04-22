package bstramke.NetherStuffs.Blocks.decorative;

import bstramke.NetherStuffs.Blocks.BlockRegistry;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.item.ItemSlab;

public class NetherHalfSlabItemBlock extends ItemSlab {

	public NetherHalfSlabItemBlock(int par1) {
		super(par1, BlockRegistry.HalfSlab, BlockRegistry.DoubleSlab, false);
	}
}
