package NetherStuffs.NEI;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IRecipe;
import net.minecraft.src.ItemStack;
import NetherStuffs.SoulWorkBench.GuiSoulWorkBench;
import NetherStuffs.SoulWorkBench.SoulWorkBenchRecipes;
import NetherStuffs.SoulWorkBench.SoulWorkBenchShapedRecipes;
import codechicken.core.ReflectionManager;
import codechicken.nei.DefaultOverlayRenderer;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class SoulWorkbenchRecipeHandler extends TemplateRecipeHandler {

	public class CachedShapedRecipe extends CachedRecipe {
		public CachedShapedRecipe(SoulWorkBenchShapedRecipes recipe) {
			result = new PositionedStack(recipe.getRecipeOutput(), 136, 31);
			ingredients = new ArrayList<PositionedStack>();
			setIngredients(recipe);
		}

		public CachedShapedRecipe(int width, int height, Object[] items, ItemStack out) {
			result = new PositionedStack(out, 136, 31);
			ingredients = new ArrayList<PositionedStack>();
			setIngredients(width, height, items);
		}

		/**
		 * @param width
		 * @param height
		 * @param items
		 *           an ItemStack[] or ItemStack[][]
		 */
		public void setIngredients(int width, int height, Object[] items) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (items[y * width + x] == null) {
						continue;
					}
					PositionedStack stack = new PositionedStack(items[y * width + x], 42 + x * 18, 13 + y * 18);
					stack.setMaxSize(1);
					ingredients.add(stack);
				}
			}
		}

		public void setIngredients(SoulWorkBenchShapedRecipes recipe) {
			int width;
			int height;
			ItemStack[] items;
			try {
				width = ReflectionManager.getField(SoulWorkBenchShapedRecipes.class, Integer.class, recipe, 0);
				height = ReflectionManager.getField(SoulWorkBenchShapedRecipes.class, Integer.class, recipe, 1);
				items = ReflectionManager.getField(SoulWorkBenchShapedRecipes.class, ItemStack[].class, recipe, 2);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			setIngredients(width, height, items);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		public PositionedStack getResult() {
			return result;
		}

		public ArrayList<PositionedStack> ingredients;
		public PositionedStack result;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(102, 30, 24, 18), "soulcrafting"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("soulcrafting") && getClass() == SoulWorkbenchRecipeHandler.class) {
			@SuppressWarnings("unchecked")
			List<IRecipe> allrecipes = SoulWorkBenchRecipes.getInstance().getRecipeList();
			for (IRecipe irecipe : allrecipes) {
				CachedShapedRecipe recipe = null;
				if (irecipe instanceof SoulWorkBenchShapedRecipes) {
					recipe = new CachedShapedRecipe((SoulWorkBenchShapedRecipes) irecipe);
				}

				if (recipe == null)
					continue;

				arecipes.add(recipe);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		@SuppressWarnings("unchecked")
		List<IRecipe> allrecipes = SoulWorkBenchRecipes.getInstance().getRecipeList();
		for (IRecipe irecipe : allrecipes) {
			if (NEIClientUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
				CachedShapedRecipe recipe = null;
				if (irecipe instanceof SoulWorkBenchShapedRecipes) {
					recipe = new CachedShapedRecipe((SoulWorkBenchShapedRecipes) irecipe);
				}

				if (recipe == null)
					continue;

				arecipes.add(recipe);
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		@SuppressWarnings("unchecked")
		List<IRecipe> allrecipes = SoulWorkBenchRecipes.getInstance().getRecipeList();
		for (IRecipe irecipe : allrecipes) {
			CachedShapedRecipe recipe = null;
			if (irecipe instanceof SoulWorkBenchShapedRecipes) {
				recipe = new CachedShapedRecipe((SoulWorkBenchShapedRecipes) irecipe);
			}

			if (recipe == null)
				continue;

			if (recipe.contains(recipe.ingredients, ingredient)) {
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}
	}

	@Override
	public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
		return DefaultOverlayRenderer.getOverlayIdent(gui).equals("soulcrafting") || (isRecipe2x2(recipe) && DefaultOverlayRenderer.getOverlayIdent(gui).equals("crafting2x2"));
	}

	public boolean isRecipe2x2(int recipe) {
		for (PositionedStack stack : getIngredientStacks(recipe)) {
			if (stack.relx > 61 || stack.rely > 50)
				return false;
		}
		return true;
	}

	@Override
	public String getRecipeName() {
		return "Soul Workbench";
	}

	@Override
	public String getGuiTexture() {
		return "/soulworkbench.png";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiSoulWorkBench.class;
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public void drawExtras(GuiContainerManager gui, int recipe) {
		int nSoulEnergyRequired = SoulWorkBenchRecipes.getInstance().getCraftingSoulEnergyRequired(getResultStack(recipe).item);
		gui.drawText(39, 72, "+ " + nSoulEnergyRequired + " Energy", 0x000000, false);
	}

	@Override
	public void drawBackground(GuiContainerManager gui, int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.bindTextureByName(getGuiTexture());
		gui.drawTexturedModalRect(0, 0, 4, 4, 166, 65);
	}
}
