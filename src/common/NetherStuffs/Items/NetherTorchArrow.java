package NetherStuffs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherTorchArrow extends Item {

	public NetherTorchArrow(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int par1) {
		return 64;
	}
	
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
