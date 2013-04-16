package bstramke.NetherStuffs.Blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.NetherWoodMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NetherSlab extends BlockHalfSlab {

	public NetherSlab(int par1, boolean par2) {
		super(par1, par2, NetherWoodMaterial.netherWood);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundWoodFootstep);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return NetherBlocks.netherPlank.getBlockTextureFromSideAndMetadata(par1, par2 & 7);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return NetherBlocks.HalfSlab.blockID;
	}

	@Override
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(NetherBlocks.HalfSlab.blockID, 2, par1 & 7);
	}
	
	@Override
	public String getFullSlabName(int par1) {
		if (par1 < 0 || par1 >= NetherPlankItemBlock.blockNames.length) {
			par1 = 0;
		}

		return super.getUnlocalizedName() + "." + NetherPlankItemBlock.blockNames[par1];
	}

	public int getMetadataSize() {
		return NetherPlankItemBlock.blockNames.length;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list) {
		if (par1 != NetherBlocks.DoubleSlab.blockID) {
			for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
				list.add(new ItemStack(par1, 1, metaNumber));
			}
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (this.isDoubleSlab) {
			return NetherBlocks.netherPlank.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		} else if (par5 != 1 && par5 != 0 && !NetherBlocks.netherPlank.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)) {
			return false;
		} else {
			int i1 = par2 + Facing.offsetsXForSide[Facing.faceToSide[par5]];
			int j1 = par3 + Facing.offsetsYForSide[Facing.faceToSide[par5]];
			int k1 = par4 + Facing.offsetsZForSide[Facing.faceToSide[par5]];
			boolean flag = (par1IBlockAccess.getBlockMetadata(i1, j1, k1) & 8) != 0;
			return flag ? (par5 == 0 ? true : (par5 == 1 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess
					.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0)) : (par5 == 1 ? true : (par5 == 0
					&& super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess.getBlockId(par2, par3, par4))
					|| (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0));
		}
	}

	@SideOnly(Side.CLIENT)
	private static boolean isBlockSingleSlab(int par0) {
		return par0 == NetherBlocks.HalfSlab.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		if (isBlockSingleSlab(this.blockID))
			return this.blockID;

		if (this.blockID == NetherBlocks.DoubleSlab.blockID)
			return NetherBlocks.DoubleSlab.blockID;

		return NetherBlocks.HalfSlab.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {}
}
