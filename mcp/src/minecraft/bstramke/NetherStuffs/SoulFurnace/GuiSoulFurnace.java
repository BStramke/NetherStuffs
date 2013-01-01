package bstramke.NetherStuffs.SoulFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.Common.CommonProxy;

public class GuiSoulFurnace extends GuiContainer {

	private TileSoulFurnace inventory;

	public GuiSoulFurnace(InventoryPlayer player_inventory, TileSoulFurnace tile_entity) {
		super(new ContainerSoulFurnace(tile_entity, player_inventory));
		this.inventory = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Furnace Gui", 38, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int var4 = this.mc.renderEngine.getTexture(CommonProxy.SOULFURNACE_PNG);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		/*
		 * int var7;
		 * 
		 * if (this.inventory.isBurning()) { var7 = this.inventory.getBurnTimeRemainingScaled(12); this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2); }
		 */

		int var7 = this.inventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 0, var7 + 1, 16);

		int nBottomLeftY = var6 + 68;
		int nFillState = this.inventory.getFillingScaled(32);
		for (int x = 0; x < 32 && x < nFillState; x++) {
			this.drawTexturedModalRect(var5 + 9, nBottomLeftY - x, 176, 48 - x, 21, 1);
		}

		Integer nCurrent = this.inventory.getCurrentTankLevel();
		if (nCurrent >= 100)
			fontRenderer.drawString(nCurrent.toString(), var5 + 11, var6 + 49, 0x000000);
		else
			fontRenderer.drawString(nCurrent.toString(), var5 + 13, var6 + 49, 0x000000);

	}
}
