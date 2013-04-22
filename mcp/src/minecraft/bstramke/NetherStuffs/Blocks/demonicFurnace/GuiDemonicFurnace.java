package bstramke.NetherStuffs.Blocks.demonicFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.Common.CommonProxy;

public class GuiDemonicFurnace extends GuiContainer {

	private TileDemonicFurnace furnaceInventory;

	public GuiDemonicFurnace(InventoryPlayer player_inventory, TileDemonicFurnace tile_entity) {
		super(new ContainerDemonicFurnace(tile_entity, player_inventory));
		this.furnaceInventory = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Demonic Furnace Gui", 38, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.FURNANCE_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		int var7;

		if (this.furnaceInventory.isBurning()) {
			var7 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
		}

		var7 = this.furnaceInventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);

	}
}