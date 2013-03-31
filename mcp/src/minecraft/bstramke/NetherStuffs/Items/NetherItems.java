package bstramke.NetherStuffs.Items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
import bstramke.NetherStuffs.NetherStuffs;

public class NetherItems {
	private static EnumToolMaterial EnumToolMaterialDemonicIngot;
	private static EnumToolMaterial EnumToolMaterialObsidian;
	private static EnumToolMaterial EnumToolMaterialSoulGlass;
	private static EnumToolMaterial EnumToolMaterialSoulGlassDemonic;
	private static EnumToolMaterial EnumToolMaterialDiamond;
	
	/**
	 * Swords
	 */
	public static Item NetherObsidianSword;
	public static Item NetherObsidianSwordAcid;
	public static Item NetherObsidianSwordDeath;

	public static Item NetherSoulglassSword;
	public static Item NetherSoulglassSwordHellfire;
	public static Item NetherSoulglassSwordAcid;
	public static Item NetherSoulglassSwordDeath;

	public static Item NetherDiamondSword;
	public static Item NetherDiamondSwordAcid;
	public static Item NetherDiamondSwordDeath;

	
	public static final void init() {
		EnumToolMaterialDemonicIngot = EnumHelper.addToolMaterial("DemonicIngot", 2, 300, 6.0F, 6, 15);
		EnumToolMaterialObsidian = EnumHelper.addToolMaterial("Obsidian", 2, 350, 6.0F, 3, 15);
		EnumToolMaterialSoulGlass = EnumHelper.addToolMaterial("SoulGlass", 2, 40, 6.0F, 3, 15);
		EnumToolMaterialSoulGlassDemonic = EnumHelper.addToolMaterial("Demonic", 2, 300, 6.5F, 3, 15);
		EnumToolMaterialDiamond = EnumHelper.addToolMaterial("Diamond", 3, 1561, 8.0F, 3, 15);

		NetherObsidianSword = new NetherObsidianSword(NetherStuffs.NetherObsidianSwordItemId, EnumToolMaterialObsidian).setUnlocalizedName("NetherObsidianSword");

		NetherObsidianSwordAcid = new NetherObsidianSwordAcid(NetherStuffs.NetherObsidianSwordAcidItemId, EnumToolMaterialObsidian).setUnlocalizedName("NetherObsidianSword");

		NetherObsidianSwordDeath = new NetherObsidianSwordDeath(NetherStuffs.NetherObsidianSwordDeathItemId, EnumToolMaterialObsidian).setUnlocalizedName("NetherObsidianSword");

		NetherSoulglassSword = new NetherSoulglassSword(NetherStuffs.NetherSoulglassSwordItemId, EnumToolMaterialSoulGlass).setUnlocalizedName("NetherSoulglassSword");
		NetherSoulglassSwordHellfire = new NetherSoulglassSwordHellfire(NetherStuffs.NetherSoulglassSwordHellfireItemId, EnumToolMaterialSoulGlassDemonic)
				.setUnlocalizedName("NetherSoulglassSword");
		NetherSoulglassSwordAcid = new NetherSoulglassSwordAcid(NetherStuffs.NetherSoulglassSwordAcidItemId, EnumToolMaterialSoulGlassDemonic)
				.setUnlocalizedName("NetherSoulglassSword");
		NetherSoulglassSwordDeath = new NetherSoulglassSwordDeath(NetherStuffs.NetherSoulglassSwordDeathItemId, EnumToolMaterialSoulGlassDemonic)
				.setUnlocalizedName("NetherSoulglassSword");

		NetherDiamondSword = new NetherDiamondSword(NetherStuffs.NetherDiamondSwordItemId, EnumToolMaterialDiamond).setUnlocalizedName("NetherDiamondSword");
		NetherDiamondSwordAcid = new NetherDiamondSwordAcid(NetherStuffs.NetherDiamondSwordAcidItemId, EnumToolMaterialDiamond).setUnlocalizedName("NetherDiamondSword");
		NetherDiamondSwordDeath = new NetherDiamondSwordDeath(NetherStuffs.NetherDiamondSwordDeathItemId, EnumToolMaterialDiamond).setUnlocalizedName("NetherDiamondSword");
	}

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
	
	public static final Item SoulEnergyLiquidItem = new SoulEnergyLiquidItem(NetherStuffs.SoulEnergyLiquidItemId).setUnlocalizedName("SoulEnergyLiquidItem");
}
