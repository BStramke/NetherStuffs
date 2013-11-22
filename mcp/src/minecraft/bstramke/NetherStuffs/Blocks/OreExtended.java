package bstramke.NetherStuffs.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.OreConfig;
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

	public Icon getIconOreOverlay(int meta) {
		if (meta >= 0 && meta < icoNetherOreOverlay.length)
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

	private int quantityDropped(int meta, Random random) {
		OreConfig oc = NetherStuffs.OreConfiguration.get(OreExtendedItemBlock.blockNames[meta]);
		return MathHelper.getRandomIntegerInRange(random, oc.DropFragmentCountMin, oc.DropFragmentCountMax);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		OreConfig oc = NetherStuffs.OreConfiguration.get(OreExtendedItemBlock.blockNames[meta]);
		
		if(oc.DoOreDropFragments)
			return quantityDropped(meta, random) + random.nextInt(1 + fortune);
		else
			return 1;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		OreConfig oc = NetherStuffs.OreConfiguration.get(OreExtendedItemBlock.blockNames[par1World.getBlockMetadata(par2, par3, par4)]);
		if (oc.DoOreDropFragments)
			this.dropXpOnBlockBreak(par1World, par2, par3, par4, MathHelper.getRandomIntegerInRange(par1World.rand, oc.HarvestXPMin, oc.HarvestXPMax));
	}

	@Override
	public int damageDropped(int meta) {
		OreConfig oc = NetherStuffs.OreConfiguration.get(OreExtendedItemBlock.blockNames[meta]);
		if (oc.DoOreDropFragments)
			return oc.FragmentMeta;
		else
			return meta;
	}

	@Override
	public int idDropped(int meta, Random par2Random, int par3) {
		OreConfig oc = NetherStuffs.OreConfiguration.get(OreExtendedItemBlock.blockNames[meta]);
		if (oc.DoOreDropFragments)
			return oc.FragmentId;
		else
			return super.idDropped(meta, par2Random, par3);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
