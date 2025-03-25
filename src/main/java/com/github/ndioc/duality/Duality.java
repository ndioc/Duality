package com.github.ndioc.duality;

import com.github.ndioc.duality.blocks.blocks;
import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.items.equipment.equipment;
import com.github.ndioc.duality.items.itemgroups;
import com.github.ndioc.duality.items.items;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Duality implements ModInitializer {

  public static final String MOD_ID = "duality";
  public static final String MOD_NAME = "DUALITY";
  public static final String CREATORS = "ndioc & TheLoaf55";
  public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

  @Override
  public void onInitialize() {

    itemgroups.initialize();
    blockentitytypes.initialize();
    blocks.initialize();
    fluids.initialize();
    potions.initialize();
    equipment.initialize();
    items.initialize();
    enchantments.initialize();
    recipes.initialize();

    LOGGER.info("{} By {} has been Successfully Loaded.", MOD_NAME, CREATORS);
  }

  public static Identifier asResourcePath(String path) {
    return new Identifier(MOD_ID, path);
  }

}
