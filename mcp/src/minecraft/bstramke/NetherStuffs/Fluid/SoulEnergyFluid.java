package bstramke.NetherStuffs.Fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class SoulEnergyFluid extends Fluid {

	public SoulEnergyFluid() {
		super("SoulEnergy");
		FluidRegistry.registerFluid(this);
	}

}
