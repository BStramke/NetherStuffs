package bstramke.NetherStuffs.Client;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import bstramke.NetherStuffs.EntityTorchArrow;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulEngine.TileSoulEngine;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings() {
		RenderingRegistry.registerBlockHandler(new NetherOreRenderingHelper());
		RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new RenderTorchArrow());
		
		if(NetherStuffs.bBuildcraftAvailable)
		{
			ClientRegistry.bindTileEntitySpecialRenderer(TileSoulEngine.class, new RenderSoulEngine());
			RenderingRegistry.registerBlockHandler(new RenderSoulEngine(CommonProxy.GFXFOLDERPREFIX+"base_wood.png"));
		}
	}
}
