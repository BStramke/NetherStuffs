package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemGlassBottle;

public class NetherSoulGlassBottle extends ItemGlassBottle {

	public NetherSoulGlassBottle(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	public String getTextureFile() {
		return "/items.png";
	}

}
