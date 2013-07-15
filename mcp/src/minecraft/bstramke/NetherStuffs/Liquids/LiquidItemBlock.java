package bstramke.NetherStuffs.Liquids;

import java.text.NumberFormat;
import java.util.List;

import bstramke.NetherStuffs.Blocks.BlockRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LiquidItemBlock extends ItemBlock {
	public static final String blockNames[] = { "SoulEnergy", "DemonicLiquid" };
	public static String[] blockDisplayNames = new String[] { "Soul Energy", "Demonic Liquid" };

	public LiquidItemBlock(int par1) {
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
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
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return BlockRegistry.SoulEnergyFluidBlock.getIcon(1, par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		Integer nAmount = par1ItemStack.stackSize;

		if (par1ItemStack.getItemName().compareToIgnoreCase("tile.NetherStuffsLiquid.SoulEnergy") == 0) {
			if (par1ItemStack.getItemDamage() > 0)
				par3List.add("Soulenergy: " + NumberFormat.getInstance().format(nAmount) + " / " + NumberFormat.getInstance().format(par1ItemStack.getItemDamage()));
		}
	}
}
