package com.github.ndioc.duality.client;

import net.fabricmc.api.ClientModInitializer;

import java.util.Random;

public class mainClient implements ClientModInitializer {

  public static Random random = new Random();

  @Override
  public void onInitializeClient() {
    registerRenderLayers.registerLayers();
    flywheelInstances.initialize();
    clientNetworking.initialize();
  }

}
