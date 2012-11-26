package NetherStuffs.NEI;

import java.util.ArrayList;
import java.util.TreeSet;

import NetherStuffs.Blocks.NetherBlocks;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEINetherStuffsConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new DemonicFurnaceRecipeHandler());
		API.registerUsageHandler(new DemonicFurnaceRecipeHandler());
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
