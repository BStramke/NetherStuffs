package bstramke.NetherStuffs.Blocks.soulCondenser;

import java.awt.Point;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.soulFurnace.ContainerSoulFurnace;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.Gui.GuiContainerSoulTank;
import bstramke.NetherStuffs.Items.ItemRegistry;

public class GuiSoulCondenser extends GuiContainerSoulTank {

	private TileSoulCondenser tile_entity;

	public GuiSoulCondenser(InventoryPlayer player_inventory, TileSoulCondenser tile_entity) {
		super(tile_entity, new ContainerSoulCondenser(tile_entity, player_inventory), new Point(12,37), new Point(28,69));
		this.tile_entity = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Condenser", 38, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.SOULCONDENSER_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		drawTankScale();
	}
}
