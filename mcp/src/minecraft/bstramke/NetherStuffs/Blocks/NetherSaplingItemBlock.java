package bstramke.NetherStuffs.Blocks;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	public Icon getIconFromDamage(int par1) {
		return NetherBlocks.netherSapling.getIcon(2, par1);
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];

		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
