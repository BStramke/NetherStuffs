package bstramke.NetherStuffs.SoulLiquid;

import net.minecraftforge.liquids.ILiquid;
import net.minecraftforge.liquids.LiquidStack;

public class SoulEnergyLiquid extends LiquidStack implements ILiquid {

	public SoulEnergyLiquid(int itemID, int amount) {
		super(itemID, amount);
	}

	@Override
	public int stillLiquidId() {
		// TODO Auto-generated method stub
		return 0;
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
