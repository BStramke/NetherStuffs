package bstramke.NetherStuffs.Client;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import bstramke.NetherStuffs.EntityTorchArrow;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulEngine.TileEngine;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings() {

		RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new RenderTorchArrow());
	}
}
