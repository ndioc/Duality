package com.github.ndioc.duality.blockentities;

import com.github.ndioc.duality.blockentities.essence.*;
import com.github.ndioc.duality.blockentities.animation.*;
import com.github.ndioc.duality.blocks.blocks;
import com.github.ndioc.duality.utilities;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

public class blockentitytypes {

  public static final BlockEntityType<AnimatedPillarEntity> ANIMATED_PILLAR = utilities.RegisterBlockEntityType("animated_pillar",
      FabricBlockEntityTypeBuilder.create(AnimatedPillarEntity::new, blocks.WISPWOOD_LOG).build());

  public static final BlockEntityType<EssenceContainerEntity> ESSENCE_CONTAINER = utilities.RegisterBlockEntityType("essence_container",
      FabricBlockEntityTypeBuilder.create(EssenceContainerEntity::new, blocks.TEST_ORB).build());

  public static final BlockEntityType<EssenceTransferEntity> ESSENCE_TRANSFER_ENTITY = utilities.RegisterBlockEntityType("essence_transfer_entity",
      FabricBlockEntityTypeBuilder.create(EssenceTransferEntity::new, blocks.TEST_RELAY).build());

  public static void initialize(){
  }

}
