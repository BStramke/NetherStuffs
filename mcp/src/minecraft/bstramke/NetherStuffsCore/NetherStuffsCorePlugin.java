package bstramke.NetherStuffsCore;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({ "NetherStuffsCore" })
public class NetherStuffsCorePlugin implements IFMLLoadingPlugin, IFMLCallHook {
	public static File myLocation;
	public static RelaunchClassLoader cl;

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		//System.out.println("getASMTransformerClass start");
		return new String[] {"bstramke.NetherStuffsCore.NetherStuffsAccessTransformer", "bstramke.NetherStuffsCore.NetherStuffsASM" };
	}

	@Override
	public String getModContainerClass() {
		//System.out.println("getModContainerClass start");
		return "bstramke.NetherStuffsCore.CoreModContainer";
	}

	@Override
	public String getSetupClass() {
		//System.out.println("getSetupClass start");
		return "bstramke.NetherStuffsCore.NetherStuffsCorePlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		cl = (RelaunchClassLoader) data.get("classLoader");
		if (data.containsKey("coremodLocation")) {
			//System.out.println("injectData start");
			myLocation = (File) data.get("coremodLocation");
			// System.out.println("Location: " + myLocation);
		}
	}

	private void addOverrides() {
		// if(ObfuscationReflectionHelper.obfuscation)
		{
			//System.out.println("addOverrides start");
			NetherStuffsASM.addClassOverride("akm", "net/minecraft/src/BlockBreakable.java");
			NetherStuffsASM.addClassOverride("amp", "net/minecraft/src/BlockPane.java");
			NetherStuffsASM.addClassOverride("zz", "net.minecraft.world.chunk.Chunk");
		}
		/*
		 * else { NetherStuffsASM.addClassOverride("net.minecraft.src.BlockPane", "Necessary for connecting GlassPanes and SoulglassPanes");
		 * NetherStuffsASM.addClassOverride("net.minecraft.src.BlockBreakable", "Necessary for Rendering SoulGlass and Glass Sides"); }
		 */
	}

	@Override
	public Void call() throws Exception {
		addOverrides();
		return null;
	}

}
