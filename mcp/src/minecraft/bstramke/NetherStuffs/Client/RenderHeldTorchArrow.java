package bstramke.NetherStuffs.Client;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderHeldTorchArrow implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		/*
		 * if (type == ItemRenderType.EQUIPPED && item.isItemEqual(new ItemStack(NetherItems.NetherBow))) return true; else
		 */
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {}
}
