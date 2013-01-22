package thaumcraft.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

import org.w3c.dom.Document;

import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.IInfusionRecipe;
import thaumcraft.api.crafting.RecipeCrucible;
import thaumcraft.api.crafting.ShapedArcaneCraftingRecipes;
import thaumcraft.api.crafting.ShapedInfusionCraftingRecipes;
import thaumcraft.api.crafting.ShapelessArcaneCraftingRecipes;
import thaumcraft.api.crafting.ShapelessInfusionCraftingRecipes;
import thaumcraft.api.research.ResearchList;
import cpw.mods.fml.common.FMLLog;


public class ThaumcraftApi {
	
	//Materials	
	public static EnumToolMaterial toolMatThaumium = EnumHelper.addToolMaterial("THAUMIUM", 3, 400, 7F, 2, 22);
	public static EnumToolMaterial toolMatElemental = EnumHelper.addToolMaterial("THAUMIUM_ELEMENTAL", 3, 1500, 10F, 3, 18);
	public static EnumArmorMaterial armorMatThaumium = EnumHelper.addArmorMaterial("THAUMIUM", 25, new int[] { 2, 6, 5, 2 }, 25);
	public static EnumArmorMaterial armorMatSpecial = EnumHelper.addArmorMaterial("SPECIAL", 25, new int[] { 1, 3, 2, 1 }, 25);
	
	
	//Miscellaneous
	/**
	 * Portable Hole Block-id Blacklist. 
	 * Simply add the block-id's of blocks you don't want the portable hole to go through.
	 */
	public static ArrayList<Integer> portableHoleBlackList = new ArrayList<Integer>();
	
	
	//RESEARCH/////////////////////////////////////////
	public static Document researchDoc = null;
	public static ArrayList<String> apiResearchFiles = new ArrayList<String>(); 
	
	/**
	 * Used to add your own xml files that the research system will check of recipes and descriptions of custom research
	 * @param resourceLoc The xml file. For example The default file used by TC is 
	 * "/thaumcraft/resources/research.xml"
	 */
	public static void registerResearchXML(String resourceLoc) {
		if (!apiResearchFiles.contains(resourceLoc)) apiResearchFiles.add(resourceLoc);
	}
	
	//RECIPES/////////////////////////////////////////
	private static ArrayList crucibleRecipes = new ArrayList();
	private static List craftingRecipes = new ArrayList();	
	private static HashMap<List,ItemStack> smeltingBonus = new HashMap<List,ItemStack>();
	private static ArrayList<List> smeltingBonusExlusion = new ArrayList<List>();
	
	/**
	 * This method is used to determine what bonus items are generated when the infernal furnace smelts items
	 * @param in The result (not input) of the smelting operation. e.g. new ItemStack(ingotGold)
	 * @param out The bonus item that can be produced from the smelting operation e.g. new ItemStack(nuggetGold,0,0).
	 * Stacksize should be 0 unless you want to guarantee that at least 1 item is always produced.
	 */
	public static void addSmeltingBonus(ItemStack in, ItemStack out) {
		smeltingBonus.put(
				Arrays.asList(in.itemID,in.getItemDamage()), 
				new ItemStack(out.itemID,0,out.getItemDamage()));
	}
	
	/**
	 * Returns the bonus item produced from a smelting operation in the infernal furnace
	 * @param in The result of the smelting operation. e.g. new ItemStack(ingotGold)
	 * @return the The bonus item that can be produced
	 */
	public static ItemStack getSmeltingBonus(ItemStack in) {
		return smeltingBonus.get(Arrays.asList(in.itemID,in.getItemDamage()));
	}
	
	/**
	 * Excludes specific items from producing bonus items when they are smelted in the infernal furnace, even 
	 * if their smelt result would normally produce a bonus item.
	 * @param in The item to be smelted that should never produce a bonus item (e.g. the various macerated dusts form IC2)
	 * Even though they produce gold, iron, etc. ingots, they should NOT produce bonus nuggets as well.
	 */
	public static void addSmeltingBonusExclusion(ItemStack in) {
		smeltingBonusExlusion.add(Arrays.asList(in.itemID,in.getItemDamage()));
	}
	
	/**
	 * Sees if an item is allowed to produce bonus items when smelted in the infernal furnace
	 * @param in The item you wish to check
	 * @return true or false
	 */
	public static boolean isSmeltingBonusExluded(ItemStack in) {
		return smeltingBonusExlusion.contains(Arrays.asList(in.itemID,in.getItemDamage()));
	}
	
