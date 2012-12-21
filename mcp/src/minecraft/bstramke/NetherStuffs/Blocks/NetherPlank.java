package bstramke.NetherStuffs.Blocks;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.NetherWoodMaterial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherPlank extends Block {
	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;

	public NetherPlank(int par1, int par2) {
		super(par1, par2, NetherWoodMaterial.netherWood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
		this.setStepSound(soundWoodFootstep);
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
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	public int getMetadataSize() {
		return NetherPlankItemBlock.blockNames.length;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		int nRowDiff = 48;
		switch (meta) {
		case hellfire:
			return hellfire + nRowDiff;
		case acid:
			return acid + nRowDiff;
		case death:
			return death + nRowDiff;
		default:
			return hellfire + nRowDiff;
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}