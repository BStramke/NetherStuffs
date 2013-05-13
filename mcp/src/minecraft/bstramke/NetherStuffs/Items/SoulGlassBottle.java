package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.Wood;
import bstramke.NetherStuffs.Blocks.puddles.TileNetherWoodPuddle;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulGlassBottle extends Item {
	
	public static String[] itemDisplayNames = new String[] { "Soulglass Bottle" };

	public SoulGlassBottle(int par1) {
		super(par1);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setUnlocalizedName("NetherSoulGlassBottleItem");
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherSoulglassBottle"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return itemIcon;
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		if (is.getItemDamage() < itemDisplayNames.length && is.getItemDamage() >= 0)
			return itemDisplayNames[is.getItemDamage()];
		else
			return itemDisplayNames[0];
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
