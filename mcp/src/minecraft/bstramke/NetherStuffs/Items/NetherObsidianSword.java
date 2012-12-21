package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class NetherObsidianSword extends NetherSword {

	public NetherObsidianSword(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
}
