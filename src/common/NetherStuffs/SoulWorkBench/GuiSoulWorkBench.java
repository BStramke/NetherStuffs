package NetherStuffs.SoulWorkBench;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiSoulWorkBench extends GuiContainer {

	private int ySize = 178;
	private TileSoulWorkBench benchInventory;

	public GuiSoulWorkBench(InventoryPlayer player_inventory, TileSoulWorkBench tile_entity) {
		super(new ContainerSoulWorkBench(tile_entity, player_inventory));
		this.benchInventory = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Workbench", 38, 0, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2 - 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int var4 = this.mc.renderEngine.getTexture("/soulworkbench.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7;

		var7 = this.benchInventory.getProgressScaled(24);
		this.drawTexturedModalRect(var5 + 106, var6 + 34, this.ySize, 0, var7, 16);

		// draw fill state from bottom to top
		int nBottomLeftY = var6 + 68;
		int nFillState = this.benchInventory.getFillingScaled(32);
		for (int i = 0; i < 32 && i < nFillState; i++) {
			this.drawTexturedModalRect(var5 + 9, nBottomLeftY - i, 176, 48 - i, 21, 1);
		}

		int nRequired = this.benchInventory.getSoulEnergyRequired();
		if (nRequired > 0)
			fontRenderer.drawString("+ " + nRequired + " Energy", var5 + 39, var6 + 72, 0x000000);

		Integer nCurrent = this.benchInventory.currentTankLevel;
		if(nCurrent>=100)
			fontRenderer.drawString(nCurrent.toString(), var5 + 11, var6 + 49, 0x000000);
		else
			fontRenderer.drawString(nCurrent.toString(), var5 + 13, var6 + 49, 0x000000);
	}
}
