package bstramke.NetherStuffs.Blocks;

import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulBomb.EntitySoulBombPrimed;

public class SoulBomb extends BlockTNT {

	private Icon icoSoulBombTop;
	private Icon icoSoulBombBottom;
	private Icon icoSoulBombSide;

	public SoulBomb(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setStepSound(soundGrassFootstep);
		this.setRequiresSelfNotify();
	}

	/**
	 * Called right before the block is destroyed by a player. Args: world, x, y, z, metaData
	 */
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		if (!par1World.isRemote) {
			if ((par5 & 1) == 1) {
				EntitySoulBombPrimed var6 = new EntitySoulBombPrimed(par1World, (double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F));
				par1World.spawnEntityInWorld(var6);
				par1World.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
			}
		}
	}

	@Override
	public void func_94332_a(IconRegister par1IconRegister)
	{
		icoSoulBombTop = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulBombTop"));
		icoSoulBombBottom = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulBombBottom"));
		icoSoulBombSide = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulBombSide"));
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (side) {
		case NetherBlocks.sideTop:
			return icoSoulBombTop;
		case NetherBlocks.sideBottom:
			return icoSoulBombBottom;
		default:
			return icoSoulBombSide;
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

}
