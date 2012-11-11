package NetherStuffsCore;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({ "NetherStuffsCore" })
public class NetherStuffsCorePlugin implements IFMLLoadingPlugin, IFMLCallHook {
	public static File myLocation;

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { "NetherStuffsCore.NetherStuffsAccessTransformer" };
	}

	@Override
	public String getModContainerClass() {
		return "NetherStuffsCore.CoreModContainer";
	}

	@Override
	public String getSetupClass() {
		return "NetherStuffsCore.NetherStuffsCorePlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if (data.containsKey("coremodLocation")) {
			myLocation = (File) data.get("coremodLocation");
			// System.out.println("Location: " + myLocation);
		}
	}

	private void addOverrides() {
		// if(ObfuscationReflectionHelper.obfuscation)
		{
			NetherStuffsAccessTransformer.addClassOverride("ale", "Necessary for connecting GlassPanes and SoulglassPanes");
			NetherStuffsAccessTransformer.addClassOverride("ajb", "Necessary for Rendering SoulGlass and Glass Sides");
		}
		/*
		 * else { NetherStuffsAccessTransformer.addClassOverride("net.minecraft.src.BlockPane", "Necessary for connecting GlassPanes and SoulglassPanes");
		 * NetherStuffsAccessTransformer.addClassOverride("net.minecraft.src.BlockBreakable", "Necessary for Rendering SoulGlass and Glass Sides"); }
		 */
	}

	@Override
	public Void call() throws Exception {
		addOverrides();
		return null;
	}

}
