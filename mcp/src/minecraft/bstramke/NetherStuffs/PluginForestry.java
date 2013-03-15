package bstramke.NetherStuffs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidStack;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Blocks.NetherLeavesItemBlock;
import bstramke.NetherStuffs.Blocks.NetherOre;
import bstramke.NetherStuffs.Blocks.NetherOreItemBlock;
import bstramke.NetherStuffs.Blocks.NetherPlankItemBlock;
import bstramke.NetherStuffs.Blocks.NetherSapling;
import bstramke.NetherStuffs.Blocks.NetherSaplingItemBlock;
import bstramke.NetherStuffs.Blocks.NetherWoodItemBlock;
import bstramke.NetherStuffs.Blocks.SoulBlockerItemBlock;
import bstramke.NetherStuffs.Blocks.SoulDetectorItemBlock;
import bstramke.NetherStuffs.Blocks.SoulSiphonItemBlock;
import bstramke.NetherStuffs.Items.NetherItems;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;
import forestry.api.core.IOreDictionaryHandler;
import forestry.api.core.IPacketHandler;
import forestry.api.core.IPickupHandler;
import forestry.api.core.IPlugin;
import forestry.api.core.IResupplyHandler;
import forestry.api.core.ISaveEventHandler;
import forestry.api.core.ItemInterface;
import forestry.api.core.PluginInfo;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;

@PluginInfo(name = "NetherStuffs Forestry Plugin", pluginID = "NetherStuffsForestryPlugin", author = "bstramke")
public class PluginForestry implements IPlugin {

	// forestry backpack numbers
	private static final int MINER = 0;
	private static final int DIGGER = 1;
	private static final int FORESTER = 2;
	private static final int ADVENTURER = 3;
	private static final int BUILDER = 4;
	

	private static ItemStack liquidJuice;
	private static ItemStack liquidHoney;
	private static ItemStack liquidBiomass;

	@Override
	public boolean isAvailable() {
		// return Loader.isModLoaded("mod_Forestry");
		return true;
	}

	@Override
	public void preInit() {}

	@Override
	public void doInit() {
		FMLLog.info("[NetherStuffs] Loading Forestry Plugin");
		addBackpackItems();

		liquidJuice = ItemInterface.getItem("liquidJuice");
		liquidHoney = ItemInterface.getItem("liquidHoney");
		liquidBiomass = ItemInterface.getItem("liquidBiomass");

		if (RecipeManagers.fermenterManager != null) {
			addFermenterRecipeSapling(new ItemStack(NetherBlocks.netherSapling, 1, NetherSapling.hellfire));
			addFermenterRecipeSapling(new ItemStack(NetherBlocks.netherSapling, 1, 1));
			addFermenterRecipeSapling(new ItemStack(NetherBlocks.netherSapling, 1, 2));
		}

		/*
		 * RecipeManagers.carpenterManager.addCrating(new ItemStack(NetherBlocks.netherWood, 1, NetherWood.acid)); RecipeManagers.carpenterManager.addCrating(new
		 * ItemStack(NetherBlocks.netherWood, 1, NetherWood.death)); RecipeManagers.carpenterManager.addCrating(new ItemStack(NetherBlocks.netherWood, 1, NetherWood.hellfire));
		 */

	}

	@Override
	public void postInit() {}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public void generateSurface(World world, Random rand, int chunkX, int chunkZ) {}

	@Override
	public IGuiHandler getGuiHandler() {
		return null;
	}

	@Override
	public IPacketHandler getPacketHandler() {
		return null;
	}

	@Override
	public IPickupHandler getPickupHandler() {
		return null;
	}

	@Override
	public IResupplyHandler getResupplyHandler() {
		return null;
	}

	@Override
	public ISaveEventHandler getSaveEventHandler() {
		return null;
	}

	@Override
	public IOreDictionaryHandler getDictionaryHandler() {
		return null;
	}

	@Override
	public ICommand[] getConsoleCommands() {
		return null;
	}

	/**
	 * @param resource
	 *           Sapling
	 */
	private static void addFermenterRecipeSapling(ItemStack resource) {
		RecipeManagers.fermenterManager.addRecipe(resource, 800, 1.0f, new LiquidStack(liquidBiomass.itemID, 1, liquidBiomass.getItemDamage()), new LiquidStack(Block.waterStill, 1));
		RecipeManagers.fermenterManager.addRecipe(resource, 800, 1.5f, new LiquidStack(liquidBiomass.itemID, 1, liquidBiomass.getItemDamage()), new LiquidStack(liquidJuice.itemID,
				1, liquidJuice.getItemDamage()));
		RecipeManagers.fermenterManager.addRecipe(resource, 800, 1.5f, new LiquidStack(liquidBiomass.itemID, 1, liquidBiomass.getItemDamage()), new LiquidStack(liquidHoney.itemID,
				1, liquidHoney.getItemDamage()));
		
		RecipeManagers.fermenterManager.addRecipe(resource, 1000, 0.5f, new LiquidStack(NetherStuffs.SoulEnergyLiquid.itemID, 1, 0), new LiquidStack(Block.lavaStill, 1));
	}

	private static void addBackpackItems() {
		for (int i = 0; i < NetherOreItemBlock.getMetadataSize(); i++) {
			if (i == NetherOre.netherOreCobblestone || i == NetherOre.netherStone)
				continue;
			BackpackManager.backpackItems[MINER].add(new ItemStack(NetherBlocks.netherOre, 1, i));
		}
		BackpackManager.backpackItems[MINER].add(new ItemStack(NetherItems.NetherOreIngot, 1, 0));
		
		for (int i = 0; i < NetherSaplingItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[FORESTER].add(new ItemStack(NetherBlocks.netherSapling, 1, i));
		}
		for (int i = 0; i < NetherLeavesItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[FORESTER].add(new ItemStack(NetherBlocks.netherLeaves, 1, i));
		}
		for (int i = 0; i < NetherWoodItemBlock.getMetadataSize(); i++){
			BackpackManager.backpackItems[FORESTER].add(new ItemStack(NetherBlocks.netherWood, 1, i));
		}

		for (int i = 0; i < NetherPlankItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.netherPlank, 1, i));
		}
		
		for (int i = 0; i < SoulBlockerItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.NetherSoulBlocker, 1, i));
		}
		
		for (int i = 0; i< SoulDetectorItemBlock.getMetadataSize(); i++){
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.NetherSoulDetector, 1, i));
		}
		
		for (int i = 0; i< SoulSiphonItemBlock.getMetadataSize(); i++){
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.NetherSoulSiphon, 1, i));
		}
		
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.NetherDemonicFurnace));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.netherSoulBomb));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.NetherSoulGlassPane));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.NetherSoulGlass));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.netherSoulWorkBench));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(NetherBlocks.NetherSoulFurnace));
		//BackpackManager.backpackItems[ADVENTURER].add(new ItemStack(NetherItems.torchArrow));
	}

}
