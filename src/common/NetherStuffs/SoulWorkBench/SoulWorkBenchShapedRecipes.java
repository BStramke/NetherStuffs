package NetherStuffs.SoulWorkBench;

import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.World;

public class SoulWorkBenchShapedRecipes implements IRecipe {

	/** How many horizontal slots this recipe is wide. */
	private int recipeWidth;

	/** How many vertical slots this recipe uses. */
	private int recipeHeight;

	/** Is a array of ItemStack that composes the recipe. */
	private ItemStack[] recipeItems;

	/** Is the ItemStack that you get when craft the recipe. */
	private ItemStack recipeOutput;

	private int recipeSoulEnergyRequired;

	public final int recipeOutputItemID;

	public SoulWorkBenchShapedRecipes(int par1, int par2,
			ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack,
			int nSoulEnergyAmount) {
		this.recipeOutputItemID = par4ItemStack.itemID;
		this.recipeWidth = par1;
		this.recipeHeight = par2;
		this.recipeItems = par3ArrayOfItemStack;
		this.recipeOutput = par4ItemStack;
		this.recipeSoulEnergyRequired = nSoulEnergyAmount;
	}

	@Override
	public boolean matches(InventoryCrafting par1InventoryCrafting,
			World par2World) {
		for (int var3 = 0; var3 <= 3 - this.recipeWidth; ++var3) {
			for (int var4 = 0; var4 <= 3 - this.recipeHeight; ++var4) {
				if (this.checkMatch(par1InventoryCrafting, var3, var4, true)) {
					return true;
				}

				if (this.checkMatch(par1InventoryCrafting, var3, var4, false)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkMatch(InventoryCrafting par1InventoryCrafting,
			int par2, int par3, boolean par4) {
		for (int var5 = 0; var5 < 3; ++var5) {
			for (int var6 = 0; var6 < 3; ++var6) {
				int var7 = var5 - par2;
				int var8 = var6 - par3;
				ItemStack var9 = null;

				if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth
						&& var8 < this.recipeHeight) {
					if (par4) {
						var9 = this.recipeItems[this.recipeWidth - var7 - 1
								+ var8 * this.recipeWidth];
					} else {
						var9 = this.recipeItems[var7 + var8 * this.recipeWidth];
					}
				}

				ItemStack var10 = par1InventoryCrafting.getStackInRowAndColumn(
						var5, var6);

				if (var10 != null || var9 != null) {
					if (var10 == null && var9 != null || var10 != null
							&& var9 == null) {
						return false;
					}

					if (var9.itemID != var10.itemID) {
						return false;
					}

					if (var9.getItemDamage() != -1
							&& var9.getItemDamage() != var10.getItemDamage()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
		return this.getRecipeOutput().copy();
	}

	public int getCraftingResultSoulEnergy() {
		return this.recipeSoulEnergyRequired;
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return this.recipeWidth * this.recipeHeight;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}
}
