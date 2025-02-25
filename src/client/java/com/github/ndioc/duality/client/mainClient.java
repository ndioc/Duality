package com.github.ndioc.duality.client;

import com.github.ndioc.duality.client.models.registerModels;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class mainClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ModelLoadingPlugin.register(new registerModels());
    flywheelInstances.initialize();
    clientNetworking.initialize();
  }

}
