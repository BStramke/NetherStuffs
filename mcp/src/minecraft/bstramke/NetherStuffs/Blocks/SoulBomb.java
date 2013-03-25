package bstramke.NetherStuffs.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
		// this.setRequiresSelfNotify();
	}

	/**
	 * Called right before the block is destroyed by a player. Args: world, x, y, z, metaData
	 */
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		this.PrimeTnt(par1World, par2, par3, par4, par5, (EntityLiving) null);
	}

	public void PrimeTnt(World par1World, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving) {
		if (!par1World.isRemote) {
			if ((par5 & 1) == 1) {
				EntitySoulBombPrimed var6 = new EntitySoulBombPrimed(par1World, (double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F),
						par6EntityLiving);
				par1World.spawnEntityInWorld(var6);
				par1World.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
			}
		}
	}
	
   /**
    * Called upon block activation (right click on the block.)
    */
	@Override
   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
   {
       if (par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID)
       {
           this.PrimeTnt(par1World, par2, par3, par4, 1, par5EntityPlayer);
           par1World.setBlockToAir(par2, par3, par4);
           return true;
       }
       else
       {
           return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
       }
   }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icoSoulBombTop = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulBombTop"));
		icoSoulBombBottom = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulBombBottom"));
		icoSoulBombSide = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulBombSide"));
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
