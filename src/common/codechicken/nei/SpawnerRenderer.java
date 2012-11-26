package codechicken.nei;

import net.minecraft.src.Block;
import net.minecraft.src.BossStatus;
import net.minecraft.src.Entity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class SpawnerRenderer implements IItemRenderer
{
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}
	
	public void renderInventoryItem(RenderBlocks render, ItemStack item)
	{
		int meta = item.getItemDamage();
		
		if(meta == 0)
		{
			meta = ItemMobSpawner.idPig;
		}
		String bossName = BossStatus.bossName;
		int bossTimeout = BossStatus.field_82826_b;
		try
		{
			World world = NEIClientUtils.mc().theWorld;
			ItemMobSpawner.loadSpawners(world);
			render.renderBlockAsItem(Block.mobSpawner, 0, 1F);
			GL11.glPushMatrix();
	        
	        Entity entity = ItemMobSpawner.getEntity(meta);
	        if(entity != null)
	        {
	        	entity.setWorld(world);
	            float f1 = 0.4375F;
	            if(entity.getShadowSize() > 1.5)
	            {
	            	f1 = 0.1F;
	            }
	            GL11.glRotatef(world.getWorldTime()*10, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
	            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
	            GL11.glScalef(f1, f1, f1);
	            entity.setLocationAndAngles(0, 0, 0, 0.0F, 0.0F);
	        	RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0);
	        }
	        GL11.glPopMatrix();
	
	        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
	        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
		catch(Exception e)
		{
			if(Tessellator.instance.isDrawing)
				Tessellator.instance.draw();
		}
		BossStatus.bossName = bossName;
		BossStatus.field_82826_b = bossTimeout;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch(type)
		{
			case EQUIPPED:
		        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			case INVENTORY:
			case ENTITY:
				renderInventoryItem((RenderBlocks)data[0], item);
		    break;
		}
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}	
}