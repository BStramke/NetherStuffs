package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class NetherWoodStick extends Item {

	public NetherWoodStick(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public String getTextureFile() {
		return "/items.png";
	}
}
