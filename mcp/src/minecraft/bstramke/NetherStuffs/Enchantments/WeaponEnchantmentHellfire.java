package bstramke.NetherStuffs.Enchantments;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class WeaponEnchantmentHellfire extends Enchantment {

	public WeaponEnchantmentHellfire(int par1, int par2) {
		super(par1, par2, EnumEnchantmentType.weapon);
		this.setName("hellfire");
		LanguageRegistry.instance().addStringLocalization("enchantment.hellfire", "Hellfire");
	}
	
	@Override
	public int getMaxLevel()
   {
       return 1;
   }
}
