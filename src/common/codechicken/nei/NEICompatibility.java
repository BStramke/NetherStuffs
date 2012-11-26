package codechicken.nei;

import java.lang.reflect.*;

import codechicken.core.ReflectionManager;
import static codechicken.core.ReflectionManager.*;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Container;

public class NEICompatibility
{	
    public static boolean callConvenientInventoryHandler(int i, int j, boolean flag, Minecraft minecraft, Container container)
    {
        try
        {
            Class<?> class1 = findClass("ConvenientInventory");
            if(class1 == null)return false;
            Method method = class1.getMethod("mod_convenientInventory_handleClickOnSlot", Integer.TYPE, Integer.TYPE, Boolean.TYPE, net.minecraft.client.Minecraft.class, net.minecraft.src.Container.class);
            method.invoke(null, i, j, flag, minecraft, container);
            return true;
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
        }
        catch(InvocationTargetException invocationtargetexception)
        {
        }
        catch(IllegalAccessException illegalaccessexception)
        {
        }
        return false;
    }
	
	public static boolean hasForge = ReflectionManager.classExists("net.minecraftforge.common.MinecraftForge");
}
