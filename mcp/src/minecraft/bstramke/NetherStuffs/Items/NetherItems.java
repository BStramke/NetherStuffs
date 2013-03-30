package bstramke.NetherStuffs.Items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
import bstramke.NetherStuffs.NetherStuffs;

public class NetherItems {
	static EnumToolMaterial EnumToolMaterialDemonicIngot = EnumHelper.addToolMaterial("DemonicIngot", 2, 300, 6.0F, 6, 15);
	static EnumToolMaterial EnumToolMaterialObsidian = EnumHelper.addToolMaterial("Obsidian", 2, 350, 6.0F, 3, 15);
	static EnumToolMaterial EnumToolMaterialSoulGlass = EnumHelper.addToolMaterial("SoulGlass", 2, 40, 6.0F, 3, 15);
	static EnumToolMaterial EnumToolMaterialSoulGlassDemonic = EnumHelper.addToolMaterial("Demonic", 2, 300, 6.5F, 3, 15);
	static EnumToolMaterial EnumToolMaterialDiamond = EnumHelper.addToolMaterial("Diamond", 3, 1561, 8.0F, 3, 15);

	public static final Item NetherPotionBottle = new NetherPotionBottle(NetherStuffs.NetherPotionBottleItemId).setUnlocalizedName("NetherPotionBottle");
	public static final Item NetherStonePotionBowl = new NetherStonePotionBowl(NetherStuffs.NetherStonePotionBowlItemId).setUnlocalizedName("NetherStonePotionBowl");
	public static final Item NetherWoodCharcoal = new NetherWoodCharcoal(NetherStuffs.NetherWoodCharcoalItemId).setUnlocalizedName("NetherWoodCharcoal");

	public static final Item SoulEnergyBottle = new SoulEnergyBottle(NetherStuffs.SoulEnergyBottleItemId).setUnlocalizedName("SoulEnergyBottle");

	public static final Item NetherOreIngot = new NetherOreIngot(NetherStuffs.NetherOreIngotItemId).setUnlocalizedName("NetherOreIngot");	

	public static final Item NetherDemonicBarHandle = new NetherDemonicBarHandle(NetherStuffs.NetherDemonicBarHandleItemId).setUnlocalizedName("NetherDemonicBarHandle");
	public static final Item NetherWoodStick = new NetherWoodStick(NetherStuffs.NetherWoodStickItemId).setUnlocalizedName("NetherWoodStick");
	public static final Item NetherStoneBowl = new NetherStoneBowl(NetherStuffs.NetherStoneBowlItemId).setUnlocalizedName("NetherStoneBowl");
	public static final Item NetherSoulGlassBottleItem = new NetherSoulGlassBottle(NetherStuffs.NetherSoulGlassBottleItemId).setUnlocalizedName("NetherSoulGlassBottleItem");

	public static final Item NetherBow = new NetherBow(NetherStuffs.NetherBowItemId).setUnlocalizedName("NetherBow");
	public static final Item torchArrow = new NetherTorchArrow(NetherStuffs.TorchArrowItemId).setUnlocalizedName("torchArrow");
	/**
	 * Swords
	 */
	public static final Item NetherObsidianSword = new NetherObsidianSword(NetherStuffs.NetherObsidianSwordItemId, EnumToolMaterialObsidian)
			.setUnlocalizedName("NetherObsidianSword");
	public static final Item NetherObsidianSwordAcid = new NetherObsidianSwordAcid(NetherStuffs.NetherObsidianSwordAcidItemId, EnumToolMaterialObsidian)
			.setUnlocalizedName("NetherObsidianSword");
	public static final Item NetherObsidianSwordDeath = new NetherObsidianSwordDeath(NetherStuffs.NetherObsidianSwordDeathItemId, EnumToolMaterialObsidian)
			.setUnlocalizedName("NetherObsidianSword");

	public static final Item NetherSoulglassSword = new NetherSoulglassSword(NetherStuffs.NetherSoulglassSwordItemId, EnumToolMaterialSoulGlass)
			.setUnlocalizedName("NetherSoulglassSword");
	public static final Item NetherSoulglassSwordHellfire = new NetherSoulglassSwordHellfire(NetherStuffs.NetherSoulglassSwordHellfireItemId, EnumToolMaterialSoulGlassDemonic)
			.setUnlocalizedName("NetherSoulglassSword");
	public static final Item NetherSoulglassSwordAcid = new NetherSoulglassSwordAcid(NetherStuffs.NetherSoulglassSwordAcidItemId, EnumToolMaterialSoulGlassDemonic)
			.setUnlocalizedName("NetherSoulglassSword");
	public static final Item NetherSoulglassSwordDeath = new NetherSoulglassSwordDeath(NetherStuffs.NetherSoulglassSwordDeathItemId, EnumToolMaterialSoulGlassDemonic)
			.setUnlocalizedName("NetherSoulglassSword");

	public static final Item NetherDiamondSword = new NetherDiamondSword(NetherStuffs.NetherDiamondSwordItemId, EnumToolMaterialDiamond).setUnlocalizedName("NetherDiamondSword");
	public static final Item NetherDiamondSwordAcid = new NetherDiamondSwordAcid(NetherStuffs.NetherDiamondSwordAcidItemId, EnumToolMaterialDiamond)
			.setUnlocalizedName("NetherDiamondSword");
	public static final Item NetherDiamondSwordDeath = new NetherDiamondSwordDeath(NetherStuffs.NetherDiamondSwordDeathItemId, EnumToolMaterialDiamond)
			.setUnlocalizedName("NetherDiamondSword");

	public static final Item SoulEnergyLiquidItem = new SoulEnergyLiquidItem(NetherStuffs.SoulEnergyLiquidItemId).setUnlocalizedName("SoulEnergyLiquidItem");
}
