package NetherStuffs.Common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import NetherStuffs.GuiDemonicFurnace;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler  implements IGuiHandler{
	 
   @Override
   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
  
           TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
          
           if(tile_entity instanceof TileDemonicFurnace){
          
                   return new ContainerDemonicFurnace((TileDemonicFurnace) tile_entity, player.inventory);
           }
          
          
           return null;
   }
  
  
   @Override
   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
  
           TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
          
          
           if(tile_entity instanceof TileDemonicFurnace){
  
                   return new GuiDemonicFurnace(player.inventory, (TileDemonicFurnace) tile_entity);
           }
  
   return null;
   }
}
