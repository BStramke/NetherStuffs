package bstramke.NetherStuffs.Client;

import net.minecraftforge.client.MinecraftForgeClient;
import bstramke.NetherStuffs.EntityTorchArrow;
import bstramke.NetherStuffs.Common.CommonProxy;
import buildcraft.BuildCraftCore;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings() {
		/*MinecraftForgeClient.preloadTexture(CommonProxy.BLOCKS_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.BLOCKDETECTOR_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.ITEMS_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.FURNANCE_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.PUDDLES_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.SOULWORKBENCH_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.SOULDETECTOR_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.SOULFURNACE_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.MOBBUTTONS_PNG);
		MinecraftForgeClient.preloadTexture(CommonProxy.SOULSIPHON_PNG);*/
		RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new RenderTorchArrow());
//		TextureFXManager.instance().addAnimation(new TextureSoulLiquidFX());
				
		BuildCraftCore.blockByEntityModel = RenderingRegistry.getNextAvailableRenderId();
		
		//MinecraftForgeClient.registerItemRenderer(NetherStuffs.Items.NetherItems.NetherBow.shiftedIndex, new RenderHeldTorchArrow());
	}
}
