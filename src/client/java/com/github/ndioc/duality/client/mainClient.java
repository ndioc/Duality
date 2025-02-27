package com.github.ndioc.duality.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class mainClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    flywheelInstances.initialize();
    clientNetworking.initialize();
  }

}
