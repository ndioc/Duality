package com.github.ndioc.duality.client;

import com.github.ndioc.duality.blocks.blocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class registerRenderLayers {

  public static void registerLayers() {
    BlockRenderLayerMap.INSTANCE.putBlock(blocks.SELECTION_OUTLINE, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(blocks.ORB_CONTAINER, RenderLayer.getCutout());
  }

}
