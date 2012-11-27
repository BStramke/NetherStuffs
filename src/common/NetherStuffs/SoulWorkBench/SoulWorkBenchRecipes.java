package NetherStuffs.SoulWorkBench;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import NetherStuffs.NetherStuffs;
import NetherStuffs.Blocks.NetherBlocks;
import NetherStuffs.Blocks.SoulDetector;
import NetherStuffs.Items.NetherItems;

public class SoulWorkBenchRecipes {
	private static final SoulWorkBenchRecipes instance = new SoulWorkBenchRecipes();
	private List recipes = new ArrayList();
	public static int nSoulEnergyRequired = 0;

	SoulWorkBenchRecipes() {
		this.addRecipe(new ItemStack(Block.slowSand, 3), 20, new Object[] { "SSS", "SSS", "SSS", 'S', new ItemStack(Block.sand) });

		this.addRecipe(new ItemStack(NetherBlocks.netherSoulBomb, 1, 0), 10, new Object[] { "DID", "IDI", "DID", 'D', new ItemStack(Item.lightStoneDust, 1, 0), 'I',
				new ItemStack(NetherItems.NetherOreIngot, 1, 0) });

		this.addRecipe(new ItemStack(NetherBlocks.NetherSoulDetector, 1, SoulDetector.mk1), 25, new Object[] { "IBI", "BTB", "IBI", 'I',
				new ItemStack(NetherItems.NetherOreIngot, 1, 0), 'B', new ItemStack(Block.netherBrick, 1, 0), 'T', new ItemStack(Block.torchRedstoneActive, 1, 0) });

		this.addRecipe(new ItemStack(NetherBlocks.NetherSoulDetector, 1, SoulDetector.mk2), 50, new Object[] { "IGI", "GDG", "IGI", 'I',
				new ItemStack(NetherItems.NetherOreIngot, 1, 0), 'G', new ItemStack(Item.ingotIron, 1, 0), 'D', new ItemStack(NetherBlocks.NetherSoulDetector, 1, SoulDetector.mk1) });

		this.addRecipe(new ItemStack(NetherBlocks.NetherSoulDetector, 1, SoulDetector.mk3), 75, new Object[] { "IGI", "GDG", "IGI", 'I',
				new ItemStack(NetherItems.NetherOreIngot, 1, 0), 'G', new ItemStack(Item.ingotGold, 1, 0), 'D', new ItemStack(NetherBlocks.NetherSoulDetector, 1, SoulDetector.mk2) });

		this.addRecipe(new ItemStack(NetherBlocks.NetherSoulDetector, 1, SoulDetector.mk4), 100, new Object[] { "IBI", "BDB", "IBI", 'I',
				new ItemStack(NetherItems.NetherOreIngot, 1, 0), 'B', new ItemStack(Item.diamond, 1, 0), 'D', new ItemStack(NetherBlocks.NetherSoulDetector, 1, SoulDetector.mk3) });

		this.addRecipe(new ItemStack(NetherBlocks.NetherSoulFurnace, 1), 30, new Object[] { "IBI", "IFI", "IBI", 'I', new ItemStack(NetherItems.NetherOreIngot, 1, 0), 'F',
				new ItemStack(NetherBlocks.NetherDemonicFurnace, 1), 'B', new ItemStack(NetherItems.SoulEnergyBottle, 1) });
	}

	public List getRecipeList() {
		return this.recipes;
	}

	public static final SoulWorkBenchRecipes getInstance() {
		return instance;
	}

	public void addRecipe(ItemStack par1ItemStack, int nSoulEnergyAmount, Object... par2ArrayOfObj) {
		String var3 = "";
		int var4 = 0;
		int var5 = 0;
		int var6 = 0;
		int var9;

		if (par2ArrayOfObj[var4] instanceof String[]) {
			String[] var7 = (String[]) ((String[]) par2ArrayOfObj[var4++]);
			String[] var8 = var7;
			var9 = var7.length;

			for (int var10 = 0; var10 < var9; ++var10) {
				String var11 = var8[var10];
				++var6;
				var5 = var11.length();
				var3 = var3 + var11;
			}
		} else {
			while (par2ArrayOfObj[var4] instanceof String) {
				String var13 = (String) par2ArrayOfObj[var4++];
				++var6;
				var5 = var13.length();
				var3 = var3 + var13;
			}
		}

		HashMap var14;

		for (var14 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2) {
			Character var16 = (Character) par2ArrayOfObj[var4];
			ItemStack var17 = null;

			if (par2ArrayOfObj[var4 + 1] instanceof Item) {
				var17 = new ItemStack((Item) par2ArrayOfObj[var4 + 1]);
			} else if (par2ArrayOfObj[var4 + 1] instanceof Block) {
				var17 = new ItemStack((Block) par2ArrayOfObj[var4 + 1], 1, -1);
			} else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack) {
				var17 = (ItemStack) par2ArrayOfObj[var4 + 1];
			}

			var14.put(var16, var17);
		}

		ItemStack[] var15 = new ItemStack[var5 * var6];

		for (var9 = 0; var9 < var5 * var6; ++var9) {
			char var18 = var3.charAt(var9);

			if (var14.containsKey(Character.valueOf(var18))) {
				var15[var9] = ((ItemStack) var14.get(Character.valueOf(var18))).copy();
			} else {
				var15[var9] = null;
			}
		}

		this.recipes.add(new SoulWorkBenchShapedRecipes(var5, var6, var15, par1ItemStack, nSoulEnergyAmount));
	}

	public ItemStack getCraftingResult(TileSoulWorkBench soulworkbench) {
		InventoryCrafting InventoryCraftingSoulWorkBench = soulworkbench.getCraftingInventory();

		Iterator var11 = this.recipes.iterator();
		IRecipe var12;

		do {
			if (!var11.hasNext()) {
				// this.nSoulEnergyRequired = 0;
				return null;
			}

			var12 = (IRecipe) var11.next();
		} while (!var12.matches(InventoryCraftingSoulWorkBench, null));

		this.nSoulEnergyRequired = ((SoulWorkBenchShapedRecipes) var12).getCraftingResultSoulEnergy();

		return var12.getCraftingResult(InventoryCraftingSoulWorkBench);

	}

	public int getCraftingSoulEnergyRequired(ItemStack item) {
		Iterator it = this.recipes.iterator();
		while (it.hasNext()) {
			Object data = it.next();
			if (data instanceof SoulWorkBenchShapedRecipes) {
				if (((SoulWorkBenchShapedRecipes) data).getRecipeOutput() != null && ((SoulWorkBenchShapedRecipes) data).getRecipeOutput().isItemEqual(item)) {
					return ((SoulWorkBenchShapedRecipes) data).getRecipeSoulEnergyRequired();
				}
			}
		}

		return this.nSoulEnergyRequired;
	}
}
