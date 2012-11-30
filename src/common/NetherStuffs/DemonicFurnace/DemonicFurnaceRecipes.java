package NetherStuffs.DemonicFurnace;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.ItemStack;

public class DemonicFurnaceRecipes {
	private static final DemonicFurnaceRecipes smeltingBase = new DemonicFurnaceRecipes();

	/** The list of smelting results. */
	public Map metaSmeltingList = new HashMap();
	private Map metaExperienceList = new HashMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final DemonicFurnaceRecipes smelting() {
		return smeltingBase;
	}

	/*
	 * public Map getSmeltingList() { return this.metaSmeltingList; }
	 */

	public float getExperience(ItemStack item) {
		if (item == null) {
			return 0.0F;
		}

		if (this.metaExperienceList.containsKey(Arrays.asList(item.itemID, item.getItemDamage())))
			return (Float) this.metaExperienceList.get(Arrays.asList(item.itemID, item.getItemDamage()));
		else
			return 0.0F;
	}

	/**
	 * Add a metadata-sensitive furnace recipe
	 * 
	 * @param itemID
	 *           The Item ID
	 * @param metadata
	 *           The Item Metadata
	 * @param itemstack
	 *           The ItemStack for the result
	 * @param experience
	 *           XP value
	 */
	public void addSmelting(int itemID, int metadata, ItemStack itemOutput, float experience) {
		this.metaSmeltingList.put(Arrays.asList(itemID, metadata), itemOutput);
		this.metaExperienceList.put(Arrays.asList(itemOutput.itemID, itemOutput.getItemDamage()), Float.valueOf(experience));
	}

	/**
	 * Used to get the resulting ItemStack form a source ItemStack
	 * 
	 * @param item
	 *           The Source ItemStack
	 * @return The result ItemStack
	 */
	public ItemStack getSmeltingResult(ItemStack item) {
		if (item == null) {
			return null;
		}
		ItemStack ret = (ItemStack) metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
		if (ret != null) {
			return ret;
		} else
			return null;
	}
}
