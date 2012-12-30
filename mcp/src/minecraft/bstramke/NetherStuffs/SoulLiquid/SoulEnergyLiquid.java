package bstramke.NetherStuffs.SoulLiquid;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.liquids.ILiquid;
import net.minecraftforge.liquids.LiquidStack;

public class SoulEnergyLiquid extends LiquidStack implements ILiquid {

	public SoulEnergyLiquid(int itemID, int amount) {
		super(itemID, amount);
	}

	@Override
	public int stillLiquidId() {
		return NetherStuffs.SoulEnergyLiquidItemId;
	}

	@Override
	public boolean isMetaSensitive() {
		return false;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}	
}
