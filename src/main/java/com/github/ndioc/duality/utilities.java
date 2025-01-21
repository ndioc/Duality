package com.github.ndioc.duality;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static com.github.ndioc.duality.main.MOD_ID;

public class utilities {

  public static Item registeritem(String id, Item item) {
    return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
  }

  public static Block registerblock(String blockname, boolean registerblockitem, Block block) {

    //create id/name for block
    Identifier regid = Identifier.of(MOD_ID, blockname);

    if (registerblockitem) {
      BlockItem blockitem = new BlockItem(block, new Item.Settings());
      Registry.register(Registries.ITEM, regid, blockitem);
    }

    return Registry.register(Registries.BLOCK, regid, block);

  }

}