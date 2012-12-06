package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import NetherStuffs.Common.CommonProxy;

public class NetherDemonicBarHandle extends Item {

	public NetherDemonicBarHandle(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