	public static List getCraftingRecipes() {
		return craftingRecipes;
	}
	
	/**
	 * @param key the research key required for this recipe to work. Leave blank if it will work without research
	 * @param cost the vis cost
	 * @param par1ItemStack the recipe output
	 * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla recipes
	 */
	public static void addArcaneCraftingRecipe(String key, int cost, ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var9;

        if (par2ArrayOfObj[var4] instanceof String[])
        {
            String[] var7 = (String[])((String[])par2ArrayOfObj[var4++]);
            String[] var8 = var7;
            var9 = var7.length;

            for (int var10 = 0; var10 < var9; ++var10)
            {
                String var11 = var8[var10];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }
        else
        {
            while (par2ArrayOfObj[var4] instanceof String)
            {
                String var13 = (String)par2ArrayOfObj[var4++];
                ++var6;
                var5 = var13.length();
                var3 = var3 + var13;
            }
        }

        HashMap var14;

        for (var14 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
        {
            Character var16 = (Character)par2ArrayOfObj[var4];
            ItemStack var17 = null;

            if (par2ArrayOfObj[var4 + 1] instanceof Item)
            {
                var17 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof Block)
            {
                var17 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, -1);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack)
            {
                var17 = (ItemStack)par2ArrayOfObj[var4 + 1];
            }

            var14.put(var16, var17);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (var9 = 0; var9 < var5 * var6; ++var9)
        {
            char var18 = var3.charAt(var9);

            if (var14.containsKey(Character.valueOf(var18)))
            {
                var15[var9] = ((ItemStack)var14.get(Character.valueOf(var18))).copy();
            }
            else
            {
                var15[var9] = null;
            }
        }

        craftingRecipes.add(new ShapedArcaneCraftingRecipes(key, var5, var6, var15, par1ItemStack,cost));
    }

	/**
	 * @param key the research key required for this recipe to work. Leave blank if it will work without research
	 * @param recipeKey a string value of the key used in your research.xml for this recipe to display in the thaumonomicon
	 * @param cost the vis cost
	 * @param par1ItemStack the recipe output
	 * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla recipes
	 */
	public static void addArcaneCraftingRecipe(String key, String recipeKey, int cost, ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
		addArcaneCraftingRecipe(key,cost,par1ItemStack,par2ArrayOfObj);
		ResearchList.craftingRecipesForResearch.put(recipeKey, Arrays.asList(getCraftingRecipes().size()-1));
	}
	
    /**
     * @param key the research key required for this recipe to work. Leave blank if it will work without research
     * @param cost the vis cost
     * @param par1ItemStack the recipe output
     * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla shapeless recipes
     */
    public static void addShapelessArcaneCraftingRecipe(String key, int cost, ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        ArrayList var3 = new ArrayList();
        Object[] var4 = par2ArrayOfObj;
        int var5 = par2ArrayOfObj.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack)
            {
                var3.add(((ItemStack)var7).copy());
            }
            else if (var7 instanceof Item)
            {
                var3.add(new ItemStack((Item)var7));
            }
            else
            {
                if (!(var7 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipe!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        craftingRecipes.add(new ShapelessArcaneCraftingRecipes(key, par1ItemStack, var3, cost));
    }
    
    /**
	 * @param key the research key required for this recipe to work. Leave blank if it will work without research
	 * @param recipeKey a string value of the key used in your research.xml for this recipe to display in the thaumonomicon
	 * @param cost the vis cost
	 * @param par1ItemStack the recipe output
	 * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla shapeless recipes
	 */
	public static void addShapelessArcaneCraftingRecipe(String key, String recipeKey, int cost, ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
		addShapelessArcaneCraftingRecipe(key,cost,par1ItemStack,par2ArrayOfObj);
		ResearchList.craftingRecipesForResearch.put(recipeKey, Arrays.asList(getCraftingRecipes().size()-1));
	}
    
    /**
     * @param key the research key required for this recipe to work. Leave blank if it will work without research
     * @param cost the vis cost
     * @param tags ObjectTags list of required aspects and their amounts. No more than 5 aspects should be used in a recipe.
     * @param par1ItemStack the recipe output
     * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla recipes
     */
    public static void addInfusionCraftingRecipe(String key, int cost, ObjectTags tags, ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var9;

        if (par2ArrayOfObj[var4] instanceof String[])
        {
            String[] var7 = (String[])((String[])par2ArrayOfObj[var4++]);
            String[] var8 = var7;
            var9 = var7.length;

            for (int var10 = 0; var10 < var9; ++var10)
            {
                String var11 = var8[var10];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }
        else
        {
            while (par2ArrayOfObj[var4] instanceof String)
            {
                String var13 = (String)par2ArrayOfObj[var4++];
                ++var6;
                var5 = var13.length();
                var3 = var3 + var13;
            }
        }

        HashMap var14;

        for (var14 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
        {
            Character var16 = (Character)par2ArrayOfObj[var4];
            ItemStack var17 = null;

            if (par2ArrayOfObj[var4 + 1] instanceof Item)
            {
                var17 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof Block)
            {
                var17 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, -1);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack)
            {
                var17 = (ItemStack)par2ArrayOfObj[var4 + 1];
            }

            var14.put(var16, var17);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (var9 = 0; var9 < var5 * var6; ++var9)
        {
            char var18 = var3.charAt(var9);

            if (var14.containsKey(Character.valueOf(var18)))
            {
                var15[var9] = ((ItemStack)var14.get(Character.valueOf(var18))).copy();
            }
            else
            {
                var15[var9] = null;
            }
        }

        craftingRecipes.add(new ShapedInfusionCraftingRecipes(key, var5, var6, var15, par1ItemStack,cost,tags));
    }

    /**
     * @param key the research key required for this recipe to work. Leave blank if it will work without research
     * @param recipeKey a string value of the key used in your research.xml for this recipe to display in the thaumonomicon
     * @param cost the vis cost
     * @param tags ObjectTags list of required aspects and their amounts. No more than 5 aspects should be used in a recipe.
     * @param par1ItemStack the recipe output
     * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla recipes
     */
    public static void addInfusionCraftingRecipe(String key, String recipeKey, int cost, ObjectTags tags, ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
    	addInfusionCraftingRecipe(key, cost, tags, par1ItemStack, par2ArrayOfObj);
    	ResearchList.craftingRecipesForResearch.put(recipeKey, Arrays.asList(getCraftingRecipes().size()-1));
    }
    
    /**
     * @param key the research key required for this recipe to work. Leave blank if it will work without research
     * @param cost the vis cost
     * @param tags ObjectTags list of required aspects and their amounts. No more than 5 aspects should be used in a recipe.
     * @param par1ItemStack the recipe output
     * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla shapeless recipes
     */
    public static void addShapelessInfusionCraftingRecipe(String key, int cost, ObjectTags tags, ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        ArrayList var3 = new ArrayList();
        Object[] var4 = par2ArrayOfObj;
        int var5 = par2ArrayOfObj.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack)
            {
                var3.add(((ItemStack)var7).copy());
            }
            else if (var7 instanceof Item)
            {
                var3.add(new ItemStack((Item)var7));
            }
            else
            {
                if (!(var7 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipe!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        craftingRecipes.add(new ShapelessInfusionCraftingRecipes(key, par1ItemStack, var3, cost,tags));
    }

    /**
     * @param key the research key required for this recipe to work. Leave blank if it will work without research
     * @param recipeKey a string value of the key used in your research.xml for this recipe to display in the thaumonomicon
     * @param cost the vis cost
     * @param tags ObjectTags list of required aspects and their amounts. No more than 5 aspects should be used in a recipe.
     * @param par1ItemStack the recipe output
     * @param par2ArrayOfObj the recipe. Format is exactly the same as vanilla shapeless recipes
     */
    public static void addShapelessInfusionCraftingRecipe(String key, String recipeKey, int cost, ObjectTags tags, ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
    	addShapelessInfusionCraftingRecipe(key, cost, tags, par1ItemStack, par2ArrayOfObj);
    	ResearchList.craftingRecipesForResearch.put(recipeKey, Arrays.asList(getCraftingRecipes().size()-1));
    }
    
    /**
     * @param key the research key required for this recipe to work. Unlike the arcane crafting and infusion crafting
     * recipes a recipe key is automatically created using the same key. 
     * See method below if the research and recipes keys do not match
     * @param result the output result
     * @param cost the vis cost
     * @param tags the aspects required to craft this
     */
    public static void addCrucibleRecipe(String key, ItemStack result, int cost, ObjectTags tags) {
		getCrucibleRecipes().add(new RecipeCrucible(key, result, tags, cost));
	}
    
    /**
     * @param key the research key required for this recipe to work. 
     * @param recipeKey a string value of the key used in your research.xml for this recipe to display in the thaumonomicon
     * @param result the output result
     * @param cost the vis cost
     * @param tags the aspects required to craft this
     */
	public static void addCrucibleRecipe(String key, String recipeKey, ItemStack result, int cost, ObjectTags tags) {
		getCrucibleRecipes().add(new RecipeCrucible(key, recipeKey, result, tags, cost));
	}
	
	/**
	 * @param key the recipe key (NOT research key)
	 * @return the recipe
	 */
	public static RecipeCrucible getCrucibleRecipe(String key) {
		for (Object r:getCrucibleRecipes()) {
			if (r instanceof RecipeCrucible) {
				if (((RecipeCrucible)r).key.equals(key))
					return (RecipeCrucible)r;
			}
		}
		return null;
	}
	
	/**
	 * @param stack the recipe result
	 * @return the recipe
	 */
	public static RecipeCrucible getCrucibleRecipe(ItemStack stack) {
		for (Object r:getCrucibleRecipes()) {
			if (r instanceof RecipeCrucible) {
				if (((RecipeCrucible)r).recipeOutput.isItemEqual(stack))
					return (RecipeCrucible)r;
			}
		}
		return null;
	}
	
	/**
	 * @param stack the item
	 * @return the thaumcraft recipe key that produces that item. Used by the thaumonomicon drilldown feature.
	 */
	public static String getCraftingRecipeKey(ItemStack stack) {
		for (Object r:getCraftingRecipes()) {
			if (r instanceof IArcaneRecipe) {
				if (ThaumcraftApiHelper.areItemsEqual(stack,((IArcaneRecipe)r).getRecipeOutput()))
					return ((IArcaneRecipe)r).getKey();
			}
			if (r instanceof IInfusionRecipe) {
				if (ThaumcraftApiHelper.areItemsEqual(stack,((IInfusionRecipe)r).getRecipeOutput()))
					return ((IInfusionRecipe)r).getKey();
			}
		}
		return "";
	}
	
	//TAGS////////////////////////////////////////
	public static Map<List,ObjectTags> objectTags = new HashMap<List,ObjectTags>();
	
	/**
	 * Checks to see if the passed item/block already has aspects associated with it.
	 * @param id
	 * @param meta
	 * @return 
	 */
	public static boolean exists(int id, int meta) {
		ObjectTags tmp = ThaumcraftApi.objectTags.get(Arrays.asList(id,meta));
		if (tmp==null) {
			tmp = ThaumcraftApi.objectTags.get(Arrays.asList(id,-1));
			if (meta==-1 && tmp==null) {
				int index=0;
				do {
					tmp = ThaumcraftApi.objectTags.get(Arrays.asList(id,index));
					index++;
				} while (index<16 && tmp==null);
			}
			if (tmp==null) return false;
		}
		
		return true;
	}
	
	/**
	 * Used to assign apsects to the given item/block. Here is an example of the declaration for cobblestone:<p>
	 * <i>ThaumcraftApi.registerObjectTag(Block.cobblestone.blockID, -1, (new ObjectTags()).add(EnumTag.ROCK, 1).add(EnumTag.DESTRUCTION, 1));</i>
	 * @param id
	 * @param meta pass -1 if all damage values of this item/block should have the same aspects
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public static void registerObjectTag(int id, int meta, ObjectTags aspects) {
		aspects = ThaumcraftApiHelper.cullTags(aspects);
		objectTags.put(Arrays.asList(id,meta), aspects);
	}
	
	/**
	 * Used to assign aspects to the given item/block. 
	 * Attempts to automatically generate aspect tags by checking registered recipes.
	 * Here is an example of the declaration for pistons:<p>
	 * <i>ThaumcraftApi.registerComplexObjectTag(Block.pistonBase.blockID, 0, (new ObjectTags()).add(EnumTag.MECHANISM, 2).add(EnumTag.MOTION, 4));</i>
	 * @param id
	 * @param meta pass -1 if all damage values of this item/block should have the same aspects
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public static void registerComplexObjectTag(int id, int meta, ObjectTags aspects ) {
		if (!exists(id,meta)) {
			ObjectTags tmp = ThaumcraftApiHelper.generateTags(id, meta);
			if (tmp != null && tmp.size()>0) {
				for(EnumTag tag:tmp.getAspects()) {
					aspects.add(tag, tmp.getAmount(tag));
				}
			}
			registerObjectTag(id,meta,aspects);
		} else {
			ObjectTags tmp = ThaumcraftApiHelper.getObjectTags(new ItemStack(id,1,meta));
			for(EnumTag tag:aspects.getAspects()) {
				tmp.merge(tag, tmp.getAmount(tag));
			}
			registerObjectTag(id,meta,tmp);
		}
	}
	
	public static ArrayList getCrucibleRecipes() {
		return crucibleRecipes;
	}


	//AURAS//////////////////////////////////////////////////
	private static Method addFluxToClosest;
	/**
	 * Adds flux to the node closest to the given location
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param tags ObjectTags list of all the EnumTags and amounts of flux to add.
	 */
	public static void addFluxToClosest(World world, float x, float y, float z, ObjectTags tags) {
		try {
            if(addFluxToClosest == null) {
                Class fake = Class.forName("thaumcraft.common.AuraManager");
                addFluxToClosest = fake.getMethod("addFluxToClosest", World.class, float.class, float.class, float.class, ObjectTags.class);
            }
            addFluxToClosest.invoke(null, world,x,y,z,tags);
        } catch(Exception ex) { 
        	FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.AuraManager method addFluxToClosest");
        }
    }
	
	private static Method decreaseClosestAura;
	/**
	 * Removes an amount of vis from the aura node closest to the given location
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param amount The amount of vis to remove
	 * @param doit If set to false it will merely perform a check to see if there is enough vis to perform the operation. 
	 * If set to true it will actually decrease the vis as well.
	 * @return It will return true if there was enough vis to perform this operation
	 */
	public static boolean decreaseClosestAura(World world, double x, double y, double z, int amount, boolean doit) {
		boolean ret=false;
		try {
            if(decreaseClosestAura == null) {
                Class fake = Class.forName("thaumcraft.common.AuraManager");
                decreaseClosestAura = fake.getMethod("decreaseClosestAura", World.class, double.class, double.class, double.class, int.class, boolean.class);
            }
            ret = (Boolean) decreaseClosestAura.invoke(null, world,x,y,z,amount,doit);
        } catch(Exception ex) { 
        	FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.AuraManager method decreaseClosestAura");
        }
		return ret;
    }
	
	private static Method increaseLowestAura;
	/**
	 * Increases the lowest aura near the given location.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param amount
	 * @return it will return true if the operation was a success
	 */
	public static boolean increaseLowestAura(World world, double x, double y, double z, int amount) {
		boolean ret=false;
		try {
            if(increaseLowestAura == null) {
                Class fake = Class.forName("thaumcraft.common.AuraManager");
                increaseLowestAura = fake.getMethod("increaseLowestAura", World.class, double.class, double.class, double.class, int.class);
            }
            ret = (Boolean) increaseLowestAura.invoke(null, world,x,y,z,amount);
        } catch(Exception ex) { 
        	FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.AuraManager method increaseLowestAura");
        }
		return ret;
    }
	
	//BIOMES//////////////////////////////////////////////////
	public static HashMap<Integer,List> biomeInfo = new HashMap<Integer,List>();
	
	/**
	 * Registers custom biomes with thaumcraft
	 * @param biomeID The id of the biome
	 * @param visLevel The average vis level of nodes generated in this biome
	 * @param tag The EnumTag associated with this biome (used to determine infused ore spawns among other things)
	 * @param greatwood Does this biome support greatwood trees
	 * @param silverwood Does this biome support silverwood trees
	 */
	public static void registerBiomeInfo(int biomeID, int visLevel, EnumTag tag, boolean greatwood, boolean silverwood) {
		biomeInfo.put(biomeID, Arrays.asList(visLevel, tag, greatwood, silverwood));
	}
	
	public static int getBiomeAura(int biomeId) {
		try { 
			return (Integer) biomeInfo.get(biomeId).get(0);
		} catch (Exception e) {}
		return 200;
	}
	
	public static EnumTag getBiomeTag(int biomeId) {
		try {
			return (EnumTag) biomeInfo.get(biomeId).get(1);
		} catch (Exception e) {}
		return EnumTag.UNKNOWN;
	}
	
	public static boolean getBiomeSupportsGreatwood(int biomeId) {
		try {
			return (Boolean) biomeInfo.get(biomeId).get(2);
		} catch (Exception e) {}
		return false;
	}
	
	public static boolean getBiomeSupportsSilverwood(int biomeId) {
		try {
			return (Boolean) biomeInfo.get(biomeId).get(3);
		} catch (Exception e) {}
		return false;
	}
}
