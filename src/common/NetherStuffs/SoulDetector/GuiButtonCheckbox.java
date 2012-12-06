package NetherStuffs.SoulDetector;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

import NetherStuffs.Common.CommonProxy;

public class GuiButtonCheckbox extends GuiButton {
	private int xButtonOffset;
	private String Label;

	public GuiButtonCheckbox(int par1, int par2, int par3, int par4, int par5, boolean isChecked, String text) {
		super(par1, par2, par3, par4, par5, "");
		Label = text;
		if (isChecked)
			this.xButtonOffset = 11;
		else
			this.xButtonOffset = 0;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (this.drawButton) {
			FontRenderer var4 = par1Minecraft.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture(CommonProxy.SOULDETECTOR_PNG));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int var5 = this.getHoverState(this.field_82253_i);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 177 + this.xButtonOffset, 34 + var5 * 11, 9, 9);
			// this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
			this.mouseDragged(par1Minecraft, par2, par3);
			var4.drawString(this.Label, this.xPosition + 12, this.yPosition+1, 0x000000);
			// int var6 = 14737632;

			/*
			 * if (!this.enabled) { var6 = -6250336; } else if (this.field_82253_i) { var6 = 16777120; }
			 * 
			 * this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var6);
			 */
		}
	}
}
