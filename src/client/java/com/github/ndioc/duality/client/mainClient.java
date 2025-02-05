package com.github.ndioc.duality.client;

import com.github.ndioc.duality.FlywheelInstances;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class mainClient implements ClientModInitializer {

  public static String MOD_ID = "duality";
  public static String MOD_NAME = "DUALITY";
  public static Logger LOGGER = LogManager.getLogger(MOD_ID);

  @Override
  public void onInitializeClient() {
    FlywheelInstances.initialize();
    clientNetworking.initialize();
  }

}
