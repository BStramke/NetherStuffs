package NetherStuffs.Client;

import java.util.Random;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBox;
import net.minecraft.src.ModelRenderer;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import NetherStuffs.EntityTorchArrow;

public class RenderHeldTorchArrow implements IItemRenderer {
	protected ModelBase mainModel;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (type == ItemRenderType.EQUIPPED)
			return true;
		else
			return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == ItemRenderType.EQUIPPED) {

			RenderBlocks render = (RenderBlocks) data[0];
			EntityLiving par1EntityLiving = (EntityLiving) data[1];

			int var3 = par1EntityLiving.func_85035_bI();

			if (var3 > 0) {
				EntityTorchArrow var4 = new EntityTorchArrow(par1EntityLiving.worldObj, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
				Random var5 = new Random((long) par1EntityLiving.entityId);
				RenderHelper.disableStandardItemLighting();

				for (int var6 = 0; var6 < var3; ++var6) {
					GL11.glPushMatrix();
					ModelRenderer var7 = this.mainModel.func_85181_a(var5);
					ModelBox var8 = (ModelBox) var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
					var7.postRender(0.0625F);
					float var9 = var5.nextFloat();
					float var10 = var5.nextFloat();
					float var11 = var5.nextFloat();
					float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0F;
					float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0F;
					float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0F;
					GL11.glTranslatef(var12, var13, var14);
					var9 = var9 * 2.0F - 1.0F;
					var10 = var10 * 2.0F - 1.0F;
					var11 = var11 * 2.0F - 1.0F;
					var9 *= -1.0F;
					var10 *= -1.0F;
					var11 *= -1.0F;
					float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
					var4.prevRotationYaw = var4.rotationYaw = (float) (Math.atan2((double) var9, (double) var11) * 180.0D / Math.PI);
					var4.prevRotationPitch = var4.rotationPitch = (float) (Math.atan2((double) var10, (double) var15) * 180.0D / Math.PI);
					double var16 = 0.0D;
					double var18 = 0.0D;
					double var20 = 0.0D;
					float var22 = 0.0F;
					RenderManager.instance.renderEntityWithPosYaw(var4, var16, var18, var20, var22, 5);
					GL11.glPopMatrix();
				}

				RenderHelper.enableStandardItemLighting();
			}
		}
	}
}
