package bstramke.NetherStuffs.Blocks.soulWorkBench;

import java.awt.Point;
import java.awt.Rectangle;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.Gui.GuiContainerSoulTank;
import bstramke.NetherStuffs.Items.ItemRegistry;

public class GuiSoulWorkBench extends GuiContainerSoulTank {

	private int ySize = 178;
	private TileSoulWorkBench benchInventory;

	public GuiSoulWorkBench(InventoryPlayer player_inventory, TileSoulWorkBench tile_entity) {
		super(tile_entity, new ContainerSoulWorkBench(tile_entity, player_inventory), new Point(12,37), new Point(28,69));
		this.benchInventory = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Workbench", 38, 0, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2 - 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.SOULWORKBENCH_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7;

		var7 = this.benchInventory.getProgressScaled(24);
		this.drawTexturedModalRect(var5 + 106, var6 + 34, this.ySize, 0, var7, 16);
		
		int nRequired = this.benchInventory.getSoulEnergyRequired();
		if (nRequired > 0)
			fontRenderer.drawString("+ " + nRequired + " Energy", var5 + 39, var6 + 72, 0x000000);
		
		drawTankScale();
	}
}
