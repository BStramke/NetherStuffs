package bstramke.NetherStuffs.Common;

public class CommonProxy {
	private static String GUIFOLDERPREFIX = "netherstuffs:textures/gui/";
	public static String GFXFOLDERPREFIX = "netherstuffs:textures/gfx/";
	public static String FURNANCE_PNG = GUIFOLDERPREFIX + "GuiDemonicFurnace.png";
	public static String SOULRIPPER_PNG = GUIFOLDERPREFIX + "GuiSoulRipper.png";
	public static String SOULWORKBENCH_PNG = GUIFOLDERPREFIX + "GuiSoulWorkbench.png";
	public static String MOBBUTTONS_PNG = GUIFOLDERPREFIX + "mobbuttons.png";

	public static String getIconLocation(String TextureName) {
		return "netherstuffs:" + TextureName;
	}
	
	public void registerRenderThings() {

	}
}
