package NetherStuffs.Blocks;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherSaplingItemBlock extends ItemBlock {
	public static String[] blockNames = new String[] { "Hellfire", "Acid", "Death" };
	public static String[] blockDisplayNames = new String[] { "Hellfire Sapling", "Acid Sapling", "Death Sapling" };

	public NetherSaplingItemBlock(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	@Override
	public int getIconFromDamage(int par1) {
		return NetherBlocks.netherSapling.getBlockTextureFromSideAndMetadata(2, par1);
	}

	@Override
	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];

		return getItemName() + "." + name;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
