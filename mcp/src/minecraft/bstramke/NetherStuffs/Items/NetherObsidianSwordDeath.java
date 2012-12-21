package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class NetherObsidianSwordDeath extends NetherSword {

	public NetherObsidianSwordDeath(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.death);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
}
