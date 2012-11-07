package NetherStuffs.SoulDetector;

import java.awt.Color;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class GuiSoulDetector extends GuiContainer {

	private TileSoulDetector tile_entity;
	private GuiButton btnRangeIncZUp;
	private GuiButton btnRangeDecZUp;
	private GuiButton btnRangeIncNorth;
	private GuiButton btnRangeDecNorth;
	private GuiButton btnRangeIncWest;
	private GuiButton btnRangeDecWest;
	private GuiButton btnRangeIncEast;
	private GuiButton btnRangeDecEast;
	private GuiButton btnRangeIncZDown;
	private GuiButton btnRangeDecZDown;
	private GuiButton btnRangeIncSouth;
	private GuiButton btnRangeDecSouth;

	public GuiSoulDetector(TileSoulDetector tile_entity) {
		super(new ContainerSoulDetector(tile_entity));
		this.tile_entity = tile_entity;
	}

	public void initGui() {
		super.initGui();
		this.controlList.add(this.btnRangeIncZUp = new GuiButtonSoulDetector(1, this.guiLeft + 57, this.guiTop + 15, 9, 9, false));
		this.controlList.add(this.btnRangeDecZUp = new GuiButtonSoulDetector(2, this.guiLeft + 57 + 9, this.guiTop + 15, 9, 9, true));

		this.controlList.add(this.btnRangeIncNorth = new GuiButtonSoulDetector(3, this.guiLeft + 96, this.guiTop + 15, 9, 9, false));
		this.controlList.add(this.btnRangeDecNorth = new GuiButtonSoulDetector(4, this.guiLeft + 96 + 9, this.guiTop + 15, 9, 9, true));

		this.controlList.add(this.btnRangeIncWest = new GuiButtonSoulDetector(5, this.guiLeft + 57, this.guiTop + 33, 9, 9, false));
		this.controlList.add(this.btnRangeDecWest = new GuiButtonSoulDetector(6, this.guiLeft + 57 + 9, this.guiTop + 33, 9, 9, true));

		this.controlList.add(this.btnRangeIncEast = new GuiButtonSoulDetector(7, this.guiLeft + 117, this.guiTop + 33, 9, 9, false));
		this.controlList.add(this.btnRangeDecEast = new GuiButtonSoulDetector(8, this.guiLeft + 117 + 9, this.guiTop + 33, 9, 9, true));

		this.controlList.add(this.btnRangeIncZDown = new GuiButtonSoulDetector(9, this.guiLeft + 57, this.guiTop + 51, 9, 9, false));
		this.controlList.add(this.btnRangeDecZDown = new GuiButtonSoulDetector(10, this.guiLeft + 57 + 9, this.guiTop + 51, 9, 9, true));

		this.controlList.add(this.btnRangeIncSouth = new GuiButtonSoulDetector(11, this.guiLeft + 96, this.guiTop + 51, 9, 9, false));
		this.controlList.add(this.btnRangeDecSouth = new GuiButtonSoulDetector(12, this.guiLeft + 96 + 9, this.guiTop + 51, 9, 9, true));
	}

	/*
	 * public void updateScreen() { super.updateScreen(); }
	 */

	private void drawRangeNumbers() {
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		Integer nRangeUp = ((Integer) this.tile_entity.getRange(ForgeDirection.UP));
		Integer nRangeNorth = ((Integer) this.tile_entity.getRange(ForgeDirection.NORTH));
		Integer nRangeWest = ((Integer) this.tile_entity.getRange(ForgeDirection.WEST));
		Integer nRangeEast = ((Integer) this.tile_entity.getRange(ForgeDirection.EAST));
		Integer nRangeDown = ((Integer) this.tile_entity.getRange(ForgeDirection.DOWN));
		Integer nRangeSouth = ((Integer) this.tile_entity.getRange(ForgeDirection.SOUTH));

		this.fontRenderer.drawString(nRangeUp.toString(), var5 + 57 - nRangeUp.toString().length() * 7, var6 + 16, 0xffffff);
		this.fontRenderer.drawString(nRangeNorth.toString(), var5 + 96 - nRangeNorth.toString().length() * 7, var6 + 16, 0xffffff);
		this.fontRenderer.drawString(nRangeWest.toString(), var5 + 57 - nRangeWest.toString().length() * 7, var6 + 34, 0xffffff);
		this.fontRenderer.drawString(nRangeEast.toString(), var5 + 117 - nRangeEast.toString().length() * 7, var6 + 34, 0xffffff);
		this.fontRenderer.drawString(nRangeDown.toString(), var5 + 57 - nRangeDown.toString().length() * 7, var6 + 52, 0xffffff);
		this.fontRenderer.drawString(nRangeSouth.toString(), var5 + 96 - nRangeSouth.toString().length() * 7, var6 + 52, 0xffffff);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.enabled) {
			switch (par1GuiButton.id) {
			case 1: // ZUp+
			case 2: // ZUp-
			case 3: // N+
			case 4: // N-
			case 5: // W+
			case 6: // W-
			case 7: // E+
			case 8: // E-
			case 9: // ZDown+
			case 10: // ZDown-
			case 11: // S+
			case 12: // S-
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Detector", 6, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int var4 = this.mc.renderEngine.getTexture("/souldetector.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		drawRangeNumbers();
	}
}
