package bstramke.NetherStuffs.Items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Items.swords.NetherDiamondSword;
import bstramke.NetherStuffs.Items.swords.NetherDiamondSwordAcid;
import bstramke.NetherStuffs.Items.swords.NetherDiamondSwordDeath;
import bstramke.NetherStuffs.Items.swords.NetherObsidianSword;
import bstramke.NetherStuffs.Items.swords.NetherObsidianSwordAcid;
import bstramke.NetherStuffs.Items.swords.NetherObsidianSwordDeath;
import bstramke.NetherStuffs.Items.swords.NetherSoulglassSword;
import bstramke.NetherStuffs.Items.swords.NetherSoulglassSwordAcid;
import bstramke.NetherStuffs.Items.swords.NetherSoulglassSwordDeath;
import bstramke.NetherStuffs.Items.swords.NetherSoulglassSwordHellfire;

public class ItemRegistry {
	// private static EnumToolMaterial EnumToolMaterialDemonicIngot;
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
		// EnumToolMaterialDemonicIngot = EnumHelper.addToolMaterial("DemonicIngot", 2, 300, 6.0F, 6, 15);
		EnumToolMaterialObsidian = EnumHelper.addToolMaterial("Obsidian", 2, 350, 6.0F, 3, 15);
		EnumToolMaterialSoulGlass = EnumHelper.addToolMaterial("SoulGlass", 2, 40, 6.0F, 3, 15);
		EnumToolMaterialSoulGlassDemonic = EnumHelper.addToolMaterial("Demonic", 2, 300, 6.5F, 3, 15);
		EnumToolMaterialDiamond = EnumHelper.addToolMaterial("Diamond", 3, 1561, 8.0F, 3, 15);

		if (EnumToolMaterialObsidian != null) {
			NetherObsidianSword = new NetherObsidianSword(NetherStuffs.NetherObsidianSwordItemId, EnumToolMaterialObsidian);
			NetherObsidianSwordAcid = new NetherObsidianSwordAcid(NetherStuffs.NetherObsidianSwordAcidItemId, EnumToolMaterialObsidian);
			NetherObsidianSwordDeath = new NetherObsidianSwordDeath(NetherStuffs.NetherObsidianSwordDeathItemId, EnumToolMaterialObsidian);
		}

		if (EnumToolMaterialSoulGlass != null)
			NetherSoulglassSword = new NetherSoulglassSword(NetherStuffs.NetherSoulglassSwordItemId, EnumToolMaterialSoulGlass);

		if (EnumToolMaterialSoulGlassDemonic != null) {
			NetherSoulglassSwordHellfire = new NetherSoulglassSwordHellfire(NetherStuffs.NetherSoulglassSwordHellfireItemId, EnumToolMaterialSoulGlassDemonic);
			NetherSoulglassSwordAcid = new NetherSoulglassSwordAcid(NetherStuffs.NetherSoulglassSwordAcidItemId, EnumToolMaterialSoulGlassDemonic);
			NetherSoulglassSwordDeath = new NetherSoulglassSwordDeath(NetherStuffs.NetherSoulglassSwordDeathItemId, EnumToolMaterialSoulGlassDemonic);
		}

		if (EnumToolMaterialDiamond != null) {
			NetherDiamondSword = new NetherDiamondSword(NetherStuffs.NetherDiamondSwordItemId, EnumToolMaterialDiamond);
			NetherDiamondSwordAcid = new NetherDiamondSwordAcid(NetherStuffs.NetherDiamondSwordAcidItemId, EnumToolMaterialDiamond);
			NetherDiamondSwordDeath = new NetherDiamondSwordDeath(NetherStuffs.NetherDiamondSwordDeathItemId, EnumToolMaterialDiamond);
		}
	}

	public static final Item NetherPotionBottle = new PotionBottle(NetherStuffs.NetherPotionBottleItemId);
	public static final Item NetherStonePotionBowl = new PotionBowl(NetherStuffs.NetherStonePotionBowlItemId);
	public static final Item NetherWoodCharcoal = new NetherCharcoal(NetherStuffs.NetherWoodCharcoalItemId);

	public static final Item SoulEnergyBottle = new SoulEnergyBottle(NetherStuffs.SoulEnergyBottleItemId);

	public static final Item NetherOreIngot = new NetherOreIngot(NetherStuffs.NetherOreIngotItemId);

	public static final Item NetherDemonicBarHandle = new DemonicBarHandle(NetherStuffs.NetherDemonicBarHandleItemId);
	public static final Item NetherWoodStick = new NetherWoodStick(NetherStuffs.NetherWoodStickItemId);
	public static final Item NetherStoneBowl = new StoneBowl(NetherStuffs.NetherStoneBowlItemId);
	public static final Item NetherSoulGlassBottleItem = new SoulGlassBottle(NetherStuffs.NetherSoulGlassBottleItemId);

	public static final Item NetherBow = new TorchBow(NetherStuffs.NetherBowItemId);
	public static final Item torchArrow = new TorchArrow(NetherStuffs.TorchArrowItemId);
}
