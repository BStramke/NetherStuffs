package bstramke.NetherStuffs.Common;

public class CommonProxy {
	private static String GUIFOLDERPREFIX = "netherstuffs:textures/gui/";
	public static String GFXFOLDERPREFIX = "netherstuffs:textures/gfx/";
	public static String FURNANCE_PNG = GUIFOLDERPREFIX + "GuiDemonicFurnace.png";
	public static String SOULSIPHON_PNG = GUIFOLDERPREFIX + "GuiSoulSiphon.png";
	public static String SOULWORKBENCH_PNG = GUIFOLDERPREFIX + "GuiSoulWorkbench.png";
	public static String SOULDETECTOR_PNG = GUIFOLDERPREFIX + "GuiSoulDetector.png";
	public static String SOULFURNACE_PNG = GUIFOLDERPREFIX + "GuiSoulFurnace.png";
	public static String SOULENERGYENGINE_PNG = GUIFOLDERPREFIX + "GuiSoulEnergyEngine.png";
	public static String SOULCONDENSER_PNG = GUIFOLDERPREFIX + "GuiSoulCondenser.png";
	public static String SOULSMELTER_PNG = GUIFOLDERPREFIX + "GuiSoulSmelter.png";
	public static String MOBBUTTONS_PNG = GUIFOLDERPREFIX + "mobbuttons.png";

	public static String getIconLocation(String TextureName) {
		return "netherstuffs:" + TextureName;
	}
	
	public void registerRenderThings() {

	}
}
