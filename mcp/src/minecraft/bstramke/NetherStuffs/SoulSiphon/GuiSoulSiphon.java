package bstramke.NetherStuffs.SoulSiphon;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;

public class GuiSoulSiphon extends GuiContainer {

	private TileSoulSiphon tile_entity;

	public GuiSoulSiphon(InventoryPlayer player_inventory, TileSoulSiphon tile_entity) {
		super(new ContainerSoulSiphon(tile_entity, player_inventory));
		this.tile_entity = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Siphon Gui", 38, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int var4 = this.mc.renderEngine.getTexture(CommonProxy.SOULSIPHON_PNG);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		// draw fill state from bottom to top
		int nBottomLeftY = var6 + 68;
		int nFillState = this.tile_entity.getFillingScaled(32);
		for (int i = 0; i < 32 && i < nFillState; i++) {
			this.drawTexturedModalRect(var5 + 9, nBottomLeftY - i, 176, 48 - i, 21, 1);
		}

		/*Integer nCurrent = this.tile_entity.getCurrentTankLevel();
		if(nCurrent>=100)
			fontRenderer.drawString(nCurrent.toString(), var5 + 11, var6 + 49, 0x000000);
		else
			fontRenderer.drawString(nCurrent.toString(), var5 + 13, var6 + 49, 0x000000);*/
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		if (par1 > var5 + 9 && par1 < var5 + 9 + 21 && par2 > var6 + 68 - 32 && par2 < var6 + 68) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.stackSize = this.tile_entity.getCurrentTankLevel();
			drawItemStackTooltip(par1ItemStack , par1, par2);
		}
	}
}
