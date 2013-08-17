package bstramke.NetherStuffs.Common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Blocks.demonicFurnace.ContainerDemonicFurnace;
import bstramke.NetherStuffs.Blocks.demonicFurnace.GuiDemonicFurnace;
import bstramke.NetherStuffs.Blocks.demonicFurnace.TileDemonicFurnace;
import bstramke.NetherStuffs.Blocks.soulSiphon.TileSoulSiphon;
import bstramke.NetherStuffs.Blocks.soulWorkBench.ContainerSoulWorkBench;
import bstramke.NetherStuffs.Blocks.soulWorkBench.GuiSoulWorkBench;
import bstramke.NetherStuffs.Blocks.soulWorkBench.TileSoulWorkBench;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		if (tile_entity instanceof TileDemonicFurnace) {
			return new ContainerDemonicFurnace((TileDemonicFurnace) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileSoulWorkBench) {
			return new ContainerSoulWorkBench((TileSoulWorkBench) tile_entity, player.inventory);
		} 
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity instanceof TileDemonicFurnace) {
			return new GuiDemonicFurnace(player.inventory, (TileDemonicFurnace) tile_entity);
		} else if (tile_entity instanceof TileSoulWorkBench) {
			return new GuiSoulWorkBench(player.inventory, (TileSoulWorkBench) tile_entity);
		} 
		return null;
	}
}
