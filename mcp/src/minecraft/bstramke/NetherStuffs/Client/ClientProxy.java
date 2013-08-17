package bstramke.NetherStuffs.Client;

import bstramke.NetherStuffs.Blocks.soulBomb.EntitySoulBombPrimed;
import bstramke.NetherStuffs.Client.Renderers.NetherOreRenderingHelper;
import bstramke.NetherStuffs.Client.Renderers.RenderSoulBombPrimed;
import bstramke.NetherStuffs.Client.Renderers.RenderTorchArrow;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.EntityTorchArrow;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings() {
		RenderingRegistry.registerBlockHandler(new NetherOreRenderingHelper());
		RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new RenderTorchArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntitySoulBombPrimed.class, new RenderSoulBombPrimed());
	}
}
