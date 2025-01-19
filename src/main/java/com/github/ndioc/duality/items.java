package com.github.ndioc.duality;

import net.minecraft.item.Item;

import static com.github.ndioc.duality.utilities.registeritem;

public class items {

  public static void initialize() {
  }

  public static final Item ESSENCE_ORB = registeritem("essence_orb",new Item(new Item.Settings().maxCount(1)));
  
}
