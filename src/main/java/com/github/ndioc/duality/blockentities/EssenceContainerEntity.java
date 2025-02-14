package com.github.ndioc.duality.blockentities;

import com.github.ndioc.duality.main;
import com.github.ndioc.duality.mechanics.essence.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EssenceContainerEntity extends BlockEntity implements essenceContainer {

  essence container;
  boolean firsttick = true;

  public EssenceContainerEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONTAINER, pos, state);
  }

  public essence createEssenceObject(essenceType type, int capacity) {
     return new essence(type, capacity);
  }

  public void deleteEssenceObject() {
    container = null;
  }

  public int transferEssence(BlockPos position, essenceType type, int amount) {
    return 0;
  }

  public int receivedEssence(essenceType type, int amount) {
    return 0;
  }

  public static void tick(World world, BlockPos position, BlockState state, EssenceContainerEntity entity) {
    if (entity.firsttick) {
      entity.container = entity.createEssenceObject(essenceType.HARMONIOUS, 256000);
      main.LOGGER.info("testorb firsttick triggered.");
      entity.firsttick = false;
    }
    main.LOGGER.info("testorb is ticking.");
  }
}
