package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import NetherStuffs.Common.CommonProxy;

public class NetherWoodCharcoal extends Item {

	protected NetherWoodCharcoal(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
