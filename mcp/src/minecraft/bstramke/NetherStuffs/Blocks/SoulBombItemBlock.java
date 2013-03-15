package bstramke.NetherStuffs.Blocks;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SoulBombItemBlock extends ItemBlock {
	public SoulBombItemBlock(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		String name = "NetherSoulBomb";
		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}
