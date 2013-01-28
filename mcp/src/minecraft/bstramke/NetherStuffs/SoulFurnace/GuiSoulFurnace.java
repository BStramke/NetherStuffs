package bstramke.NetherStuffs.SoulFurnace;

import javax.swing.Renderer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.Items.SoulEnergyLiquidItem;

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

		int var7 = this.inventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 0, var7 + 1, 16);

		int nBottomLeftY = var6 + 69;
		this.mc.renderEngine.bindTexture(mc.renderEngine.getTexture(NetherItems.SoulEnergyLiquidItem.getTextureFile()));
		int nFillState = this.inventory.getFillingScaled(32);
		int y = 0;
		for (int x = 16; x <= 32 && x <= nFillState; x+=16) {
			this.drawTexturedModalRect(var5 + 12, nBottomLeftY-x, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)%16)*16, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)/16)*16, 16, 16);
			y = x;
		}
		
		if(nFillState%16!=0){
			nFillState -= y;
			this.drawTexturedModalRect(var5 + 12, nBottomLeftY-y-nFillState, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)%16)*16, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)/16)*16, 16, nFillState);
		}
		
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		if (par1 > var5 + 9 && par1 < var5 + 9 + 21 && par2 > var6 + 68 - 32 && par2 < var6 + 68) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.stackSize = this.inventory.getCurrentTankLevel();
			par1ItemStack.setItemDamage(this.inventory.maxTankLevel);
			drawItemStackTooltip(par1ItemStack , par1, par2);
		}
	}
}
