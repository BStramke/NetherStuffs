package codechicken.nei.api;

import net.minecraft.src.Slot;
import codechicken.nei.forge.GuiContainerManager;

public interface IRecipeOverlayRenderer
{
	public void renderOverlay(GuiContainerManager gui, Slot slot);
}
