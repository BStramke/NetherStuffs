package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherStonePotionBowl extends Item {

	public static String[] itemDisplayNames = new String[] { "Hellfire Bowl", "Acid Bowl", "Death Bowl" };

	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;
	private Icon icoBowlHellfire;
	private Icon icoBowlAcid;
	private Icon icoBowlDeath;

	public NetherStonePotionBowl(int par1) {
		super(par1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}

	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStoneBowl"));
		icoBowlHellfire = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStonePotionBowlHellfire"));
		icoBowlAcid = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStonePotionBowlAcid"));
		icoBowlDeath = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStonePotionBowlDeath"));
	}

	@Override
	public Icon getIconFromDamage(int par1) {
		switch (par1) {
		case hellfire:
			return icoBowlHellfire;
		case acid:
			return icoBowlAcid;
		case death:
			return icoBowlDeath;
		default:
			return iconIndex;
		}
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		if (is.getItemDamage() < itemDisplayNames.length && is.getItemDamage() >= 0)
			return itemDisplayNames[is.getItemDamage()];
		else
			return  itemDisplayNames[0];
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < itemDisplayNames.length; metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}

}
