package bstramke.NetherStuffs.Blocks.puddles;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class NetherWoodPuddleItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "HellfirePuddle", "AcidPuddle", "DeathPuddle" };
	public static String[] blockDisplayNames = new String[] { "Hellfire Wood Puddled", "Acid Wood Puddled", "Death Wood Puddled" };

	public NetherWoodPuddleItemBlock(int id) {
		super(id);
		setHasSubtypes(true);
	}

	public static int getMetadataSize() {
		return blockNames.length;
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
