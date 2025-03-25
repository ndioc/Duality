package com.github.ndioc.duality.client;

import com.github.ndioc.duality.client.animation.AnimationBuilder;
import com.github.ndioc.duality.client.models.PartialModels;
import net.fabricmc.api.ClientModInitializer;

import java.util.Random;

public class DualityClient implements ClientModInitializer {

  public static Random random = new Random();

  @Override
  public void onInitializeClient() {
    PartialModels.fetch();
    registerRenderLayers.registerLayers();
    AnimationBuilder.build();
    clientNetworking.initialize();
    FlywheelVisuals.initialize();
  }



}
