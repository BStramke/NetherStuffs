package NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import NetherStuffs.Common.CommonProxy;

public class NetherWoodCharcoal extends Item {
	
	public static String[] itemNames = new String[] { "NetherCharcoal", "NetherCoal" };
	public static String[] itemDisplayNames = new String[] { "Nether Charcoal", "Nether Coal" };
	
	public static int charcoal = 0;
	public static int coal = 1;
	protected NetherWoodCharcoal(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override
	public int getIconFromDamage(int par1) {
		return this.iconIndex;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
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
