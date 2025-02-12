package com.github.ndioc.duality.items;

import com.github.ndioc.duality.block.blocks;
import com.github.ndioc.duality.main;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class itemgroups {

  public static class Duality {

    public static final ItemGroup DUALITY = Registry.register(Registries.ITEM_GROUP, new Identifier(main.MOD_ID,"duality_itemgroup"), FabricItemGroup.builder()

        .icon(() -> new ItemStack(blocks.WISPWOOD_LOG))
        .displayName(Text.translatable("itemgroup.duality.duality"))
        .entries((context, entries) -> {

          // add item to item group: entries.add(class.ITEM_NAME);
            entries.add(blocks.WISPWOOD_LOG);
            entries.add(items.ESSENCE_ORB);
            entries.add(items.WISP_FRUIT);

          }
        )

        .build());

    public static void initializegroup(){
    }

  }

  public static void initialize(){
    Duality.initializegroup();
  }


}
