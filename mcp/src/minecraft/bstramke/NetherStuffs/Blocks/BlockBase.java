package bstramke.NetherStuffs.Blocks;

import bstramke.NetherStuffs.NetherStuffs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block {

	public BlockBase(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(10.0F);
		setResistance(5.0F);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}

}
