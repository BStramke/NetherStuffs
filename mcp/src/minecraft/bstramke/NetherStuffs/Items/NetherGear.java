package bstramke.NetherStuffs.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;

public class NetherGear extends Item {

	public NetherGear(int par1) {
		super(par1);
		maxStackSize = 64;
		this.setMaxDamage(0);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		//this.setHasSubtypes(true);
	}

	@Override
	public void updateIcons(IconRegister iconRegister)
	{
		iconIndex = iconRegister.registerIcon(CommonProxy.getIconLocation("demonic_gear"));
	}
	
	@Override
	public String getItemDisplayName(ItemStack is) {
		return "Demonic Gear";
	}
}
