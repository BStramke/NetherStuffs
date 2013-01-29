package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import bstramke.NetherStuffs.Items.NetherSword.Types;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NetherDiamondSwordDeath extends NetherSword {
	public NetherDiamondSwordDeath(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.death);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
}