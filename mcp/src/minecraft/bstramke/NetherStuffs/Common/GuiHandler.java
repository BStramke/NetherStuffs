package bstramke.NetherStuffs.Common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Blocks.demonicFurnace.ContainerDemonicFurnace;
import bstramke.NetherStuffs.Blocks.demonicFurnace.GuiDemonicFurnace;
import bstramke.NetherStuffs.Blocks.demonicFurnace.TileDemonicFurnace;
import bstramke.NetherStuffs.Blocks.soulBlocker.ContainerSoulBlocker;
import bstramke.NetherStuffs.Blocks.soulBlocker.TileSoulBlocker;
import bstramke.NetherStuffs.Blocks.soulCondenser.ContainerSoulCondenser;
import bstramke.NetherStuffs.Blocks.soulCondenser.ContainerSoulSmelter;
import bstramke.NetherStuffs.Blocks.soulCondenser.GuiSoulCondenser;
import bstramke.NetherStuffs.Blocks.soulCondenser.GuiSoulSmelter;
import bstramke.NetherStuffs.Blocks.soulCondenser.TileSoulCondenser;
import bstramke.NetherStuffs.Blocks.soulDetector.ContainerSoulDetector;
import bstramke.NetherStuffs.Blocks.soulDetector.GuiSoulDetector;
import bstramke.NetherStuffs.Blocks.soulDetector.TileSoulDetector;
import bstramke.NetherStuffs.Blocks.soulEngine.ContainerSoulEngine;
import bstramke.NetherStuffs.Blocks.soulEngine.GuiSoulEngine;
import bstramke.NetherStuffs.Blocks.soulEngine.TileSoulEngine;
import bstramke.NetherStuffs.Blocks.soulFurnace.ContainerSoulFurnace;
import bstramke.NetherStuffs.Blocks.soulFurnace.GuiSoulFurnace;
import bstramke.NetherStuffs.Blocks.soulFurnace.TileSoulFurnace;
import bstramke.NetherStuffs.Blocks.soulSiphon.ContainerSoulSiphon;
import bstramke.NetherStuffs.Blocks.soulSiphon.GuiSoulSiphon;
import bstramke.NetherStuffs.Blocks.soulSiphon.TileSoulSiphon;
import bstramke.NetherStuffs.Blocks.soulWorkBench.ContainerSoulWorkBench;
import bstramke.NetherStuffs.Blocks.soulWorkBench.GuiSoulWorkBench;
import bstramke.NetherStuffs.Blocks.soulWorkBench.TileSoulWorkBench;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity instanceof TileSoulFurnace) {
			return new ContainerSoulFurnace((TileSoulFurnace) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileDemonicFurnace) {
			return new ContainerDemonicFurnace((TileDemonicFurnace) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileSoulWorkBench) {
			return new ContainerSoulWorkBench((TileSoulWorkBench) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileSoulDetector) {
			return new ContainerSoulDetector((TileSoulDetector) tile_entity);
		} else if (tile_entity instanceof TileSoulBlocker) {
			return new ContainerSoulBlocker();
		} else if (tile_entity instanceof TileSoulSiphon) {
			return new ContainerSoulSiphon((TileSoulSiphon) tile_entity, player.inventory);
		} /*else if (tile_entity instanceof TileSoulEngine) {
			return new ContainerSoulEngine((TileSoulEngine) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileSoulCondenser) {
			if (world.getBlockMetadata(x, y, z) == 0)
				return new ContainerSoulCondenser((TileSoulCondenser) tile_entity, player.inventory);
			else if (world.getBlockMetadata(x, y, z) == 1)
				return new ContainerSoulSmelter((TileSoulCondenser) tile_entity, player.inventory);
		}*/

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity instanceof TileSoulFurnace) {
			return new GuiSoulFurnace(player.inventory, (TileSoulFurnace) tile_entity);
		} else if (tile_entity instanceof TileDemonicFurnace) {
			return new GuiDemonicFurnace(player.inventory, (TileDemonicFurnace) tile_entity);
		} else if (tile_entity instanceof TileSoulWorkBench) {
			return new GuiSoulWorkBench(player.inventory, (TileSoulWorkBench) tile_entity);
		} else if (tile_entity instanceof TileSoulDetector) {
			return new GuiSoulDetector((TileSoulDetector) tile_entity, player);
		} else if (tile_entity instanceof TileSoulSiphon) {
			return new GuiSoulSiphon(player.inventory, (TileSoulSiphon) tile_entity);
		}/* else if (tile_entity instanceof TileSoulEngine) {
			return new GuiSoulEngine(player.inventory, (TileSoulEngine) tile_entity);
		} else if (tile_entity instanceof TileSoulCondenser) {
			if (world.getBlockMetadata(x, y, z) == 0)
				return new GuiSoulCondenser(player.inventory, (TileSoulCondenser) tile_entity);
			else if (world.getBlockMetadata(x, y, z) == 1)
				return new GuiSoulSmelter(player.inventory, (TileSoulCondenser) tile_entity);

		}*/

		return null;
	}
}
