package NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemStack;

public class NetherOreIngot extends Item {
	public NetherOreIngot(int par1) {
		super(par1);
		maxStackSize = 64;
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setHasSubtypes(true);
	}

	public String getTextureFile() {
		return "/items.png";
	}

	public int getIconFromDamage(int par1) {
		return this.iconIndex + par1;
	}

	public String getItemNameIS(ItemStack is) {
		String name = "";
		switch (is.getItemDamage()) {
		case 0:
			name = "DemonicIngot";
			break;
		default:
			name = "DemonicIngot";
		}
		
		return getItemName() + "." + name;
	}
	
	@SideOnly(Side.CLIENT)
		
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for(int metaNumber=0; metaNumber<1; metaNumber++){
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
