package bstramke.NetherStuffs.Client;

import bstramke.NetherStuffs.Blocks.soulBomb.EntitySoulBombPrimed;
import bstramke.NetherStuffs.Client.Renderers.NetherOreExtendedRenderingHelper;
import bstramke.NetherStuffs.Client.Renderers.NetherOreRenderingHelper;
import bstramke.NetherStuffs.Client.Renderers.RenderSoulBombPrimed;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings() {
		RenderingRegistry.registerBlockHandler(new NetherOreRenderingHelper());
		RenderingRegistry.registerBlockHandler(new NetherOreExtendedRenderingHelper());
		RenderingRegistry.registerEntityRenderingHandler(EntitySoulBombPrimed.class, new RenderSoulBombPrimed());
	}
}
