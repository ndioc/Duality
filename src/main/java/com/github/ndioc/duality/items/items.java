package com.github.ndioc.duality.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

import static com.github.ndioc.duality.utilities.registeritem;

public class items {

  public static void initialize() {
  }

  public static final Item ESSENCE_ORB = registeritem("essence_orb",new Item(new Item.Settings().maxCount(1)));

  public static final Item WISP_FRUIT = registeritem("wisp_fruit",new Item(new FabricItemSettings()
          .maxCount(16)
          .food(new FoodComponent.Builder()
                  .hunger(4)
                  .saturationModifier(0.6f)
                  .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,30*20, 1),1f)
                  .build())

  ));
}

