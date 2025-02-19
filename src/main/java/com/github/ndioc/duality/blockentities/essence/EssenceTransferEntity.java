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
    setConstants();
  }

  public void setConstants() {
    int[][] AiA = essenceBlockConstants.fetchConstants(this.getCachedState().getBlock().getTranslationKey());
    if (AiA != null) {
      amountPerTransfer = AiA[0][0];
      ticksBetweenTransfer = AiA[0][1];
      travelSpeedMult = AiA[0][2];
      distanceBeforeLossMult = AiA[0][3];
      maxSourceContainers = AiA[0][4];
      maxDestinationContainers = AiA[0][5];
    }
  }

  private int amountPerTransfer;
  private int ticksBetweenTransfer;
  private int travelSpeedMult;
  private int distanceBeforeLossMult;
  private int maxSourceContainers;
  private int maxDestinationContainers;

  private final int entityCacheSize = 16;
  private int lastEntityTakenFrom = 0;
  private int lastEntitySentTo = 0;

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

  public void transferEssence(EssenceContainerEntity source, EssenceContainerEntity destination, int amount, essenceType type) {
    int essencetosend = source.removeEssence(type, amount);
    if (essencetosend != 0) {
      int essencetoreturn = destination.addEssence(type, essencetosend);
      if(essencetoreturn != 0) {
        source.addEssence(type, essencetoreturn);
      }
    }
  }

  public void transferEssenceRoundRobin(EssenceContainerEntity[] source, EssenceContainerEntity[] destination, int amount, essenceType type) {
      int essencetosend = 0;
    for (int x = lastEntityTakenFrom + 1, y = lastEntitySentTo + 1, z = 0; z < Math.max(source.length, destination.length); z++) {

      if (essencetosend == 0) {
        essencetosend = source[x].removeEssence(type, amount);
      }

      if (essencetosend != 0) {
        if (destination[y].canReceiveEssence(type)) {
          int essencetoreturn = destination[y].addEssence(type, essencetosend);
          if (essencetoreturn != 0) {
            source[x].addEssence(type, essencetoreturn);
          }
          lastEntityTakenFrom = x;
          lastEntitySentTo = y;
          break;
        }
        else{
          y++;
        }
      } else {
        x++;
      }
    }
  }

  public void transferEssenceToAll(EssenceContainerEntity[] source, EssenceContainerEntity[] destination, int amount, essenceType type) {

  }
}
