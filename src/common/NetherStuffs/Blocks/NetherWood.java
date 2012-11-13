package NetherStuffs.Blocks;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.List;

import NetherStuffs.Common.NetherWoodMaterial;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapColor;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.WorldProviderEnd;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

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
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return NetherWoodItemBlock.blockNames.length;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {

		/*
		 * int nRowDiff = 32; // side: 1=top, 0=bottom if (side == 1 || side == 0) { nRowDiff = nRowDiff - 16;// look one row above } switch (var4) { case hellfire: return hellfire + nRowDiff; case
		 * acid: return acid + nRowDiff; case death: return death + nRowDiff; default: return hellfire + nRowDiff; }
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


	//this does the block sideway placement
	@Override
	public int func_85104_a(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
		int var10 = par9 & 3;
		byte var11 = 0;

		switch (par5) {
		case 0:
		case 1:
			var11 = 0;
			break;
		case 2:
		case 3:
			var11 = 8;
			break;
		case 4:
		case 5:
			var11 = 4;
		}

		return var10 | var11;
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