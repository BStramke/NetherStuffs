package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class NetherObsidianSwordAcid extends NetherSword {

	public NetherObsidianSwordAcid(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.acid);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
}
