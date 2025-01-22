package com.github.ndioc.duality.client;

import com.github.ndioc.duality.FlywheelInstances;
import net.fabricmc.api.ClientModInitializer;

public class mainClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    FlywheelInstances.initialize();
  }

}
