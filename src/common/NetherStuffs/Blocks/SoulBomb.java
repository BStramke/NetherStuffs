package NetherStuffs.Blocks;

import net.minecraft.src.BlockTNT;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.World;
import NetherStuffs.SoulBomb.EntitySoulBombPrimed;

public class SoulBomb extends BlockTNT {
	
	public SoulBomb(int par1, int par2) {
		super(par1, par2);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setStepSound(soundGrassFootstep);
		this.setRequiresSelfNotify();
	}

   /**
    * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
    */
   public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
   {
       if (!par1World.isRemote)
       {
           if ((par5 & 1) == 1)
           {
               EntitySoulBombPrimed var6 = new EntitySoulBombPrimed(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F));
               par1World.spawnEntityInWorld(var6);
               par1World.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
           }
       }
   }
	
	/*public String getTextureFile() {
		return "/blocks.png";
	}*/

	/*public int getMetadataSize() {
		return NetherPlankItemBlock.blockNames.length;
	}*/

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		if(side==NetherBlocks.sideBottom || side == NetherBlocks.sideTop)
			return 9;
		else
			return 8;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	/*@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}*/
}