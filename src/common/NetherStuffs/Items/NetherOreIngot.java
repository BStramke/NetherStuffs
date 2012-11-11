package NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityXPOrb;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

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
	public String getTextureFile() {
		return "/items.png";
	}

	@Override
	public int getIconFromDamage(int par1) {
		return this.iconIndex + par1;
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
