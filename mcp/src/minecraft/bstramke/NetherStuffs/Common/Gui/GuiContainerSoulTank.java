package bstramke.NetherStuffs.Common.Gui;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.SoulWorkBench.ContainerSoulWorkBench;
import bstramke.NetherStuffs.SoulWorkBench.TileSoulWorkBench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

public abstract class GuiContainerSoulTank extends GuiContainer {

	private ISoulEnergyTank tile;
	private int TankTopXpos;
	private int TankTopYpos;
	private int TankWidth;
	private int TankHeight;

	public GuiContainerSoulTank(ISoulEnergyTank Tile, Container par1Container, Point TankTopLeft, Point TankBottomRight) {
		super(par1Container);
		TankTopXpos = TankTopLeft.x;
		TankTopYpos = TankTopLeft.y;
		TankWidth = Math.abs(TankBottomRight.x - TankTopXpos) + 1;
		TankHeight = Math.abs(TankBottomRight.y - TankTopYpos) + 1;
		tile = Tile;
	}

	public void drawTankScale() {
		int TopX = (this.width - this.xSize) / 2 + TankTopXpos;
		int TopY = (this.height - this.ySize) / 2 - 7 + TankTopYpos;

		int nBottomLeftY = TopY + TankHeight;

		int nFillState = tile.getFillingScaled(TankHeight - 1);
		this.mc.renderEngine.bindTexture("/gui/items.png");
		Icon LiquidIcon = NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0);
		int y = 0;
		for (int x = 16; x <= 32 && x <= nFillState; x += 16) {
			drawTexturedModelRectFromIcon(TopX, nBottomLeftY - x, LiquidIcon, 16, 16);
			y = x;
		}

		if (nFillState % 16 != 0) {
			nFillState -= y;
			drawTexturedModelRectFromIcon(TopX, nBottomLeftY - y - nFillState, LiquidIcon, 16, nFillState);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);

		int x = (this.width - this.xSize) / 2 + TankTopXpos;
		int y = (this.height - this.ySize) / 2 - 7 + TankTopYpos;

		if (new Rectangle(x, y, TankWidth, TankHeight).contains(mouseX, mouseY)) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.stackSize = tile.getCurrentTankLevel();
			par1ItemStack.setItemDamage(tile.getMaxTankLevel());
			drawItemStackTooltip(par1ItemStack, mouseX, mouseY);
		}
	}
}
