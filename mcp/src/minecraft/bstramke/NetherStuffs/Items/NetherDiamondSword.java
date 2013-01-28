package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NetherDiamondSword extends NetherSword {
	public NetherDiamondSword(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial);
		this.setCreativeTab(CreativeTabs.tabCombat);
		LanguageRegistry.instance().addStringLocalization("item.NetherDiamondSword.name", "Diamond Sword");
	}
}
