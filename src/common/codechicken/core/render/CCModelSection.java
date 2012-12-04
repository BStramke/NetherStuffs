package codechicken.core.render;

import net.minecraft.src.Tessellator;

public class CCModelSection
{
	double textureWidth;
	double textureHeight;
	int tilesU;
	int tilesV;
	double offU;
	double offV;
	
	public CCModelSection(int width, int height)
	{
		textureWidth = width;
		textureHeight = height;
	}
	
	public CCModelSection(int tileWidth, int tileHeight, int tilesX, int tilesY)
	{
		textureWidth = tileWidth*tilesX;
		textureHeight = tileHeight*tilesY;
		this.tilesU = tilesX;
		this.tilesV = tilesY;
	}
	
	public void setTile(int x, int y)
	{
		offU = textureWidth*x/tilesU;
		offV = textureHeight*y/tilesV;
	}

	public void addVertex(Vertex5 vert)
	{
		Tessellator.instance.addVertexWithUV(vert.vert.x, vert.vert.y, vert.vert.z, (offU+vert.u)/textureWidth, (offV+vert.v)/textureHeight);
	}
}
