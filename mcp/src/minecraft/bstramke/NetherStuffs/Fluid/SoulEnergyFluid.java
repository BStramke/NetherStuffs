package bstramke.NetherStuffs.Fluid;

import java.text.NumberFormat;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulEnergyFluid extends Fluid {

	public SoulEnergyFluid() {
		super("SoulEnergy");
		FluidRegistry.registerFluid(this);
	}
}
