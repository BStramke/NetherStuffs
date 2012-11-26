package NetherStuffs.NEI;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import NetherStuffs.NEI.DemonicFurnaceRecipeHandler.SmeltingPair;
import NetherStuffs.SoulFurnace.GuiSoulFurnace;
import net.minecraft.src.Block;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.ItemStack;
import codechicken.core.ReflectionManager;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;

public class SoulFurnaceRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Soul Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "/soulfurnace.png";
	}

	public class SmeltingPair extends CachedRecipe {
		public SmeltingPair(ItemStack ingred, ItemStack result) {
			ingred.stackSize = 1;
			this.ingred = new PositionedStack(ingred, 51, 6);
			this.result = new PositionedStack(result, 111, 24);
		}

		public PositionedStack getIngredient() {
			int cycle = cycleticks / 48;
			if (ingred.item.getItemDamage() == -1) {
				PositionedStack stack = ingred.copy();
				int maxDamage = 0;
				do {
					maxDamage++;
					stack.item.setItemDamage(maxDamage);
				} while (NEIClientUtils.isValidItem(stack.item));

				stack.item.setItemDamage(cycle % maxDamage);
				return stack;
			}
			return ingred;
		}

		public PositionedStack getResult() {
			return result;
		}

		PositionedStack ingred;
		PositionedStack result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("netherdemonicsmelting") && getClass() == SoulFurnaceRecipeHandler.class)// don't want subclasses getting a hold of this
		{
			HashMap<Integer, ItemStack> recipes;
			HashMap<List<Integer>, ItemStack> metarecipes = null;
			try {
				recipes = (HashMap<Integer, ItemStack>) ReflectionManager.getField(DemonicFurnaceRecipes.class, HashMap.class, DemonicFurnaceRecipes.smelting(), 1);
				try {
					metarecipes = ReflectionManager.getField(DemonicFurnaceRecipes.class, HashMap.class, DemonicFurnaceRecipes.smelting(), 3);
				} catch (ArrayIndexOutOfBoundsException e) {}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			for (Entry<Integer, ItemStack> recipe : recipes.entrySet()) {
				ItemStack item = recipe.getValue();
				arecipes.add(new SmeltingPair(new ItemStack(recipe.getKey(), 1, -1), item));
			}
			if (metarecipes == null)
				return;
			for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
				ItemStack item = recipe.getValue();
				arecipes.add(new SmeltingPair(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), item));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result.isItemEqual(new ItemStack(NetherStuffs.Items.NetherItems.NetherOreIngot, 1, 0)))
			arecipes.add(new SmeltingPair(new ItemStack(NetherStuffs.Blocks.NetherBlocks.netherOre, 1, NetherStuffs.Blocks.NetherBlocks.demonicOre), result));
		else if (result.isItemEqual(new ItemStack(NetherStuffs.Blocks.NetherBlocks.NetherSoulGlass)))
			arecipes.add(new SmeltingPair(new ItemStack(Block.slowSand, 1), result));
		else if (result.isItemEqual(new ItemStack(NetherStuffs.Items.NetherItems.NetherWoodCharcoal, 1)))
			arecipes.add(new SmeltingPair(new ItemStack(NetherStuffs.Blocks.NetherBlocks.netherWood, 1, NetherStuffs.Blocks.NetherBlocks.netherWoodHellfire), result));

	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("netherdemonicsmelting") && getClass() == SoulFurnaceRecipeHandler.class)// don't want subclasses getting a hold of this
		{
			loadCraftingRecipes("netherdemonicsmelting");
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ingredient.isItemEqual(new ItemStack(NetherStuffs.Blocks.NetherBlocks.netherOre, 1, NetherStuffs.Blocks.NetherBlocks.demonicOre)))
			arecipes.add(new SmeltingPair(ingredient, new ItemStack(NetherStuffs.Items.NetherItems.NetherOreIngot, 1, 0)));
		else if (ingredient.isItemEqual(new ItemStack(Block.slowSand, 1)))
			arecipes.add(new SmeltingPair(ingredient, new ItemStack(NetherStuffs.Blocks.NetherBlocks.NetherSoulGlass, 1)));
		else if (ingredient.isItemEqual(new ItemStack(NetherStuffs.Blocks.NetherBlocks.netherWood, 1, NetherStuffs.Blocks.NetherBlocks.netherWoodHellfire)))
			arecipes.add(new SmeltingPair(ingredient, new ItemStack(NetherStuffs.Items.NetherItems.NetherWoodCharcoal, 1)));
	}
	
	public void drawExtras(GuiContainerManager gui, int recipe) {
		//drawProgressBar(gui, 51, 25, 176, 0, 14, 14, 48, 7);
		drawProgressBar(gui, 79, 34, 176, 0, 24, 16, 48, 0);
	}
	
	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiSoulFurnace.class;
	}

}
