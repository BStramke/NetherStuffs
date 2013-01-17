package bstramke.NetherStuffs.mc15compat;

import java.util.Random;

import bstramke.NetherStuffs.Common.CommonProxy;

import net.minecraft.block.BlockOreStorage;

public class BlockQuartz extends BlockOreStorage {

	public BlockQuartz(int par1, int par2) {
		super(par1, par2);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 4;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return CompatItem.netherQuartz.itemID;
	}
	
	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}
}
