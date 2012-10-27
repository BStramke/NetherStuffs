package NetherStuffs.Common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.ItemStack;

public class DemonicFurnaceRecipes {
	private static final DemonicFurnaceRecipes smeltingBase = new DemonicFurnaceRecipes();

	/** The list of smelting results. */
	private Map metaSmeltingList = new HashMap();
	private Map metaExperienceList = new HashMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final DemonicFurnaceRecipes smelting() {
		return smeltingBase;
	}

	//private DemonicFurnaceRecipes() {
		/*
		 * this.addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron), 0.7F); this.addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold), 1.0F);
		 * this.addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond), 1.0F); this.addSmelting(Block.sand.blockID, new ItemStack(Block.glass), 0.1F);
		 * this.addSmelting(Item.porkRaw.shiftedIndex, new ItemStack(Item.porkCooked), 0.35F); this.addSmelting(Item.beefRaw.shiftedIndex, new ItemStack(Item.beefCooked), 0.35F);
		 * this.addSmelting(Item.chickenRaw.shiftedIndex, new ItemStack(Item.chickenCooked), 0.35F); this.addSmelting(Item.fishRaw.shiftedIndex, new ItemStack(Item.fishCooked),
		 * 0.35F); this.addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone), 0.1F); this.addSmelting(Item.clay.shiftedIndex, new ItemStack(Item.brick), 0.3F);
		 * this.addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2), 0.2F); this.addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1), 0.15F);
		 * this.addSmelting(Block.oreEmerald.blockID, new ItemStack(Item.emerald), 1.0F); this.addSmelting(Item.field_82794_bL.shiftedIndex, new ItemStack(Item.field_82795_bM),
		 * 0.35F); this.addSmelting(Block.oreCoal.blockID, new ItemStack(Item.coal), 0.1F); this.addSmelting(Block.oreRedstone.blockID, new ItemStack(Item.redstone), 0.7F);
		 * this.addSmelting(Block.oreLapis.blockID, new ItemStack(Item.dyePowder, 1, 4), 0.2F);
		 */
	//}

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
	 */
	/*public void addSmelting(int itemID, int metadata, ItemStack itemstack) {
		addSmelting(itemID, metadata, itemstack, 0.0F);
	}*/

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
