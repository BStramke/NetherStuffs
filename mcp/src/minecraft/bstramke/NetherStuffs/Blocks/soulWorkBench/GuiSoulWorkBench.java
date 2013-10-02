package bstramke.NetherStuffs.Blocks.soulWorkBench;

import java.awt.Point;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.Blocks.demonicFurnace.ContainerDemonicFurnace;
import bstramke.NetherStuffs.Common.CommonProxy;

public class GuiSoulWorkBench extends GuiContainer {
	private static final ResourceLocation BackgroundTexture = new ResourceLocation(CommonProxy.SOULWORKBENCH_PNG);
	private TileSoulWorkBench benchInventory;

	public GuiSoulWorkBench(InventoryPlayer player_inventory, TileSoulWorkBench tile_entity) {
		super(new ContainerSoulWorkBench(tile_entity, player_inventory));
		this.benchInventory = tile_entity;
		ySize = 178;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Workbench", 38, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(BackgroundTexture);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);		
	}
}
