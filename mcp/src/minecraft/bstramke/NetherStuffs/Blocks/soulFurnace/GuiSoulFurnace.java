package bstramke.NetherStuffs.Blocks.soulFurnace;

import java.awt.Point;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.soulSiphon.ContainerSoulSiphon;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.Gui.GuiContainerSoulTank;
import bstramke.NetherStuffs.Items.ItemRegistry;

public class GuiSoulFurnace extends GuiContainerSoulTank {

	private TileSoulFurnace inventory;

	public GuiSoulFurnace(InventoryPlayer player_inventory, TileSoulFurnace tile_entity) {
		super(tile_entity, new ContainerSoulFurnace(tile_entity, player_inventory), new Point(12,37), new Point(28,69));
		this.inventory = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Furnace Gui", 38, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.SOULFURNACE_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		int var7 = this.inventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 0, var7 + 1, 16);
		drawTankScale();
	}
}
