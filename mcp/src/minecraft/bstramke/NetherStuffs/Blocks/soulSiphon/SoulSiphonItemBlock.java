package bstramke.NetherStuffs.Blocks.soulSiphon;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulSiphonItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "SoulSiphonMK1", "SoulSiphonMK2", "SoulSiphonMK3", "SoulSiphonMK4" };
	public static String[] blockDisplayNames = new String[] { "Soul Siphon MK 1", "Soul Siphon MK 2", "Soul Siphon MK 3", "Soul Siphon MK 4" };

	public SoulSiphonItemBlock(int id) {
		super(id);
		setHasSubtypes(true);
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];

		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int nRange = 4;
		String addition = "";
		switch (par1ItemStack.getItemDamage()) {
		case SoulSiphon.mk1:
			nRange = 4;
			break;
		case SoulSiphon.mk2:
			nRange = 8;
			break;
		case SoulSiphon.mk3:
			nRange = 12;
			break;
		case SoulSiphon.mk4:
			nRange = 12;
			addition = "Bonus of 25% Collected energy";
			break;
		default:
			nRange = 4;
		}

		par3List.add("Siphon Radius: " + nRange);
		if (addition != "")
			par3List.add(addition);
	}
}
