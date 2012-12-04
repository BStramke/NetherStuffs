package NetherStuffs.NEI;

import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.Map.Entry;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import NetherStuffs.Blocks.NetherBlocks;
import NetherStuffs.Blocks.NetherOre;
import NetherStuffs.Blocks.NetherOreItemBlock;
import NetherStuffs.Blocks.NetherWood;
import NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import NetherStuffs.DemonicFurnace.GuiDemonicFurnace;
import NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import NetherStuffs.Items.NetherItems;
import codechicken.core.ReflectionManager;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.FurnaceRecipeHandler.SmeltingPair;

public class DemonicFurnaceRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Demonic Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "/furnace.png";
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

		public PositionedStack getOtherStack() {
			return afuels.get((cycleticks / 48) % afuels.size()).stack;
		}

		PositionedStack ingred;
		PositionedStack result;
	}

	public static class FuelPair {
		public FuelPair(ItemStack ingred, int burnTime) {
			this.stack = new PositionedStack(ingred, 51, 42);
			this.burnTime = burnTime;
		}

		public PositionedStack stack;
		public int burnTime;
	}

	public static ArrayList<FuelPair> afuels;
	public static TreeSet<Integer> efuels;

	@Override
	public void loadTransferRects() {
		// transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "netherdemonicfuel"));
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "netherdemonicsmelting"));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiDemonicFurnace.class;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("netherdemonicsmelting") && getClass() == DemonicFurnaceRecipeHandler.class)// don't want subclasses getting a hold of this
		{
			HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) DemonicFurnaceRecipes.smelting().metaSmeltingList;
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
		HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) DemonicFurnaceRecipes.smelting().metaSmeltingList;
		for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
			ItemStack item = recipe.getValue();
			if (NEIClientUtils.areStacksSameType(item, result)) {
				arecipes.add(new SmeltingPair(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), item));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("netherdemonicfuel") && getClass() == DemonicFurnaceRecipeHandler.class)// don't want subclasses getting a hold of this
		{
			loadCraftingRecipes("netherdemonicsmelting");
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (DemonicFurnaceRecipes.smelting().getSmeltingResult(ingredient) != null)
			arecipes.add(new SmeltingPair(ingredient, DemonicFurnaceRecipes.smelting().getSmeltingResult(ingredient)));
	}

	public void drawExtras(GuiContainerManager gui, int recipe) {
		drawProgressBar(gui, 51, 25, 176, 0, 14, 14, 48, 7);
		drawProgressBar(gui, 74, 23, 176, 14, 24, 16, 48, 0);
	}

	private static void removeFuels() {
		efuels = new TreeSet<Integer>();
	}

	private static void findFuels() {
		Method getBurnTime;
		try {
			getBurnTime = TileDemonicFurnace.class.getDeclaredMethod("getItemBurnTime", ItemStack.class);
			getBurnTime.setAccessible(true);
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		} catch (NoSuchMethodException e) {
			try {
				getBurnTime = TileDemonicFurnace.class.getDeclaredMethod("a", ItemStack.class);
				getBurnTime.setAccessible(true);
			} catch (SecurityException e1) {
				e1.printStackTrace();
				return;
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
				return;
			}
		}

		TileDemonicFurnace afurnace = new TileDemonicFurnace();
		afuels = new ArrayList<FuelPair>();

		try {
			afuels.add(new FuelPair(new ItemStack(NetherItems.NetherWoodCharcoal), (Integer) getBurnTime.invoke(afurnace, new ItemStack(NetherItems.NetherWoodCharcoal))));
			afuels.add(new FuelPair(new ItemStack(NetherBlocks.netherWood, 1, 0), (Integer) getBurnTime.invoke(afurnace, new ItemStack(NetherBlocks.netherWood, 1, 0))));
			afuels.add(new FuelPair(new ItemStack(NetherBlocks.netherWood, 1, 1), (Integer) getBurnTime.invoke(afurnace, new ItemStack(NetherBlocks.netherWood, 1, 1))));
			afuels.add(new FuelPair(new ItemStack(NetherBlocks.netherWood, 1, 2), (Integer) getBurnTime.invoke(afurnace, new ItemStack(NetherBlocks.netherWood, 1, 2))));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	static {
		removeFuels();
		findFuels();
	}
	
	@Override
	public String getOverlayIdentifier()
	{
		return "netherdemonicsmelting";
	}
}
