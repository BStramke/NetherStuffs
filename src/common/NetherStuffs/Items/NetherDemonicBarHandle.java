package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class NetherDemonicBarHandle extends Item {

	public NetherDemonicBarHandle(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	public String getTextureFile() {
		return "/items.png";
	}
}
