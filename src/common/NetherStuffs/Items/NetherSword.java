package NetherStuffs.Items;

import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherSword extends ItemSword {
	public static enum Types {
		hellfire(1), acid(2), death(3), undefined(0);
		private final int value;

		private Types(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public String getTextureFile() {
		return "/items.png";
	}

	protected Types nType = Types.undefined;

	public NetherSword(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
		this.nType = Types.undefined;
	}

	public NetherSword(int par1, EnumToolMaterial par2EnumToolMaterial, Types nType) {
		super(par1, par2EnumToolMaterial);
		this.nType = nType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		if (this.nType != Types.undefined)
			return true;
		else
			return false;
	}

	@Override
	public int getDamageVsEntity(Entity par1Entity) {
		if (this.nType == Types.acid) {
			((EntityLiving) par1Entity).addPotionEffect(new PotionEffect(Potion.field_82731_v.id, 20 * 15, 4));
		} else if (this.nType == Types.death) {
			((EntityLiving) par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 20 * 60, 8));
			((EntityLiving) par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 20 * 5, 8));
		} else if (this.nType == Types.hellfire) {}

		return super.getDamageVsEntity(par1Entity);
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
		super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		// par3EntityLiving == attacking entity
		// par2EntityLiving == attacked entity

		if (!par3EntityLiving.worldObj.isRemote) {
			InventoryPlayer inventoryPlayer = ((EntityPlayer) par3EntityLiving).inventory;
			int nAmountToAdd = getDamageVsEntity(par2EntityLiving);
			for (int i = 0; i < inventoryPlayer.mainInventory.length && nAmountToAdd > 0; i++) {
				if (inventoryPlayer.mainInventory[i] != null && inventoryPlayer.mainInventory[i].itemID == new ItemStack(NetherItems.SoulEnergyBottle.shiftedIndex, 1, 0).itemID) {
					nAmountToAdd = SoulEnergyBottle.addSoulEnergy(nAmountToAdd, inventoryPlayer.mainInventory[i]);
					System.out.println(nAmountToAdd);
				}
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if (this.nType == Types.acid) {
			par3List.add("Acid Blade");
		} else if (this.nType == Types.death) {
			par3List.add("Death Blade");
		} else if (this.nType == Types.hellfire) {
			par3List.add("Hellfire Blade");
		}
	}
}
