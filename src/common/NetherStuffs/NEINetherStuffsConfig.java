package NetherStuffs;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEINetherStuffsConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new SoulWorkbenchRecipeHandler());
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
