package bstramke.NetherStuffs.Common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import bstramke.NetherStuffs.DemonicFurnace.ContainerDemonicFurnace;
import bstramke.NetherStuffs.DemonicFurnace.GuiDemonicFurnace;
import bstramke.NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import bstramke.NetherStuffs.SoulBlocker.ContainerSoulBlocker;
import bstramke.NetherStuffs.SoulBlocker.TileSoulBlocker;
import bstramke.NetherStuffs.SoulCondenser.ContainerSoulCondenser;
import bstramke.NetherStuffs.SoulCondenser.ContainerSoulSmelter;
import bstramke.NetherStuffs.SoulCondenser.GuiSoulCondenser;
import bstramke.NetherStuffs.SoulCondenser.GuiSoulSmelter;
import bstramke.NetherStuffs.SoulCondenser.TileSoulCondenser;
import bstramke.NetherStuffs.SoulDetector.ContainerSoulDetector;
import bstramke.NetherStuffs.SoulDetector.GuiSoulDetector;
import bstramke.NetherStuffs.SoulDetector.TileSoulDetector;
import bstramke.NetherStuffs.SoulEngine.ContainerSoulEngine;
import bstramke.NetherStuffs.SoulEngine.GuiSoulEngine;
import bstramke.NetherStuffs.SoulEngine.TileSoulEngine;
import bstramke.NetherStuffs.SoulFurnace.ContainerSoulFurnace;
import bstramke.NetherStuffs.SoulFurnace.GuiSoulFurnace;
import bstramke.NetherStuffs.SoulFurnace.TileSoulFurnace;
import bstramke.NetherStuffs.SoulSiphon.ContainerSoulSiphon;
import bstramke.NetherStuffs.SoulSiphon.GuiSoulSiphon;
import bstramke.NetherStuffs.SoulSiphon.TileSoulSiphon;
import bstramke.NetherStuffs.SoulWorkBench.ContainerSoulWorkBench;
import bstramke.NetherStuffs.SoulWorkBench.GuiSoulWorkBench;
import bstramke.NetherStuffs.SoulWorkBench.TileSoulWorkBench;
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
		} else if (tile_entity instanceof TileSoulEngine) {
			return new ContainerSoulEngine((TileSoulEngine) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileSoulCondenser) {
			if (world.getBlockMetadata(x, y, z) == 0)
				return new ContainerSoulCondenser((TileSoulCondenser) tile_entity, player.inventory);
			else if (world.getBlockMetadata(x, y, z) == 1)
				return new ContainerSoulSmelter((TileSoulCondenser) tile_entity, player.inventory);
		}

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
		} else if (tile_entity instanceof TileSoulEngine) {
			return new GuiSoulEngine(player.inventory, (TileSoulEngine) tile_entity);
		} else if (tile_entity instanceof TileSoulCondenser) {
			if (world.getBlockMetadata(x, y, z) == 0)
				return new GuiSoulCondenser(player.inventory, (TileSoulCondenser) tile_entity);
			else if (world.getBlockMetadata(x, y, z) == 1)
				return new GuiSoulSmelter(player.inventory, (TileSoulCondenser) tile_entity);

		}

		return null;
	}
}
