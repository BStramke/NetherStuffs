package NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;

public class NetherObsidianSwordAcid extends ItemSword {

	public NetherObsidianSwordAcid(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	public String getTextureFile() {
		return "/items.png";
	}

	@Override
	public int getDamageVsEntity(Entity par1Entity) {
		//System.out.println(((EntityLiving) par1Entity).getActivePotionEffects());
		//if (((EntityLiving) par1Entity).isEntityUndead()) {} else
		
		((EntityLiving) par1Entity).addPotionEffect(new PotionEffect(Potion.field_82731_v.id, 20*15, 4));
		return super.getDamageVsEntity(par1Entity);
	}
	

	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// TODO Auto-generated method stub
		par3List.add("Acid Blade");
	}
}
