package bstramke.NetherStuffs.Blocks.decorative;

import bstramke.NetherStuffs.NetherStuffs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class NetherStairs extends BlockStairs {

	public NetherStairs(int par1, Block par2Block, int par3) {
		super(par1, par2Block, par3);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}

}
