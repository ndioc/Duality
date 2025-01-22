package com.github.ndioc.duality;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

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

  public static <T extends BlockEntityType<?>> T RegisterBlockEntityType(String path, T type) {
    return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(main.MOD_ID, path), type);
  }

  @Nullable
  public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
    return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
  }

}