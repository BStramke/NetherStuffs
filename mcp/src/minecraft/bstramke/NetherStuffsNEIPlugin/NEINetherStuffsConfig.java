package bstramke.NetherStuffsNEIPlugin;

import bstramke.NetherStuffs.DemonicFurnace.GuiDemonicFurnace;
import bstramke.NetherStuffs.SoulWorkBench.GuiSoulWorkBench;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEINetherStuffsConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new DemonicFurnaceRecipeHandler());
		API.registerUsageHandler(new DemonicFurnaceRecipeHandler());
		API.registerGuiOverlay(GuiDemonicFurnace.class, "netherdemonicsmelting", 5, 11);

		API.registerRecipeHandler(new SoulWorkbenchRecipeHandler());
		API.registerUsageHandler(new SoulWorkbenchRecipeHandler());
		API.registerGuiOverlay(GuiSoulWorkBench.class, "soulcrafting", 4, 4);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
