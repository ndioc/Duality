package com.github.ndioc.duality;

import com.github.ndioc.duality.blockentities.AnimatedPillarEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

public class blockentitytypes {

  public static final BlockEntityType<AnimatedPillarEntity> ANIMATED_PILLAR = utilities.RegisterBlockEntityType("animated_pillar",
      FabricBlockEntityTypeBuilder.create(AnimatedPillarEntity::new, blocks.WISPWOOD_LOG).build());

  public static void initialize(){
  }

}
