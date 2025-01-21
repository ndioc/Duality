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

  public static final BlockEntityType<AnimatedPillarEntity> ANIMATED_PILLAR = register("Animated_Pillar",
      FabricBlockEntityTypeBuilder.create(AnimatedPillarEntity::new, blocks.WISPWOOD_LOG).build());

  public static void initialize(){
  }

}
