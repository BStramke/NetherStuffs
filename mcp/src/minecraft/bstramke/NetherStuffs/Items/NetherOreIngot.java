package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherOreIngot extends Item {

	public static String[] itemNames = new String[] { "DemonicIngot" };
	public static String[] itemDisplayNames = new String[] { "Demonic Ingot" };

	public NetherOreIngot(int par1) {
		super(par1);
		maxStackSize = 64;
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setHasSubtypes(true);
	}

	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("IngotDemonic"));
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

		int var2 = par1ItemStack.stackSize;
		if (var2 <= 0)
			return;

		float var3 = 0; // Var3==Exp for 1 Item

		switch (par1ItemStack.getItemDamage()) {
		case 0:
			var3 = 1;
			break;
		case 1:
			var3 = 0.5F;
			break;
		}

		int var4 = 0; // contains calculated xp to process

		if (var3 == 0.0F) {
			var2 = 0;
		} else if (var3 < 1.0F) {
			var4 = MathHelper.floor_float((float) var2 * var3);

			if (var4 < MathHelper.ceiling_float_int((float) var2 * var3) && (float) Math.random() < (float) var2 * var3 - (float) var4) {
				++var4;
			}

			var2 = var4;
		} else
			var4 = (int) (var2 * var3);

		par3EntityPlayer.addExperience(var4);
	}

	public static int getMetadataSize() {
		return itemNames.length;
	}

	@Override
	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = itemNames[is.getItemDamage()];
		else
			name = itemNames[0];

		return getItemName() + "." + name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
