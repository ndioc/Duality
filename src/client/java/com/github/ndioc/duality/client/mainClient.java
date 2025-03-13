package com.github.ndioc.duality.client;

import com.github.ndioc.duality.client.animation.AnimationBuilder;
import net.fabricmc.api.ClientModInitializer;

import java.util.Random;

public class mainClient implements ClientModInitializer {

  public static Random random = new Random();

  @Override
  public void onInitializeClient() {
    registerRenderLayers.registerLayers();
    AnimationBuilder.build();
    flywheelInstances.initialize();
    clientNetworking.initialize();

  }

}
