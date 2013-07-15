package bstramke.NetherStuffs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.LeafItemBlock;
import bstramke.NetherStuffs.Blocks.Ore;
import bstramke.NetherStuffs.Blocks.OreItemBlock;
import bstramke.NetherStuffs.Blocks.PlankItemBlock;
import bstramke.NetherStuffs.Blocks.Sapling;
import bstramke.NetherStuffs.Blocks.SaplingItemBlock;
import bstramke.NetherStuffs.Blocks.WoodItemBlock;
import bstramke.NetherStuffs.Blocks.soulBlocker.SoulBlockerItemBlock;
import bstramke.NetherStuffs.Blocks.soulDetector.SoulDetectorItemBlock;
import bstramke.NetherStuffs.Blocks.soulSiphon.SoulSiphonItemBlock;
import bstramke.NetherStuffs.Items.ItemRegistry;
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
		return NetherStuffs.bUseForestry;
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
			addFermenterRecipeSapling(new ItemStack(BlockRegistry.Sapling, 1, Sapling.hellfire));
			addFermenterRecipeSapling(new ItemStack(BlockRegistry.Sapling, 1, 1));
			addFermenterRecipeSapling(new ItemStack(BlockRegistry.Sapling, 1, 2));
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
		/*RecipeManagers.fermenterManager.addRecipe(resource, 800, 1.0f, new FluidStack(liquidBiomass.itemID, 1, liquidBiomass.getItemDamage()), new FluidStack(Block.waterStill, 1));
		RecipeManagers.fermenterManager.addRecipe(resource, 800, 1.5f, new FluidStack(liquidBiomass.itemID, 1, liquidBiomass.getItemDamage()), new FluidStack(liquidJuice.itemID,
				1, liquidJuice.getItemDamage()));
		RecipeManagers.fermenterManager.addRecipe(resource, 800, 1.5f, new FluidStack(liquidBiomass.itemID, 1, liquidBiomass.getItemDamage()), new FluidStack(liquidHoney.itemID,
				1, liquidHoney.getItemDamage()));
		
		RecipeManagers.fermenterManager.addRecipe(resource, 1000, 0.5f, new FluidStack(NetherStuffs.SoulEnergyLiquid.itemID, 1), new FluidStack(Block.lavaStill, 1));*/
	}

	private static void addBackpackItems() {
		for (int i = 0; i < OreItemBlock.getMetadataSize(); i++) {
			if (i == Ore.netherOreCobblestone || i == Ore.netherStone)
				continue;
			BackpackManager.backpackItems[MINER].add(new ItemStack(BlockRegistry.netherOre, 1, i));
		}
		BackpackManager.backpackItems[MINER].add(new ItemStack(ItemRegistry.NetherOreIngot, 1, 0));
		
		for (int i = 0; i < SaplingItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[FORESTER].add(new ItemStack(BlockRegistry.Sapling, 1, i));
		}
		for (int i = 0; i < LeafItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[FORESTER].add(new ItemStack(BlockRegistry.netherLeaves, 1, i));
		}
		for (int i = 0; i < WoodItemBlock.getMetadataSize(); i++){
			BackpackManager.backpackItems[FORESTER].add(new ItemStack(BlockRegistry.netherWood, 1, i));
		}

		for (int i = 0; i < PlankItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.netherPlank, 1, i));
		}
		
		for (int i = 0; i < SoulBlockerItemBlock.getMetadataSize(); i++) {
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulBlocker, 1, i));
		}
		
		for (int i = 0; i< SoulDetectorItemBlock.getMetadataSize(); i++){
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulDetector, 1, i));
		}
		
		for (int i = 0; i< SoulSiphonItemBlock.getMetadataSize(); i++){
			BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulSiphon, 1, i));
		}
		
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.DemonicFurnace));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulBomb));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulGlassPane));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulGlass));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulWorkBench));
		BackpackManager.backpackItems[BUILDER].add(new ItemStack(BlockRegistry.SoulFurnace));
		//BackpackManager.backpackItems[ADVENTURER].add(new ItemStack(NetherItems.torchArrow));
	}

}
