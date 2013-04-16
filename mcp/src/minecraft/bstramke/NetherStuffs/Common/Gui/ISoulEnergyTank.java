package bstramke.NetherStuffs.Common.Gui;

public interface ISoulEnergyTank {
	public int getCurrentTankLevel();
	public int getMaxTankLevel();
	public int getFillingScaled(int MaxSize);
}
