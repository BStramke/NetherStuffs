package NetherStuffs.Client;

import net.minecraftforge.client.MinecraftForgeClient;
import NetherStuffs.Common.CommonProxyNetherStuffs;

public class ClientProxy extends CommonProxyNetherStuffs{
	@Override
	public void registerRenderThings(){
		MinecraftForgeClient.preloadTexture("/blocks.png");
		MinecraftForgeClient.preloadTexture("/items.png");
	}
}
