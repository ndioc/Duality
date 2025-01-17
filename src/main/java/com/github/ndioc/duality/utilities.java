package com.github.ndioc.duality;


import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.github.ndioc.duality.main.MOD_ID;

public class utilities {

  public static Item registeritem(String id, Item item){
    return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
  }

}
