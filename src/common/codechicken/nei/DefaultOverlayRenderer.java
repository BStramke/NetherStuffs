package codechicken.nei;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.Slot;

import org.lwjgl.opengl.GL11;

import codechicken.nei.forge.GuiContainerManager;

public class DefaultOverlayRenderer implements IRecipeOverlayRenderer
{
	static class struct_1
	{
		public struct_1(String s, IStackPositioner p)
		{
			positioner = p;
			ident = s;
		}
		
		IStackPositioner positioner;
		String ident;
	}
	
	static HashMap<Class<? extends GuiContainer>, struct_1> guiMap = new HashMap<Class<? extends GuiContainer>, struct_1>();
		
	public static void registerGuiOverlay(Class<? extends GuiContainer> classz, String ident, IStackPositioner positioner)
	{
		guiMap.put(classz, new struct_1(ident, positioner));
	}

	public static String getOverlayIdent(GuiContainer gui)
	{
		struct_1 s = guiMap.get(gui.getClass());
		if(s == null)return "";
		return s.ident;
	}
	
	public DefaultOverlayRenderer(ArrayList<PositionedStack> ai, GuiContainer container)
	{
		positioner = guiMap.get(container.getClass()).positioner;
		ingreds = positioner.positionStacks(ai);
	}
	
	@Override
	public void renderOverlay(GuiContainerManager gui, Slot slot)
	{
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 1);
		GL11.glColor4d(0.6, 0.6, 0.6, 0.7);
		
		gui.setColouredItemRender(true);
		for(PositionedStack stack : ingreds)
		{
			if(stack.relx == slot.xDisplayPosition && stack.rely == slot.yDisplayPosition)
				gui.drawItem(stack.relx, stack.rely, stack.item);
		}
		gui.setColouredItemRender(false);
		
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	IStackPositioner positioner;	
	ArrayList<PositionedStack> ingreds;
}
