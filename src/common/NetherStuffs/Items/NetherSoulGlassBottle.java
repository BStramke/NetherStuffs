package NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemGlassBottle;
import net.minecraft.src.ItemStack;

public class NetherSoulGlassBottle extends ItemGlassBottle {
	public static String[] itemNames = new String[] { "SoulglassBottle", "SoulglassBottleHellfire", "SoulglassBottleAcid", "SoulglassBottleDeath" };
	public static String[] itemDisplayNames = new String[] { "Soulglass Bottle", "Soulglass Bottle Hellfire", "Soulglass Bottle Acid", "Soulglass Bottle Death" };
	public static final int empty = 0;
	public static final int hellfire = 1;
	public static final int acid = 2;
	public static final int death = 3;

	public NetherSoulGlassBottle(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		this.setHasSubtypes(true);
	}

	public String getTextureFile() {
		return "/items.png";
	}

	public int getIconFromDamage(int par1) {
		switch (par1) {
		case empty:
			return 16;
		case hellfire:
			return 32;
		case acid:
			return 33;
		case death:
			return 34;
		default:
			return 16;
		}
	}

	public static int getMetadataSize() {
		return itemNames.length;
	}

	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = itemNames[is.getItemDamage()];
		else
			name = itemNames[0];

		return getItemName() + "." + name;
	}

	public int getMetadata(int meta) {
		return meta;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
