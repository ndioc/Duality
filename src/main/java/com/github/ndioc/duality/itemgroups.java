package com.github.ndioc.duality;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class itemgroups {

  public static class Duality {

    public static final ItemGroup DUALITY = Registry.register(Registries.ITEM_GROUP, new Identifier("duality","DUALITY"), FabricItemGroup.builder()

        .icon(() -> new ItemStack(items.CRYSTALLIZED_ESSENCE))
        .displayName(Text.translatable("ItemGroup.duality.DUALITY"))
        .entries((context, entries) -> {

          // add item to item group: entries.add(class.ITEM_NAME);


          }
        )

        .build());

  }

  public static void initialize(){
  }


}
