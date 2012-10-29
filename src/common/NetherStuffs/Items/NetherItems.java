package NetherStuffs.Items;

import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraftforge.common.EnumHelper;
import NetherStuffs.NetherStuffs;

public class NetherItems {
	static EnumToolMaterial EnumToolMaterialDemonicIngot = EnumHelper.addToolMaterial("DemonicIngot", 2, 400, 6.0F, 6, 15);
	static EnumToolMaterial EnumToolMaterialObsidian = EnumHelper.addToolMaterial("Obsidian", 2, 400, 6.0F, 3, 15);

	public static Item NetherPotionBottle = new NetherPotionBottle(NetherStuffs.NetherPotionBottleItemId).setItemName("NetherPotionBottle").setIconCoord(0, 0);
	public static Item NetherStonePotionBowl = new NetherStonePotionBowl(NetherStuffs.NetherStonePotionBowlItemId).setItemName("NetherStonePotionBowl").setIconCoord(0,0);
	/**
	 * Swords
	 */
	public static Item NetherObsidianSword = new NetherObsidianSword(NetherStuffs.NetherObsidianSwordItemId, EnumToolMaterialObsidian).setItemName("NetherObsidianSword")
			.setIconCoord(1, 1);
	public static Item NetherObsidianSwordAcid = new NetherObsidianSwordAcid(NetherStuffs.NetherObsidianSwordAcidItemId, EnumToolMaterialObsidian).setItemName(
			"NetherObsidianSword").setIconCoord(1, 1);
	public static Item NetherObsidianSwordDeath = new NetherObsidianSwordDeath(NetherStuffs.NetherObsidianSwordDeathItemId, EnumToolMaterialObsidian).setItemName(
			"NetherObsidianSword").setIconCoord(1, 1);
}
