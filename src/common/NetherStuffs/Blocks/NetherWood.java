package NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherWood extends Block {

	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;

	public NetherWood(int par1, int par2) {
		super(par1, par2, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundWoodFootstep);
		this.setRequiresSelfNotify();
	}

	@Override
	public boolean isWood(World world, int x, int y, int z) {
		return true;
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType() {
		return 31;
	}

	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z) {
		return true;
	}

	public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return NetherWoodItemBlock.blockNames.length;
	}

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {

		/*
		 * int nRowDiff = 32; // side: 1=top, 0=bottom if (side == 1 || side == 0) { nRowDiff = nRowDiff - 16;// look one row above } switch (var4) { case hellfire: return hellfire +
		 * nRowDiff; case acid: return acid + nRowDiff; case death: return death + nRowDiff; default: return hellfire + nRowDiff; }
		 */
		int orientation = meta & 12;
		int type = meta & 3;

		if (orientation == 0 && (side == 1 || side == 0)) {
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

	public void updateBlockMetadata(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8) {
		int var9 = par1World.getBlockMetadata(par2, par3, par4) & 3;
		byte var10 = 0;

		switch (par5) {
		case 0:
		case 1:
			var10 = 0;
			break;
		case 2:
		case 3:
			var10 = 8;
			break;
		case 4:
		case 5:
			var10 = 4;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, var9 | var10);
	}

	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}