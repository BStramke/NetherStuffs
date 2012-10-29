package NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;

public class NetherObsidianSwordDeath extends ItemSword {

	public NetherObsidianSwordDeath(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	public String getTextureFile() {
		return "/items.png";
	}

	@Override
	public int getDamageVsEntity(Entity par1Entity) {
		byte var2 = 0;
		
		((EntityLiving) par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 20*60, 8));
		((EntityLiving) par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 20*5, 8));
		return super.getDamageVsEntity(par1Entity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// TODO Auto-generated method stub
		par3List.add("Death Blade");
	}
}
