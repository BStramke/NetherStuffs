package bstramke.NetherStuffs.Common.Gui;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.soulWorkBench.ContainerSoulWorkBench;
import bstramke.NetherStuffs.Blocks.soulWorkBench.TileSoulWorkBench;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.ItemRegistry;
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
		TankWidth = Math.abs(TankBottomRight.x - TankTopXpos);
		TankHeight = Math.abs(TankBottomRight.y - TankTopYpos);
		tile = Tile;
	}

	public void drawTankScale() {
		int TopX = (this.width - this.xSize) / 2 + TankTopXpos;
		int TopY = (this.height - this.ySize) / 2 + TankTopYpos;
		
		int nBottomLeftY = TopY + TankHeight;

		int nFillState = tile.getFillingScaled(TankHeight);
		this.mc.renderEngine.bindTexture("/terrain.png");
		
		Icon LiquidIcon = BlockRegistry.LiquidStill.getIcon(BlockRegistry.sideTop, 0);
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
		int y = (this.height - this.ySize) / 2 + TankTopYpos;

		if (new Rectangle(x, y, TankWidth, TankHeight).contains(mouseX, mouseY)) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.stackSize = tile.getCurrentTankLevel();
			par1ItemStack.setItemDamage(tile.getMaxTankLevel());
			drawItemStackTooltip(par1ItemStack, mouseX, mouseY);
		}
	}
}
