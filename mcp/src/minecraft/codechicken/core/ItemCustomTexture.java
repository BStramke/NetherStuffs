package codechicken.core;

import codechicken.core.render.SpriteSheetManager;
import codechicken.core.render.SpriteSheetManager.SpriteSheet;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemCustomTexture extends Item
{
    private int sprite;
    private SpriteSheet spriteSheet;
    
	public ItemCustomTexture(int itemID, int iconIndex, String texturefile)
	{
		super(itemID);
		sprite = iconIndex;
		spriteSheet = SpriteSheetManager.getSheet(texturefile);
	}

	@Override
	public void func_94581_a(IconRegister register)
	{
	    spriteSheet.requestIndicies(sprite);
	    spriteSheet.registerIcons(register);
	    iconIndex = spriteSheet.getSprite(sprite);
	}
}
