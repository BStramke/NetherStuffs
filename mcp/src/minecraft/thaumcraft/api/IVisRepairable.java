package thaumcraft.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;


/**
 * @author Azanor
 * Items and tools with this interface can receive the Repair enchantment. 
 * Armor is handled automatically, but tools and weapons will need some custom code to do the repairs. 
 * Usually 1 vis equals 1 point of durability every 2 seconds (1 second for repair II)
 * Some sample code:<p>
 * <i>
 *	public void doRepair(ItemStack is, Entity e) {<br>
 *	 if (AuraManager.decreaseClosestAura(e.worldObj,e.posX, e.posY, e.posZ, 1)) {<br>
 *		 is.damageItem(-1,(EntityLiving) e);<br>
 *		}<br>
 *	}<br><br>
 *	public void onUpdate(ItemStack is, World w, Entity e, int par4, boolean par5) {	<br>	
 *		int level = EnchantmentHelper.getEnchantmentLevel(Config.enchRepair.effectId, is);<br>
 *		if (level>0 && !w.isRemote && e.ticksExisted%(60-(level*20))==0 && is.getItemDamage()>0) doRepair(is,e);<br>
 *	}
 *</i>
 */
public interface IVisRepairable {
	
	void doRepair(ItemStack stack, Entity e);

}
