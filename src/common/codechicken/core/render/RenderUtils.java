package codechicken.core.render;

import org.lwjgl.opengl.GL11;

import codechicken.core.Vector3;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.Tessellator;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderUtils
{
	public static int bindLiquidTexture(int liquidID, int liquidMeta)
	{
		if(liquidID < Block.blocksList.length)
		{
	    	Block liquidBlock = Block.blocksList[liquidID];
	    	ForgeHooksClient.bindTexture(liquidBlock.getTextureFile(), 0);
	    	return liquidBlock.getBlockTextureFromSideAndMetadata(0, liquidMeta);
		}
		else
		{
	    	Item liquidItem = Item.itemsList[liquidID];
	    	ForgeHooksClient.bindTexture(liquidItem.getTextureFile(), 0);
	    	return liquidItem.getIconFromDamage(liquidMeta);
		}
	}
	
	public static void renderLiquidQuad(Vector3 point1, Vector3 point2, Vector3 point3, Vector3 point4, int texIndex, double res)
	{
    	double tx1 = (texIndex%16)/16D;
    	double ty1 = (texIndex/16)/16D;
    	double ty2 = ty1+0.0625;
		
		Vector3 wide = point4.copy().subtract(point1);
		Vector3 high = point1.copy().subtract(point2);
		
		double wlen = wide.mag();
		double hlen = high.mag();
		
		double x = 0;
		while(x < wlen)
		{
			double rx = wlen - x;
    		if(rx > res)
    			rx = res;

    		double y = 0;
    		while(y < hlen)
    		{
    			double ry = hlen-y;
    			if(ry > res)
    				ry = res;

    			Vector3 dx1 = wide.copy().multiply(x/wlen);
    			Vector3 dx2 = wide.copy().multiply((x+rx)/wlen);    
    			Vector3 dy1 = high.copy().multiply(y/hlen);    
    			Vector3 dy2 = high.copy().multiply((y+ry)/hlen);

    			Tessellator.instance.addVertexWithUV(point2.x+dx1.x+dy2.x, point2.y+dx1.y+dy2.y, point2.z+dx1.z+dy2.z, tx1, ty2-ry/res*0.0625);
    			Tessellator.instance.addVertexWithUV(point2.x+dx1.x+dy1.x, point2.y+dx1.y+dy1.y, point2.z+dx1.z+dy1.z, tx1, ty2);
    			Tessellator.instance.addVertexWithUV(point2.x+dx2.x+dy1.x, point2.y+dx2.y+dy1.y, point2.z+dx2.z+dy1.z, tx1+rx/res*0.0625, ty2);
    			Tessellator.instance.addVertexWithUV(point2.x+dx2.x+dy2.x, point2.y+dx2.y+dy2.y, point2.z+dx2.z+dy2.z, tx1+rx/res*0.0625, ty2-ry/res*0.0625);
    			
    			y+=ry;
    		}
    		
    		x+=rx;
		}
	}

	public static void disableLightMapTexture()
	{
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public static void enableLightMapTexture()
	{
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
    
	public static void translateToWorldCoords(Entity entity, float frame)
    {       
        double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)frame;
        double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)frame;
        double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)frame;
        
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
    }
}
