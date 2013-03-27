package bstramke.NetherStuffs.SoulEngine;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.NetherItems;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class GuiSoulEnergyEngine extends GuiContainer{

	private TileEngine tile;
	public GuiSoulEnergyEngine(InventoryPlayer inventoryplayer, TileEngine tileEngine) {
		super(new ContainerEngine(tileEngine, inventoryplayer));
		tile = tileEngine;
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

		TileEngine engine = tile;

		int nBottomLeftY = var6 + 69;
		int nFillState = 58;// = engine.getScaledBurnTime(58);
		
		this.mc.renderEngine.bindTexture("/gui/items.png");
		Icon LiquidIcon = NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0);
		int y = 0;
		for (int x = 16; x <= 32 && x <= nFillState; x+=16) {
			drawTexturedModelRectFromIcon(var5 + 104, nBottomLeftY-x, LiquidIcon, 16, 16);
			y = x;
		}
				
		if(nFillState%16!=0){
			nFillState -= y;
			drawTexturedModelRectFromIcon(var5 + 104, nBottomLeftY-y-nFillState, LiquidIcon, 16, nFillState);
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		if (par1 > var5 + 9 && par1 < var5 + 9 + 21 && par2 > var6 + 68 - 32 && par2 < var6 + 68) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.setItemDamage(tile.getMaxLiquid());
			par1ItemStack.stackSize = this.tile.getCurrentTankLevel();
			drawItemStackTooltip(par1ItemStack, par1, par2);
		}
	}
}
