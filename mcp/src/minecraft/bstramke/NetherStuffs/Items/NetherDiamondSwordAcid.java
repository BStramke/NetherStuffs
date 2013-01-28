package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NetherDiamondSwordAcid extends NetherSword {
	public NetherDiamondSwordAcid(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.acid);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
}
