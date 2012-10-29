package NetherStuffs.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
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
