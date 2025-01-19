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

    public static final ItemGroup DUALITY = Registry.register(Registries.ITEM_GROUP, new Identifier(main.MOD_ID,"DUALITY"), FabricItemGroup.builder()

        .icon(() -> new ItemStack(blocks.WISPWOOD_LOG))
        .displayName(Text.translatable("itemgroup.duality.DUALITY"))
        .entries((context, entries) -> {

          // add item to item group: entries.add(class.ITEM_NAME);
            entries.add(blocks.WISPWOOD_LOG);

          }
        )

        .build());

  }

  public static void initialize(){

  }


}
