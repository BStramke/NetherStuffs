package bstramke.NetherStuffs.Blocks.soulDetector;

import java.text.NumberFormat;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.CommonProxy;

public class SoulDetectorItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "SoulDetectorMK1", "SoulDetectorMK2", "SoulDetectorMK3", "SoulDetectorMK4" };
	public static String[] blockDisplayNames = new String[] { "SoulDetector MK1", "SoulDetector MK2", "SoulDetector MK3", "SoulDetector MK4" };

	public SoulDetectorItemBlock(int par1) {
		super(par1);
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
