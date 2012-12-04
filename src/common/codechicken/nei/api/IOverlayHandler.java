package codechicken.nei.api;

import java.util.List;

import codechicken.nei.PositionedStack;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.Slot;

public interface IOverlayHandler
{
    public void overlayRecipe(GuiContainer firstGui, List<PositionedStack> ingredients, boolean shift);
}
