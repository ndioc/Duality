package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.main;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import com.github.ndioc.duality.mechanics.essence.*;
import net.minecraft.util.math.BlockPos;

public class EssenceTransferEntity extends BlockEntity implements essenceTransfer {

  public EssenceTransferEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_TRANSFER_ENTITY, pos, state);
  }

  private final int entityCacheSize = 16;
  private final int maxSourceContainers = 3;
  private final int maxDestinationContainers = 5;

  // TEMPORARY SETTINGS TO SHOW LINUS AND GET SOME FEEDBACK.

  private final boolean SendToAllAtOnce = true;



  private EssenceContainerEntity[] cachedEntities = new EssenceContainerEntity[entityCacheSize];
  private BlockPos[] cachedEntityPos = new BlockPos[entityCacheSize];

  private BlockPos[] sourceContainers = new BlockPos[maxSourceContainers];
  private BlockPos[] destinationContainers = new BlockPos[maxDestinationContainers];

  public EssenceContainerEntity checkCacheForTarget(BlockPos position) {
    for (int x = 0; x < entityCacheSize; x++) {
      if (position == cachedEntityPos[x]) {
        return cachedEntities[x];
      }
    }
    return getEntity(position);
  }

  public void writeToCache(EssenceContainerEntity entity, BlockPos pos) {
    for (int x = 0; x < entityCacheSize; x++) {
      if (cachedEntities[x] == null) {
        cachedEntities[x] = entity;
        cachedEntityPos[x] = pos;
        break;
      }
      else if (x == entityCacheSize - 1) {
        main.LOGGER.warn("ENTITY CACHE OF BLOCK @ {} IS FULL. THAT'S BAD :)", pos.toShortString());
      }
    }
  }

  public EssenceContainerEntity getEntity(BlockPos position) {
    if (world != null) {
      BlockEntity entitytocheck = world.getBlockEntity(position);
      if (entitytocheck != null && entitytocheck.getType() == blockentitytypes.ESSENCE_CONTAINER) {
        writeToCache((EssenceContainerEntity) entitytocheck, position);
        return (EssenceContainerEntity) entitytocheck;
      }
    }
    return null;
  }

  public void transferEssence(EssenceContainerEntity source, EssenceContainerEntity target, int amount, essenceType type) {

  }

  public void transferEssenceToAll(EssenceContainerEntity[] sources, EssenceContainerEntity[] destinations, int amount, essenceType type) {

  }
}
