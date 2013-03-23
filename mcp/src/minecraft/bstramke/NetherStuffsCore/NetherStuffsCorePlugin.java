package bstramke.NetherStuffsCore;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
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
		// System.out.println("getASMTransformerClass start");
		return new String[] { "bstramke.NetherStuffsCore.NetherStuffsAccessTransformer", "bstramke.NetherStuffsCore.NetherStuffsASM" };
	}

	@Override
	public String getModContainerClass() {
		// System.out.println("getModContainerClass start");
		return "bstramke.NetherStuffsCore.CoreModContainer";
	}

	@Override
	public String getSetupClass() {
		// System.out.println("getSetupClass start");
		return "bstramke.NetherStuffsCore.NetherStuffsCorePlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		cl = (RelaunchClassLoader) data.get("classLoader");
		if (data.containsKey("coremodLocation")) {
			// System.out.println("injectData start");
			myLocation = (File) data.get("coremodLocation");
			// System.out.println("Location: " + myLocation);
		}
	}

	/*private void addOverrides() {		
		if (CoreModContainer.bOverrideChunk)
			NetherStuffsASM.addClassOverride("zz", "net.minecraft.world.chunk.Chunk");

	}*/

	@Override
	public Void call() throws Exception {
		//addOverrides();
		return null;
	}

}
