package NetherStuffs.Client;

import net.minecraftforge.client.MinecraftForgeClient;
import NetherStuffs.EntityTorchArrow;
import NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings() {
		MinecraftForgeClient.preloadTexture("/blocks.png");
		MinecraftForgeClient.preloadTexture("/block_detector.png");
		MinecraftForgeClient.preloadTexture("/items.png");
		MinecraftForgeClient.preloadTexture("/furnace.png");
		MinecraftForgeClient.preloadTexture("/puddles.png");
		MinecraftForgeClient.preloadTexture("/soulworkbench.png");
		MinecraftForgeClient.preloadTexture("/souldetector.png");
		MinecraftForgeClient.preloadTexture("/soulfurnace.png");
		MinecraftForgeClient.preloadTexture("/mobbuttons.png");
		RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new RenderTorchArrow());
		//MinecraftForgeClient.registerItemRenderer(NetherStuffs.Items.NetherItems.NetherBow.shiftedIndex, new RenderHeldTorchArrow());
	}
}
