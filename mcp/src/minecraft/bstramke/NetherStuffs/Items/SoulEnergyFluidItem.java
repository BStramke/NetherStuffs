package bstramke.NetherStuffs.Items;

import java.text.NumberFormat;
import java.util.List;

import bstramke.NetherStuffs.Common.CommonProxy;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulEnergyFluidItem extends Item {
	
	public SoulEnergyFluidItem(int par1) {
		super(par1);
		setMaxDamage(0);
		
		setUnlocalizedName("SoulEnergyLiquidItem");
		LanguageRegistry.instance().addStringLocalization("item.SoulEnergyLiquidItem.name", "Soul Energy");
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("SoulEnergyLiquidItem"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		Integer nAmount = par1ItemStack.stackSize;

		if (par1ItemStack.getItemDamage() > 0)
			par3List.add("Soulenergy: " + NumberFormat.getInstance().format(nAmount) + " / " + NumberFormat.getInstance().format(par1ItemStack.getItemDamage()));
	}
}
