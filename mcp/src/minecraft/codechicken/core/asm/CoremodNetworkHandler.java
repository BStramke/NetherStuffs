package codechicken.core.asm;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkModHandler;

public class CoremodNetworkHandler extends NetworkModHandler
{
    public CoremodNetworkHandler(ModContainer container, NetworkMod modAnnotation)
    {
        super(container, modAnnotation);
    }
}
