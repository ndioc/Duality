package com.github.ndioc.duality.client;

import com.github.ndioc.duality.client.animation.AnimationBuilder;
import com.github.ndioc.duality.client.models.registerPartialModels;
import net.fabricmc.api.ClientModInitializer;

import java.util.Random;

public class mainClient implements ClientModInitializer {

  public static Random random = new Random();

  @Override
  public void onInitializeClient() {
    registerPartialModels.createArrays();
    registerRenderLayers.registerLayers();
    AnimationBuilder.build();
    FlywheelVisuals.initialize();
    clientNetworking.initialize();

  }

}
