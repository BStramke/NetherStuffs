package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemSword;

public class NetherObsidianSword extends ItemSword {

	public NetherObsidianSword(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	public String getTextureFile() {
		return "/items.png";
	}

}
