package com.github.ndioc.duality.blockentities;

import com.github.ndioc.duality.blocks;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.utilities;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class blockentitytypes {

  public static final BlockEntityType<AnimatedPillarEntity> ANIMATED_PILLAR = utilities.RegisterBlockEntityType("animated_pillar",
      FabricBlockEntityTypeBuilder.create(AnimatedPillarEntity::new, blocks.WISPWOOD_LOG).build());

  public static void initialize(){
  }

}
