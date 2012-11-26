package NetherStuffs.NEI;

import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

import net.minecraft.src.Block;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiFurnace;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import NetherStuffs.Blocks.NetherBlocks;
import NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import NetherStuffs.DemonicFurnace.GuiDemonicFurnace;
import NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import NetherStuffs.Items.NetherItems;
import codechicken.core.ReflectionManager;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;

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
		transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "netherdemonicfuel"));
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
		if (ingredient.isItemEqual(new ItemStack(NetherStuffs.Blocks.NetherBlocks.netherOre, 1, NetherStuffs.Blocks.NetherBlocks.demonicOre)))
			arecipes.add(new SmeltingPair(ingredient, new ItemStack(NetherStuffs.Items.NetherItems.NetherOreIngot, 1, 0)));
		else if (ingredient.isItemEqual(new ItemStack(Block.slowSand, 1)))
			arecipes.add(new SmeltingPair(ingredient, new ItemStack(NetherStuffs.Blocks.NetherBlocks.NetherSoulGlass, 1)));
		else if (ingredient.isItemEqual(new ItemStack(NetherStuffs.Blocks.NetherBlocks.netherWood, 1, NetherStuffs.Blocks.NetherBlocks.netherWoodHellfire)))
			arecipes.add(new SmeltingPair(ingredient, new ItemStack(NetherStuffs.Items.NetherItems.NetherWoodCharcoal, 1)));
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

	@Override
	public String getOverlayIdentifier() {
		return "netherdemonicsmelting";
	}

	static {
		removeFuels();
		findFuels();
	}
}
