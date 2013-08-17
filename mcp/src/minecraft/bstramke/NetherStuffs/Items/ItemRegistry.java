package bstramke.NetherStuffs.Items;

import net.minecraft.item.Item;
import bstramke.NetherStuffs.NetherStuffs;

public class ItemRegistry {

	public static final Item NetherWoodCharcoal = new NetherCoal(NetherStuffs.NetherWoodCharcoalItemId);
	public static final Item NetherOreIngot = new NetherOreIngot(NetherStuffs.NetherOreIngotItemId);

	public static final Item NetherWoodStick = new NetherWoodStick(NetherStuffs.NetherWoodStickItemId);

	public static final Item NetherBow = new TorchBow(NetherStuffs.NetherBowItemId);
	public static final Item torchArrow = new TorchArrow(NetherStuffs.TorchArrowItemId);
}
