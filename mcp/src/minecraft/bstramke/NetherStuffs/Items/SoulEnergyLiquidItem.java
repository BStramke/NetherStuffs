package bstramke.NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SoulEnergyLiquidItem extends Item {

	public SoulEnergyLiquidItem(int par1) {
		super(par1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int par1) {
		return 80;
	}
	
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		Integer nAmount = par1ItemStack.stackSize;
		
		par3List.add("Amount: " + nAmount.toString());
	}
}
