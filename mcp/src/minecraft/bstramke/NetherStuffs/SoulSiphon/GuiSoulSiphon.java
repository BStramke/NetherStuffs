package bstramke.NetherStuffs.SoulSiphon;

import java.awt.Point;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.SoulSiphon;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.Gui.GuiContainerSoulTank;
import bstramke.NetherStuffs.Items.NetherItems;

public class GuiSoulSiphon extends GuiContainerSoulTank {

	private TileSoulSiphon tile_entity;

	public GuiSoulSiphon(InventoryPlayer player_inventory, TileSoulSiphon tile_entity) {
		super(tile_entity, new ContainerSoulSiphon(tile_entity, player_inventory), new Point(12,37), new Point(28,69));
		this.tile_entity = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		switch (tile_entity.blockMetadata) {
		case SoulSiphon.mk1:
			this.fontRenderer.drawString("Soul Siphon MK 1", 38, 6, 4210752);
			break;
		case SoulSiphon.mk2:
			this.fontRenderer.drawString("Soul Siphon MK 2", 38, 6, 4210752);
			break;
		case SoulSiphon.mk3:
			this.fontRenderer.drawString("Soul Siphon MK 3", 38, 6, 4210752);
			break;
		case SoulSiphon.mk4:
			this.fontRenderer.drawString("Soul Siphon MK 4", 38, 6, 4210752);
			break;
		}

		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.SOULSIPHON_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		
		drawTankScale();
	}
}
