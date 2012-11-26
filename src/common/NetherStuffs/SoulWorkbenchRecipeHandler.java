package NetherStuffs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.ItemStack;
import codechicken.nei.IRecipeOverlayRenderer;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class SoulWorkbenchRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Soul Workbench Crafting";
	}

	@Override
	public String getGuiTexture() {
		// TODO Auto-generated method stub
		return null;
	}
}
