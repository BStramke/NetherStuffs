package bstramke.NetherStuffs.Blocks;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.Materials.NetherMaterials;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Plank extends BlockBase {
	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;
	private Icon icoPlankHellfire;
	private Icon icoPlankAcid;
	private Icon icoPlankDeath;

	public Plank(int par1) {
		super(par1, NetherMaterials.netherWood);
		setStepSound(soundWoodFootstep);
		setBurnProperties(this.blockID, 0, 0);
		setUnlocalizedName("NetherPlank");
		for (int i = 0; i < PlankItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherPlank." + PlankItemBlock.blockNames[i] + ".name", PlankItemBlock.blockDisplayNames[i]);
		}
	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, int metadata, ForgeDirection side) {
		if (side == UP) {
			return true;
		}
		return false;
	}

	public int getMetadataSize() {
		return PlankItemBlock.blockNames.length;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		icoPlankHellfire = par1IconRegister.registerIcon(CommonProxy.getIconLocation("PlankHellfire"));
		icoPlankAcid = par1IconRegister.registerIcon(CommonProxy.getIconLocation("PlankAcid"));
		icoPlankDeath = par1IconRegister.registerIcon(CommonProxy.getIconLocation("PlankDeath"));		 
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		switch (meta) {
		case hellfire:
			return icoPlankHellfire;
		case acid:
			return icoPlankAcid;
		case death:
			return icoPlankDeath;
		default:
			return icoPlankHellfire;
		}
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