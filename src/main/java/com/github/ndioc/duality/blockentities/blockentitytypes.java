package com.github.ndioc.duality.blockentities;

import com.github.ndioc.duality.blocks;
import com.github.ndioc.duality.main;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class blockentitytypes {
  public static <T extends BlockEntityType<?>> T register(String path, T type) {
    return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(main.MOD_ID, path), type);
  }

  public static final BlockEntityType<animatedpillar> ANIMATED_PILLAR = register("animated_pillar",
      FabricBlockEntityTypeBuilder.create(animatedpillar::new, blocks.WISPWOOD_LOG).build());

  public static void initialize(){
  }

}
