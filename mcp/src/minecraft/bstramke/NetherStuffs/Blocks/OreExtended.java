package bstramke.NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.Client.Renderers.NetherOreExtendedRenderingHelper;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OreExtended extends BlockBase {
	public static final int netherOreCertusQuartz = 0;
	public static final int netherOreFerrous = 1;
	public static final int netherOreApatite = 2;
	public static final int netherOreUranium = 3;

	private Icon icoNetherOre;
	private Icon icoNetherStone;
	
	private Icon icoNetherOreOverlay[];
	
	public OreExtended(int par1) {
		super(par1, Material.rock);
		this.setStepSound(soundStoneFootstep);
		setUnlocalizedName("NetherOreExtended");
		
		for (int i = 0; i < OreExtendedItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherOreExtended." + OreExtendedItemBlock.blockNames[i] + ".name", OreExtendedItemBlock.blockDisplayNames[i]);
		}
	}
	
	public int getMetadataSize() {
		return OreExtendedItemBlock.blockNames.length;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icoNetherOreOverlay = new Icon[4];
		icoNetherOre = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre"));
		icoNetherOreOverlay[netherOreCertusQuartz] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_CertusQuartz"));
		icoNetherOreOverlay[netherOreFerrous] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Ferrous"));
		icoNetherOreOverlay[netherOreApatite] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Apatite"));
		icoNetherOreOverlay[netherOreUranium] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Uranium"));
		blockIcon = icoNetherOre;
	}

	public Icon getIconOreOverlay(int meta)
	{
		if(meta >=0 && meta < icoNetherOreOverlay.length)
			return icoNetherOreOverlay[meta];
		else
			return icoNetherOre;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return icoNetherOre;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return NetherOreExtendedRenderingHelper.instance.getRenderId();
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
