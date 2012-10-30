package NetherStuffsCore;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class NetherStuffsCore implements IFMLLoadingPlugin {

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {"NetherStuffsCore.NetherStuffsAccessTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return "NetherStuffsCore.CoreModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

}
