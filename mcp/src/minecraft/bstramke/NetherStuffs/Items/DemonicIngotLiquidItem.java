package bstramke.NetherStuffs.Items;

import java.text.NumberFormat;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.ILiquid;
import net.minecraftforge.liquids.LiquidStack;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;

public class DemonicIngotLiquidItem extends Item {

	public DemonicIngotLiquidItem(int par1) {
		super(par1);
	}

	@Override
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon(CommonProxy.getIconLocation("DemonicIngotLiquidItem"));
		NetherStuffs.DemonicIngotLiquid.setRenderingIcon(iconIndex);
	}
}
