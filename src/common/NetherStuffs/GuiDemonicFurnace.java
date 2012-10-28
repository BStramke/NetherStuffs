package NetherStuffs;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

import NetherStuffs.Common.ContainerDemonicFurnace;
import NetherStuffs.Common.TileDemonicFurnace;

public class GuiDemonicFurnace extends GuiContainer {

	private TileDemonicFurnace furnaceInventory;

	public GuiDemonicFurnace(InventoryPlayer player_inventory, TileDemonicFurnace tile_entity) {
		super(new ContainerDemonicFurnace(tile_entity, player_inventory));
		this.furnaceInventory = tile_entity;
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Demonic Furnace Gui", 6, 6, 0xffffff);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 6, ySize - 96, 0xffffff);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int var4 = this.mc.renderEngine.getTexture("/furnace.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
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