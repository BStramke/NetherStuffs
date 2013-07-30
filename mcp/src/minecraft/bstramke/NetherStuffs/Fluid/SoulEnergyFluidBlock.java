package bstramke.NetherStuffs.Fluid;

import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulEnergyFluidBlock extends BlockFluidClassic {
	private Icon icoStill;
	private Icon icoFlowing;
	
	public SoulEnergyFluidBlock(int id, Fluid fluid, Material material) {
		super(id, fluid, material);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		if(side <= 1)
			return icoStill;
		else
			return icoFlowing;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icoStill = par1IconRegister.registerIcon(CommonProxy.getIconLocation("liquid_SoulEnergy"));
		icoFlowing = par1IconRegister.registerIcon(CommonProxy.getIconLocation("liquid_SoulEnergy_flow"));
	}
	
}
