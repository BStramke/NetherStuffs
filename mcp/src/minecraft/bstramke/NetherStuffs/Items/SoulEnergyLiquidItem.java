package bstramke.NetherStuffs.Items;

import java.text.NumberFormat;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulEnergyLiquidItem extends Item {

	public SoulEnergyLiquidItem(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public void func_94581_a(IconRegister iconRegister) {
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("SoulEnergyLiquidItem"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		Integer nAmount = par1ItemStack.stackSize;

		if (par1ItemStack.getItemDamage() > 0)
			par3List.add("Soulenergy: " + NumberFormat.getInstance().format(nAmount) + " / " + NumberFormat.getInstance().format(par1ItemStack.getItemDamage()));
		else
			par3List.add("Soulenergy: " + NumberFormat.getInstance().format(nAmount));
	}
}
