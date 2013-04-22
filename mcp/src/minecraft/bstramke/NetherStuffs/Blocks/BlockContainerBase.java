package bstramke.NetherStuffs.Blocks;

import bstramke.NetherStuffs.NetherStuffs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainerBase extends BlockContainer {

	protected BlockContainerBase(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(3.5F);
		setResistance(10.0F);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}

}
