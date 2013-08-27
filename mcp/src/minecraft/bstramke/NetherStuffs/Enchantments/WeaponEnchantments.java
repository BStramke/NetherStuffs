package bstramke.NetherStuffs.Enchantments;

import bstramke.NetherStuffs.NetherStuffs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;

public class WeaponEnchantments {
	public static final Enchantment hellfireAspect = new WeaponEnchantmentHellfire(NetherStuffs.EnchantmentHellfireId, 2);
	public static final Enchantment deathAspect = new WeaponEnchantmentDeath(NetherStuffs.EnchantmentDeathId, 2);
	public static final Enchantment acidAspect = new WeaponEnchantmentAcid(NetherStuffs.EnchantmentAcidId, 2);
	
	public WeaponEnchantments() {
		Enchantment.addToBookList(hellfireAspect);
		Enchantment.addToBookList(deathAspect);
		Enchantment.addToBookList(acidAspect);
	}
}
