package codechicken.core.render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import codechicken.core.render.SpriteSheetManager.SpriteSheet;
import codechicken.core.render.TextureUtils.IIconRegister;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureStitched;
import net.minecraft.client.texturepacks.ITexturePack;

public class TextureSpecial extends TextureStitched implements IIconRegister
{
    //sprite sheet fields
    private int spriteIndex;
    private SpriteSheet spriteSheet;
    
    //textureFX fields
    private TextureFX textureFX;
    
    private int blankSize = -1;
    
    private String textureFile;
    
    private boolean selfRegister;
    
    protected TextureSpecial(String par1)
    {
        super(par1);
    }
    
    public TextureSpecial baseFromSheet(SpriteSheet spriteSheet, int spriteIndex)
    {
        this.spriteSheet = spriteSheet;
        this.spriteIndex = spriteIndex;
        return this;
    }
    
    public TextureSpecial addTextureFX(TextureFX fx)
    {
        textureFX = fx;
        return this;
    }
    
    @Override
    public void func_94218_a(Texture par1Texture, List par2List, int originX, int originY, int width, int height, boolean par7)
    {
        super.func_94218_a(par1Texture, par2List, originX, originY, width, height, par7);
        if(textureFX != null)
            textureFX.onTextureDimensionsUpdate(width, height);
    }
    
    @Override
    public void func_94219_l()
    {
        super.func_94219_l();
        
        if(textureFX != null)
        {
            textureFX.update();
            if(textureFX.changed())
                TextureUtils.write(textureFX.imageData, textureFX.tileSizeBase, textureFX.tileSizeBase, field_94228_a, field_94224_d, field_94225_e);
        }
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean loadTexture(TextureManager manager, ITexturePack texturepack, String name, String fileName, BufferedImage image, ArrayList textures)
    {
        if(spriteSheet != null)
            textures.add(spriteSheet.createSprite(spriteIndex));
        else if(blankSize > 0)
            textures.add(TextureUtils.createTextureObject(name, blankSize, blankSize));
        else if(textureFile != null)
            textures.add(TextureUtils.createTextureObject(textureFile));
        
        if(textureFX != null)
        {
            if(textures.isEmpty())
                throw new RuntimeException("TextureFX with no base sprite: "+name);
            Texture base = (Texture) textures.get(0);
            //add 2 so we get the update call
            textures.add(TextureUtils.createTextureObject(name+"$2", base.func_94275_d(), base.func_94276_e()));
        }
        
        if(!textures.isEmpty())
            return true;
        
        return super.loadTexture(manager, texturepack, name, fileName, image, textures);
    }

    public TextureSpecial blank(int size)
    {
        blankSize = size;
        selfRegister();
        return this;
    }
    
    public TextureSpecial setTextureFile(String fileName)
    {
        textureFile = fileName;
        return this;
    }
    
    public void selfRegister()
    {
        selfRegister = true;
        TextureUtils.addIconRegistrar(this);
    }
    
    @Override
    public void registerIcons(IconRegister register)
    {
        if(selfRegister)
        {
            ((TextureMap)register).setTextureEntry(func_94215_i(), this);
        }
    }
}
