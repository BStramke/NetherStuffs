package bstramke.NetherStuffs.Client;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.soulBomb.EntitySoulBombPrimed;
import bstramke.NetherStuffs.Blocks.soulEngine.TileSoulEngine;
import bstramke.NetherStuffs.Client.Renderers.FluidRender;
import bstramke.NetherStuffs.Client.Renderers.NetherOreRenderingHelper;
import bstramke.NetherStuffs.Client.Renderers.RenderSoulBombPrimed;
import bstramke.NetherStuffs.Client.Renderers.RenderSoulEngine;
import bstramke.NetherStuffs.Client.Renderers.RenderTorchArrow;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.EntityTorchArrow;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings() {
		RenderingRegistry.registerBlockHandler(new NetherOreRenderingHelper());		
		RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new RenderTorchArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntitySoulBombPrimed.class, new RenderSoulBombPrimed());
		RenderingRegistry.registerBlockHandler(new FluidRender());
		
		if(NetherStuffs.bBuildcraftAvailable)
		{
			ClientRegistry.bindTileEntitySpecialRenderer(TileSoulEngine.class, new RenderSoulEngine());
			RenderingRegistry.registerBlockHandler(new RenderSoulEngine(CommonProxy.GFXFOLDERPREFIX+"base_soulengine.png"));
		}
	}
}
