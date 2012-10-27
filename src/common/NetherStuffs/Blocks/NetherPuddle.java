package NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherPuddle extends BlockFlower {
	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;

	public NetherPuddle(int par1, int par2) {
		super(par1, par2);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
		this.setHardness(0.1F);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return NetherPuddleItemBlock.blockNames.length;
	}

	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		return par1 == Block.netherrack.blockID || par1 == Block.slowSand.blockID || par1 == NetherBlocks.netherOre.blockID;
	}

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

	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		boolean bValid = true;
		int puddleMeta = par1World.getBlockMetadata(par2, par3, par4);
		if (par1World.provider.dimensionId != -1) // only allow in Nether
			return false;
		if (par3 >= 0 && par3 < 256) {

			for (int y = par3 + 1; y < 256 && bValid; y++) {
				if (!par1World.isAirBlock(par2, y, par4)) {
					if (par1World.getBlockId(par2, y, par3) == NetherBlocks.netherLeaves.blockID
							&& NetherLeaves.unmarkedMetadata(par1World.getBlockMetadata(par2, y, par3)) == puddleMeta)
						break; // we found a valid Top for the Puddle
					else
						bValid = false;
				}
			}

			return bValid;

		} else
			return false;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}