package bstramke.NetherStuffs.Blocks;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.NetherWoodMaterial;
import bstramke.NetherStuffs.NetherWoodPuddle.TileNetherWoodPuddle;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherWood extends Block {
	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;

	public NetherWood(int par1, int par2) {
		super(par1, par2, NetherWoodMaterial.netherWood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundWoodFootstep);
		this.setRequiresSelfNotify();
		this.setBurnProperties(this.blockID, 0, 0);
	}
	
	@Override
	public boolean isFireSource(World world, int x, int y, int z, int metadata, ForgeDirection side) {
		if (side == UP) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isWood(World world, int x, int y, int z) {
		return true;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 31;
	}

	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	public int getMetadataSize() {
		return NetherWoodItemBlock.blockNames.length;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		par1World.markBlockForUpdate(par2, par3, par4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		/*
		 * int nRowDiff = 32; // side: 1=top, 0=bottom if (side == 1 || side == 0) { nRowDiff = nRowDiff - 16;// look one row above } switch (var4) { case hellfire: return hellfire + nRowDiff; case
		 * acid: return acid + nRowDiff; case death: return death + nRowDiff; default: return hellfire + nRowDiff; }
		 */
		int orientation = meta & 12;
		int type = meta & 3;

		if ((orientation == 0 || orientation == 12) && (side == 1 || side == 0)) {
			if (type == hellfire)
				return 16;
			if (type == acid)
				return 17;
			if (type == death)
				return 18;
		}
		if (orientation == 4 && (side == 5 || side == 4)) {
			if (type == hellfire)
				return 16;
			if (type == acid)
				return 17;
			if (type == death)
				return 18;
		}
		if (orientation == 8 && (side == 2 || side == 3)) {
			if (type == hellfire)
				return 16;
			if (type == acid)
				return 17;
			if (type == death)
				return 18;
		}

		if (type == hellfire)
			return 32;
		if (type == acid)
			return 33;
		if (type == death)
			return 34;

		return 16;

	}

	@Override
	public void onBlockAdded(World par1World, int x, int y, int z) {
		super.onBlockAdded(par1World, x, y, z);
		if (!par1World.isRemote) {
			if (par1World.rand.nextInt(100) + 1 <= 15) // chance of being able to spawn a puddled block
			{
				int meta = par1World.getBlockMetadata(x, y, z) & 3;
				par1World.setBlockAndMetadata(x, y, z, NetherStuffs.NetherWoodPuddleBlockId, meta);
			}
		}
	}
	
	//this does the block sideway placement
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int side, float par6, float par7, float par8, int meta) {
		int type = meta & 3;
		byte orientation = 0;

		switch (side) {
		case 0:
		case 1:
			orientation = 0; 
			break;
		case 2:
		case 3:
			orientation = 8;
			break;
		case 4:
		case 5:
			orientation = 4;
		}

		return type | orientation;
	}

	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}