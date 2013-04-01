package bstramke.NetherStuffs.SoulCondenser;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.SoulSiphon.TileSoulSiphon;

public class GuiSoulCondenser extends GuiContainer {

	private TileSoulCondenser tile_entity;

	public GuiSoulCondenser(InventoryPlayer player_inventory, TileSoulCondenser tile_entity) {
		super(new ContainerSoulCondenser(tile_entity, player_inventory));
		this.tile_entity = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Condenser", 38, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.SOULCONDENSER_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		// draw fill state from bottom to top
		int nBottomLeftY = var6 + 69;
		int nFillState = this.tile_entity.getFillingScaled(32);
		this.mc.renderEngine.bindTexture("/gui/items.png");
		Icon LiquidIcon = NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0);
		int y = 0;
		for (int x = 16; x <= 32 && x <= nFillState; x+=16) {
			drawTexturedModelRectFromIcon(var5 + 12, nBottomLeftY-x, LiquidIcon, 16, 16);
			y = x;
		}
				
		if(nFillState%16!=0){
			nFillState -= y;
			drawTexturedModelRectFromIcon(var5 + 12, nBottomLeftY-y-nFillState, LiquidIcon, 16, nFillState);
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		if (par1 > var5 + 9 && par1 < var5 + 9 + 21 && par2 > var6 + 68 - 32 && par2 < var6 + 68) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.setItemDamage(tile_entity.maxTankLevel);
			par1ItemStack.stackSize = this.tile_entity.getCurrentTankLevel();
			drawItemStackTooltip(par1ItemStack, par1, par2);
		}
	}

}