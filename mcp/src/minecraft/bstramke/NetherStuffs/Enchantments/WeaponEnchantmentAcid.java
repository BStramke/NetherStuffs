package bstramke.NetherStuffs.Enchantments;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;

public class WeaponEnchantmentAcid extends Enchantment {
	public WeaponEnchantmentAcid(int par1, int par2) {
		super(par1, par2, EnumEnchantmentType.weapon);
		this.setName("acid");
		LanguageRegistry.instance().addStringLocalization("enchantment.acid", "Acid");
	}
	
	@Override
	public int getMaxLevel()
   {
       return 1;
   }
}
