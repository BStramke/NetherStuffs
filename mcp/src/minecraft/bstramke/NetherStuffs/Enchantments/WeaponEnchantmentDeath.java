package bstramke.NetherStuffs.Enchantments;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class WeaponEnchantmentDeath extends Enchantment {
	public WeaponEnchantmentDeath(int par1, int par2) {
		super(par1, par2, EnumEnchantmentType.weapon);
		this.setName("death");
		LanguageRegistry.instance().addStringLocalization("enchantment.death", "Death");
	}
	
	@Override
	public int getMaxLevel()
   {
       return 1;
   }
}
