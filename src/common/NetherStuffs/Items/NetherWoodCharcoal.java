package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class NetherWoodCharcoal extends Item {

	protected NetherWoodCharcoal(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	public String getTextureFile() {
		return "/items.png";
	}
}
