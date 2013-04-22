package bstramke.NetherStuffs.Blocks.decorative;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.PlankItemBlock;
import bstramke.NetherStuffs.Common.Materials.NetherMaterials;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSlab extends BlockHalfSlab {

	public NetherSlab(int par1, boolean par2) {
		super(par1, par2, NetherMaterials.netherWood);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundWoodFootstep);
		
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return BlockRegistry.netherPlank.getIcon(par1, par2 & 7);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return BlockRegistry.HalfSlab.blockID;
	}

	@Override
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(BlockRegistry.HalfSlab.blockID, 2, par1 & 7);
	}

	@Override
	public String getFullSlabName(int par1) {
		if (par1 < 0 || par1 >= PlankItemBlock.blockNames.length) {
			par1 = 0;
		}

		return super.getUnlocalizedName() + "." + PlankItemBlock.blockNames[par1];
	}

	public int getMetadataSize() {
		return PlankItemBlock.blockNames.length;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list) {
		if (par1 != BlockRegistry.DoubleSlab.blockID) {
			for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
				list.add(new ItemStack(par1, 1, metaNumber));
			}
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (this.isDoubleSlab) {
			return BlockRegistry.netherPlank.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		} else if (par5 != 1 && par5 != 0 && !BlockRegistry.netherPlank.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)) {
			return false;
		} else {
			int i1 = par2 + Facing.offsetsXForSide[Facing.oppositeSide[par5]];
			int j1 = par3 + Facing.offsetsYForSide[Facing.oppositeSide[par5]];
			int k1 = par4 + Facing.offsetsZForSide[Facing.oppositeSide[par5]];
			boolean flag = (par1IBlockAccess.getBlockMetadata(i1, j1, k1) & 8) != 0;
			return flag ? (par5 == 0 ? true : (par5 == 1 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess
					.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0)) : (par5 == 1 ? true : (par5 == 0
					&& super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess.getBlockId(par2, par3, par4))
					|| (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0));
		}
	}

	@SideOnly(Side.CLIENT)
	private static boolean isBlockSingleSlab(int par0) {
		return par0 == BlockRegistry.HalfSlab.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		if (isBlockSingleSlab(this.blockID))
			return this.blockID;

		if (this.blockID == BlockRegistry.DoubleSlab.blockID)
			return BlockRegistry.DoubleSlab.blockID;

		return BlockRegistry.HalfSlab.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {}
}
