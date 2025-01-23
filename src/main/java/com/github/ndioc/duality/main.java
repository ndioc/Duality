package com.github.ndioc.duality;

import com.github.ndioc.duality.blockentitytypes.blockentitytypes;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class main implements ModInitializer {

  public static String MOD_ID = "duality";
  public static String MOD_NAME = "DUALITY";
  public static Logger LOGGER = LogManager.getLogger(MOD_ID);

  @Override
  public void onInitialize() {

    itemgroups.initialize();
    potions.initialize();
    blocks.initialize();
    blockentitytypes.initialize();
    fluids.initialize();
    equipment.initialize();
    items.initialize();
    enchantments.initialize();
    recipes.initialize();

    LOGGER.info(MOD_NAME + " By ndioc has been Successfully Loaded.");
  }
}
