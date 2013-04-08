package bstramke.NetherStuffs.SoulEngine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.GuiLedger;
import bstramke.NetherStuffs.Common.NetherStuffsCore;
import bstramke.NetherStuffs.Items.NetherItems;

public class GuiSoulEngine extends GuiLedger {

	protected class EngineLedger extends Ledger {

		TileSoulEngine engine;
		int headerColour = 0xe1c92f;
		int subheaderColour = 0xaaafb8;
		int textColour = 0x000000;

		public EngineLedger(TileSoulEngine engine) {
			this.engine = engine;
			maxHeight = 94;
			overlayColor = 0xd46c1f;
		}

		@Override
		public void draw(int x, int y) {
			// Draw background
			drawBackground(x, y);
			// Draw icon
			mc.renderEngine.bindTexture("/gui/items.png");
			drawIcon(NetherStuffsCore.instance.icoEnergy, x + 3, y + 4);
			if (!isFullyOpened())
				return;
			fontRenderer.drawStringWithShadow("Energy", x + 22, y + 8, headerColour);
			fontRenderer.drawStringWithShadow("CurrentOutput:", x + 22, y + 20, subheaderColour);
			fontRenderer.drawString(engine.getCurrentOutput() + " MJ/t", x + 22, y + 32, textColour);
			fontRenderer.drawStringWithShadow("Stored:", x + 22, y + 44, subheaderColour);
			fontRenderer.drawString(engine.getEnergyStored() + " MJ", x + 22, y + 56, textColour);
			fontRenderer.drawStringWithShadow("Heat:", x + 22, y + 68, subheaderColour);
			fontRenderer.drawString(((double) engine.getHeat() / (double) 10) + " \u00B0C", x + 22, y + 80, textColour);
		}

		@Override
		public String getTooltip() {
			return engine.getCurrentOutput() + " MJ/t";
		}
	}

	private TileSoulEngine tile;

	public GuiSoulEngine(InventoryPlayer inventoryplayer, TileSoulEngine tileEngine) {
		super(new ContainerSoulEngine(tileEngine, inventoryplayer), tileEngine);
		tile = tileEngine;
		ledgerManager.add(new EngineLedger(tile));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		fontRenderer.drawString("Soulenergy Engine", getCenteredOffset("Soulenergy Engine"), 6, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	protected int getCenteredOffset(String string) {
		return getCenteredOffset(string, xSize);
	}

	protected int getCenteredOffset(String string, int xWidth) {
		return (xWidth - fontRenderer.getStringWidth(string)) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int a, int b) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(CommonProxy.SOULENERGYENGINE_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		TileSoulEngine engine = tile;

		int nBottomLeftY = var6 + 77;
		int nFillState = engine.getScaledTankLevel(58);

		this.mc.renderEngine.bindTexture("/gui/items.png");
		Icon LiquidIcon = NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0);
		int y = 0;
		for (int x = 16; x <= 32 && x <= nFillState; x += 16) {
			drawTexturedModelRectFromIcon(var5 + 104, nBottomLeftY - x, LiquidIcon, 16, 16);
			y = x;
		}

		if (nFillState % 16 != 0) {
			nFillState -= y;
			drawTexturedModelRectFromIcon(var5 + 104, nBottomLeftY - y - nFillState, LiquidIcon, 16, nFillState);
		}

		mc.renderEngine.bindTexture(CommonProxy.SOULENERGYENGINE_PNG);
		drawTexturedModalRect(var5 + 104, var6 + 19, 176, 0, 16, 60);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		if (par1 > var5 + 104 && par1 < var5 + 104 + 16 && par2 > var6 + 75 - 58 && par2 < var6 + 77) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.setItemDamage(tile.MAX_LIQUID);
			par1ItemStack.stackSize = this.tile.getCurrentTankLevel();
			drawItemStackTooltip(par1ItemStack, par1, par2);
		}
	}
}
