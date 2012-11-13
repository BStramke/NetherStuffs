package NetherStuffs.Blocks;

import java.util.List;

import NetherStuffs.SoulBlocker.TileSoulBlocker;
import NetherStuffs.SoulDetector.TileSoulDetector;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class SoulBlocker extends BlockContainer {
	public static final int NetherSoulBlocker = 0;

	public SoulBlocker(int par1, int par2) {
		super(par1, par2, Material.circuits);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return SoulBlockerItemBlock.blockNames.length;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (meta) {
		case NetherSoulBlocker:
			return 114;
		default:
			return 114;
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulBlocker();
	}

	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
