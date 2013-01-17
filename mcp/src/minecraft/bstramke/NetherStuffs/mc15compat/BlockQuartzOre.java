package bstramke.NetherStuffs.mc15compat;

import java.util.Random;

import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockQuartzOre extends BlockOre {

	public BlockQuartzOre(int par1, int par2) {
		super(par1, par2);
	}
	
	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return CompatItem.netherQuartz.itemID;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		 return true;
	}
}
