package com.github.ndioc.duality.client;

import com.github.ndioc.duality.blocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.render.RenderLayer;

public class mainClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    BlockRenderLayerMap.INSTANCE.putBlock(blocks.WISPWOOD_LOG, RenderLayer.getCutout());

  }

}
