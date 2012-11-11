package NetherStuffs.Common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import NetherStuffs.DemonicFurnace.ContainerDemonicFurnace;
import NetherStuffs.DemonicFurnace.GuiDemonicFurnace;
import NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import NetherStuffs.SoulDetector.ContainerSoulDetector;
import NetherStuffs.SoulDetector.GuiSoulDetector;
import NetherStuffs.SoulDetector.TileSoulDetector;
import NetherStuffs.SoulWorkBench.ContainerSoulWorkBench;
import NetherStuffs.SoulWorkBench.GuiSoulWorkBench;
import NetherStuffs.SoulWorkBench.TileSoulWorkBench;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity instanceof TileDemonicFurnace) {
			return new ContainerDemonicFurnace((TileDemonicFurnace) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileSoulWorkBench) {
			return new ContainerSoulWorkBench((TileSoulWorkBench) tile_entity, player.inventory);
		} else if (tile_entity instanceof TileSoulDetector) {
			return new ContainerSoulDetector((TileSoulDetector) tile_entity);
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
		} else if (tile_entity instanceof TileSoulDetector) {
			return new GuiSoulDetector((TileSoulDetector) tile_entity, player);
		}

		return null;
	}
}
