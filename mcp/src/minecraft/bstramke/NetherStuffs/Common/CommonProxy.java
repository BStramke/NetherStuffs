package bstramke.NetherStuffs.Common;

public class CommonProxy {
	private static String GUIFOLDERPREFIX = "/mods/NetherStuffs/textures/gui/";
	public static String FURNANCE_PNG = GUIFOLDERPREFIX + "GuiDemonicFurnace.png";
	public static String SOULSIPHON_PNG = GUIFOLDERPREFIX + "GuiSoulSiphon.png";
	public static String SOULWORKBENCH_PNG = GUIFOLDERPREFIX + "GuiSoulWorkbench.png";
	public static String SOULDETECTOR_PNG = GUIFOLDERPREFIX + "GuiSoulDetector.png";
	public static String SOULFURNACE_PNG = GUIFOLDERPREFIX + "GuiSoulFurnace.png";
	public static String MOBBUTTONS_PNG = GUIFOLDERPREFIX + "mobbuttons.png";

	public static String getIconLocation(String TextureName) {
		return "NetherStuffs:" + TextureName;
	}
	
	public void registerRenderThings() {

	}
}
