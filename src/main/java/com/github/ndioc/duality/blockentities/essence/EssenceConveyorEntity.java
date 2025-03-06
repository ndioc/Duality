package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceConveyorConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import com.github.ndioc.duality.mechanics.essence.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import static com.github.ndioc.duality.mechanics.essence.objects.EssenceConveyorConstants.fetchConveyorConstants;
import static com.github.ndioc.duality.util.nbt.BlockPosNBT.*;

public class EssenceConveyorEntity extends BlockEntity implements EssenceConveyor {

  public EssenceConveyorEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_TRANSFER_ENTITY, pos, state);
    setConstants();
    sourceContainers = new BlockPos[maxSourceContainers];
    destinationContainers = new BlockPos[maxDestinationContainers];
  }


  @Override
  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    nbt.putInt("maxSources", maxSourceContainers);
    nbt.putInt("maxDestinations", maxDestinationContainers);
    writeBlockPosNBT(sourceContainers, nbt, "Sources");
    writeBlockPosNBT(destinationContainers, nbt, "Destinations");
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    maxSourceContainers = nbt.getInt("maxSources");
    sourceContainers = new BlockPos[maxSourceContainers];
    sourceContainers = readBlockPosNBT(nbt, "Sources");

    maxDestinationContainers = nbt.getInt("maxDestinations");
    destinationContainers = new BlockPos[maxDestinationContainers];
    destinationContainers = readBlockPosNBT(nbt, "Destinations");
  }

  public void writeSelection(NbtCompound nbt) {
    sourceContainers = readBlockPosNBT(nbt, "Sources");
    destinationContainers = readBlockPosNBT(nbt, "Destinations");
  }

  public void setConstants() {
    EssenceConveyorConstants constants = fetchConveyorConstants(this.getCachedState().getBlock().getTranslationKey());
    if (constants != null) {
      amountPerTransfer = constants.getAmountPerTransfer();
      ticksBetweenTransfer = constants.getTicksBetweenTransfers();
      travelSpeedMult = constants.getTravelSpeedMult();
      distanceBeforeLossMult = constants.getDistanceBeforeLossMult();
      maxSourceContainers = constants.getMaxSources();
      maxDestinationContainers = constants.getMaxDestinations();
    }
  }

  private int amountPerTransfer;
  private int ticksBetweenTransfer;
  private float travelSpeedMult;
  private float distanceBeforeLossMult;
  private int maxSourceContainers;
  private int maxDestinationContainers;

  private final int entityCacheSize = 16;
  private int lastEntityTakenFrom = 0;
  private int lastEntitySentTo = 0;

  private EssenceContainerEntity[] cachedEntities = new EssenceContainerEntity[entityCacheSize];
  private BlockPos[] cachedEntityPos = new BlockPos[entityCacheSize];

  private BlockPos[] sourceContainers = new BlockPos[maxSourceContainers];
  private BlockPos[] destinationContainers = new BlockPos[maxDestinationContainers];

  public EssenceContainerEntity checkCacheForTarget(BlockPos position) {
    for (int x = 0; x < entityCacheSize; x++) {
      if (position == cachedEntityPos[x]) {
        try{
          if (cachedEntities[x].getPos() == position) {
            return cachedEntities[x];
          }
        } catch (Exception e) {
          cachedEntities[x] = null;
          cachedEntityPos[x] = null;
          throw new NullPointerException("getPos() of block @ " + position.toShortString() + "returned NULL");
        }
      }
    }
    return getEntity(position);
  }

  public void wipeCache() {
    for (int x = 0; x < cachedEntityPos.length - 1; x++) {
      cachedEntities[x] = null;
      cachedEntityPos[x] = null;
    }
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

  public void transferEssence(EssenceContainerEntity source, EssenceContainerEntity destination, int amount, EssenceType type) {
    int essencetosend = source.removeEssence(type, amount);
    if (essencetosend != 0) {
      int essencetoreturn = destination.addEssence(type, essencetosend);
      if(essencetoreturn != 0) {
        source.addEssence(type, essencetoreturn);
      }
    }
  }

  public void transferEssenceRoundRobin(EssenceContainerEntity[] source, EssenceContainerEntity[] destination, int amount, EssenceType type) {
      int essencetosend = 0;
    for (int x = lastEntityTakenFrom + 1, y = lastEntitySentTo + 1, z = 0; z < Math.max(source.length, destination.length); z++) {

      if (essencetosend == 0) {
        try {
          essencetosend = source[x].removeEssence(type, amount);
        }
        catch (Exception e) {
          throw new RuntimeException("Essence Transfer: Can't remove essence from container");
        }
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

  public void transferEssenceToAll(EssenceContainerEntity[] source, EssenceContainerEntity[] destination, int amount, EssenceType type) {

  }

  public static void tick(World world, BlockPos blockPos, BlockState blockState, EssenceConveyorEntity essenceTransferEntity) {
  }

}
