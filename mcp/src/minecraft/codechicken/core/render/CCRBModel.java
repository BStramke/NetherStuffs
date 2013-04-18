package codechicken.core.render;

import java.util.Arrays;

import codechicken.core.vec.Rotation;
import codechicken.core.vec.Vector3;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class CCRBModel extends CCModel
{
    public static class LC
    {
        public LC(int s, float e, float f, float g, float h)
        {
            side = s;
            fa = e;
            fb = f;
            fc = g;
            fd = h;
            ia = (int)(1/fa);
            ib = (int)(1/fb);
            ic = (int)(1/fc);
            id = (int)(1/fd);
        }
        
        public int side;
        public float fa;
        public float fb;
        public float fc;
        public float fd;
        public int ia;
        public int ib;
        public int ic;
        public int id;
    }
    
    public static class LightMatrix
    {
        public float[][] ao = new float[6][4];
        public int[][] brightness = new int[6][4];
        
        private float[] aSamples = new float[27];
        private int[] bSamples = new int[27];
        
        public static final int[][] ssamplem = new int[][]{//the 9 positions in the sample array for each side, looping through s+5 then s+3 axes
            { 0, 1, 2, 3, 4, 5, 6, 7, 8},
            {18,19,20,21,22,23,24,25,26},
            { 0, 9,18, 1,10,19, 2,11,20},
            { 6,15,24, 7,16,25, 8,17,26},
            { 0, 3, 6, 9,12,15,18,21,24},
            { 2, 5, 8,11,14,17,20,23,26}};
        public static final int[][] qsamplem = new int[][]{//the positions in the side sample array for each corner
            {0,1,3,4},
            {1,2,4,5},
            {3,4,6,7},
            {4,5,7,8}};
        
        static
        {
            int[][] os = new int[][]{
                    {0,-1,0},
                    {0, 1,0},
                    {0,0,-1},
                    {0,0, 1},
                    {-1,0,0},
                    { 1,0,0}};
            
            for(int s = 0; s < 6; s++)
            {
                int[] d0 = new int[]{os[s][0]+1, os[s][1]+1, os[s][2]+1};
                int[] d1 = os[((s&6)+3)%6];
                int[] d2 = os[((s&6)+5)%6];
                for(int a = -1; a <= 1; a++)
                    for(int b = -1; b <= 1; b++)
                        ssamplem[s][(a+1)*3+b+1] = (d0[1]+d1[1]*a+d2[1]*b)*9+(d0[2]+d1[2]*a+d2[2]*b)*3+(d0[0]+d1[0]*a+d2[0]*b);
            }
            System.out.println(Arrays.deepToString(ssamplem));
        }
        
        public void computeAt(IBlockAccess a, int x, int y, int z)
        {
            //inc x, inc z, inc y
            sample( 0, aSamples, bSamples, a, x-1, y-1, z-1);
            sample( 1, aSamples, bSamples, a, x  , y-1, z-1);
            sample( 2, aSamples, bSamples, a, x+1, y-1, z-1);
            sample( 3, aSamples, bSamples, a, x-1, y-1, z  );
            sample( 4, aSamples, bSamples, a, x  , y-1, z  );
            sample( 5, aSamples, bSamples, a, x+1, y-1, z  );
            sample( 6, aSamples, bSamples, a, x-1, y-1, z+1);
            sample( 7, aSamples, bSamples, a, x  , y-1, z+1);
            sample( 8, aSamples, bSamples, a, x+1, y-1, z+1);
            sample( 9, aSamples, bSamples, a, x-1, y  , z-1);
            sample(10, aSamples, bSamples, a, x  , y  , z-1);
            sample(11, aSamples, bSamples, a, x+1, y  , z-1);
            sample(12, aSamples, bSamples, a, x-1, y  , z  );
            
            sample(14, aSamples, bSamples, a, x+1, y  , z  );
            sample(15, aSamples, bSamples, a, x-1, y  , z+1);
            sample(16, aSamples, bSamples, a, x  , y  , z+1);
            sample(17, aSamples, bSamples, a, x+1, y  , z+1);
            sample(18, aSamples, bSamples, a, x-1, y+1, z-1);
            sample(19, aSamples, bSamples, a, x  , y+1, z-1);
            sample(20, aSamples, bSamples, a, x+1, y+1, z-1);
            sample(21, aSamples, bSamples, a, x-1, y+1, z  );
            sample(22, aSamples, bSamples, a, x  , y+1, z  );
            sample(23, aSamples, bSamples, a, x+1, y+1, z  );
            sample(24, aSamples, bSamples, a, x-1, y+1, z+1);
            sample(25, aSamples, bSamples, a, x  , y+1, z+1);
            sample(26, aSamples, bSamples, a, x+1, y+1, z+1);

            for(int s = 0; s < 6; s++)
            {
                int[] ssample = ssamplem[s];
                for(int q = 0; q < 4; q++)
                {
                    int[] qsample = qsamplem[q];
                    if(Minecraft.isAmbientOcclusionEnabled())
                        interp(s, q, ssample[qsample[0]], ssample[qsample[1]], ssample[qsample[2]], ssample[qsample[3]]);
                    else
                        interp(s, q, ssample[4], ssample[4], ssample[4], ssample[4]);
                }
            }
                    
        }

        private void sample(int i, float[] aSamples, int[] bSamples, IBlockAccess a, int x, int y, int z)
        {
            int bid = a.getBlockId(x, y, z);
            Block b = Block.blocksList[bid];
            bSamples[i] = a.getLightBrightnessForSkyBlocks(x, y, z, b.getLightValue(a, x, y, z));
            aSamples[i] = b.getAmbientOcclusionLightValue(a, x, y, z);
        }
        
        private void interp(int s, int q, int a, int b, int c, int d)
        {
            ao[s][q] = interpAO(aSamples[a], aSamples[b], aSamples[c], aSamples[d]);
            brightness[s][q] = interpBrightness(bSamples[a], bSamples[b], bSamples[c], bSamples[d]);
        }
        
        public static float interpAO(float a, float b, float c, float d)
        {
            return (a+b+c+d)/4F;
        }
        
        public static int interpBrightness(int a, int b, int c, int d)
        {
            return (a+b+c+d)>>2 & 0xFF00FF;
        }
    }
    
    public LC[] lightCoefficents;
    private LightMatrix lightMatrix;
    
    /**
     * Lighting sides and coefficients are computed for each vertex of the model. All faces must be axis planar and all verts are assumed to be in the range (0,0,0) to (1,1,1)
     */
    protected CCRBModel(CCModel m)
    {
        super(m.vertexMode);
        verts = new Vertex5[m.verts.length];
        copy(m, 0, this, 0, m.verts.length);
        if(normals == null)
            computeNormals();
        if(colours == null)
            setColour(-1);
        computeLighting();
    }

    private void computeLighting()
    {
        lightCoefficents = new LC[verts.length];
        for(int k = 0; k < verts.length; k++)
        {
            Vertex5 vert = verts[k];
            Vector3 normal = normals[k];
            int s = findSide(normal);
            Vector3 v1 = Rotation.axes[((s&6)+3)%6];
            Vector3 v2 = Rotation.axes[((s&6)+5)%6];
            float d1 = (float) vert.vec.scalarProject(v1);
            float d2 = 1-d1;
            float d3 = (float) vert.vec.scalarProject(v2);
            float d4 = 1-d3;
            lightCoefficents[k] = new LC(s, d1*d3, d2*d3, d1*d4, d2*d4);
        }
    }

    private int findSide(Vector3 normal)
    {
        for(int s = 0; s < 6; s++)
            if(normal.equalsT(Rotation.axes[s]))
                return s;
        throw new IllegalArgumentException("Non axial vertex normal "+normal);
    }
    
    public CCRBModel setLightMatrix(LightMatrix m)
    {
        lightMatrix = m;
        return this;
    }
    
    @Override
    public int getColour(int i)
    {
        if(lightMatrix == null)
            return super.getColour(i);
        LC lc = lightCoefficents[i];
        float[] ao = lightMatrix.ao[lc.side];
        float f = LightMatrix.interpAO(ao[0]*lc.fa, ao[1]*lc.fb, ao[2]*lc.fc, ao[3]*lc.fd);
        int c = super.getColour(i);
        return (c&0xFFFFFF00)/(int)(1/f)|c&0xFF;
    }
    
    @Override
    public void applyVertexModifiers(Tessellator tess, int i)
    {
        LC lc = lightCoefficents[i];
        int[] b = lightMatrix.brightness[lc.side];
        tess.setBrightness(LightMatrix.interpBrightness(b[0]/lc.ia, b[1]/lc.ib, b[2]/lc.ic, b[3]/lc.id));
    }
}
