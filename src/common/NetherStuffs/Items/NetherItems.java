package NetherStuffs.Items;

import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraftforge.common.EnumHelper;
import NetherStuffs.NetherStuffs;

public class NetherItems {
	static EnumToolMaterial EnumToolMaterialDemonicIngot = EnumHelper.addToolMaterial("DemonicIngot", 2, 400, 6.0F, 6, 15);
	static EnumToolMaterial EnumToolMaterialObsidian = EnumHelper.addToolMaterial("Obsidian", 2, 400, 6.0F, 3, 15);
	static EnumToolMaterial EnumToolMaterialSoulGlass = EnumHelper.addToolMaterial("SoulGlass", 2, 400, 6.0F, 3, 15);
	static EnumToolMaterial EnumToolMaterialSoulGlassDemonic = EnumHelper.addToolMaterial("Demonic", 2, 400, 6.0F, 3, 15);

	public static final Item NetherPotionBottle = new NetherPotionBottle(NetherStuffs.NetherPotionBottleItemId).setItemName("NetherPotionBottle").setIconCoord(0, 0);
	public static final Item NetherStonePotionBowl = new NetherStonePotionBowl(NetherStuffs.NetherStonePotionBowlItemId).setItemName("NetherStonePotionBowl").setIconCoord(0, 0);
	public static final Item NetherWoodCharcoal = new NetherWoodCharcoal(NetherStuffs.NetherWoodCharcoalItemId).setItemName("NetherWoodCharcoal").setIconCoord(6, 1);

	public static final Item SoulEnergyBottle = new SoulEnergyBottle(NetherStuffs.SoulEnergyBottleItemId).setItemName("SoulEnergyBottle").setIconCoord(0, 1);
	
	public static final Item NetherOreIngot = new NetherOreIngot(NetherStuffs.NetherOreIngotItemId).setItemName("NetherOreIngot").setIconCoord(0, 0);

	public static final Item NetherDemonicBarHandle = new NetherDemonicBarHandle(NetherStuffs.NetherDemonicBarHandleItemId).setItemName("NetherDemonicBarHandle").setIconCoord(4, 1);
	public static final Item NetherWoodStick = new NetherWoodStick(NetherStuffs.NetherWoodStickItemId).setItemName("NetherWoodStick").setIconCoord(2, 1);
	public static final Item NetherStoneBowl = new NetherStoneBowl(NetherStuffs.NetherStoneBowlItemId).setItemName("NetherStoneBowl").setIconCoord(3, 1);
	public static final Item NetherSoulGlassBottleItem = new NetherSoulGlassBottle(NetherStuffs.NetherSoulGlassBottleItemId).setItemName("NetherSoulGlassBottleItem").setIconCoord(0, 1);
	
	/**
	 * Swords
	 */
	public static final Item NetherObsidianSword = new NetherObsidianSword(NetherStuffs.NetherObsidianSwordItemId, EnumToolMaterialObsidian).setItemName("NetherObsidianSword")
			.setIconCoord(1, 1);
	public static final Item NetherObsidianSwordAcid = new NetherObsidianSwordAcid(NetherStuffs.NetherObsidianSwordAcidItemId, EnumToolMaterialObsidian)
			.setItemName("NetherObsidianSword").setIconCoord(1, 1);
	public static final Item NetherObsidianSwordDeath = new NetherObsidianSwordDeath(NetherStuffs.NetherObsidianSwordDeathItemId, EnumToolMaterialObsidian).setItemName(
			"NetherObsidianSword").setIconCoord(1, 1);

	public static final Item NetherSoulglassSword = new NetherSoulglassSword(NetherStuffs.NetherSoulglassSwordItemId, EnumToolMaterialSoulGlass).setItemName("NetherSoulglassSword")
			.setIconCoord(5, 1);
	public static final Item NetherSoulglassSwordHellfire = new NetherSoulglassSwordHellfire(NetherStuffs.NetherSoulglassSwordHellfireItemId, EnumToolMaterialSoulGlassDemonic)
			.setItemName("NetherSoulglassSword").setIconCoord(5, 1);
	public static final Item NetherSoulglassSwordAcid = new NetherSoulglassSwordAcid(NetherStuffs.NetherSoulglassSwordAcidItemId, EnumToolMaterialSoulGlassDemonic).setItemName(
			"NetherSoulglassSword").setIconCoord(5, 1);
	public static final Item NetherSoulglassSwordDeath = new NetherSoulglassSwordDeath(NetherStuffs.NetherSoulglassSwordDeathItemId, EnumToolMaterialSoulGlassDemonic).setItemName(
			"NetherSoulglassSword").setIconCoord(5, 1);
}
