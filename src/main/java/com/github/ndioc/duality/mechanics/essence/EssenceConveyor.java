package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.blockentities.essence.EssenceConveyorEntity;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.mechanics.essence.objects.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static com.github.ndioc.duality.util.nbt.BlockPosNBT.*;

public interface EssenceConveyor {

  QueuedTransfer[] getEssenceTransferQueue();
  void setEssenceTransferQueue(QueuedTransfer[] queuedTransfer);

  EssenceContainerEntity[] getSources();
  void setSources(EssenceContainerEntity[] sources);

  BlockPos[] getSourcePos();
  void setSourcePos(BlockPos[] sourcePos);

  BlockPos[] getDestinationPos();
  void setDestinationPos(BlockPos[] destinationPos);

  EssenceContainerEntity[] getDestinations();
  void setDestinations(EssenceContainerEntity[] destinations);

  int[] getSourceTimeCache();
  void setSourceTimeCache(int[] sourceTimeCache);

  int[] getDestinationTimeCache();
  void setDestinationTimeCache(int[] destinationTimeCache);

  int[] getSourceLossCache();
  void setSourceLossCache(int[] sourceLossCache);

  int[] getDestinationLossCache();
  void setDestinationLossCache(int[] destinationLossCache);

  int[] getLastTransferIndex();
  void setLastTransferIndex(int[] lastTransferIndex);

  default void writeEntitiesToNBT(NbtCompound nbt) {
    writeBlockPosNBT(getBlockPosArrayFromEntityArray(getSources()), nbt, "Sources");
    writeBlockPosNBT(getBlockPosArrayFromEntityArray(getDestinations()), nbt, "Destinations");
  }

  default void readBlockPosFromNBT(NbtCompound nbt) {
    setSourcePos(readBlockPosNBT(nbt, "Sources"));
    setDestinationPos(readBlockPosNBT(nbt, "Destinations"));
  }

  default void fetchEntities(World world) {
    setSources(getEntityArrayFromBlockPosArray(getSourcePos(), world));
    setDestinations(getEntityArrayFromBlockPosArray(getDestinationPos(), world));
  }

  default BlockPos[] getBlockPosArrayFromEntityArray(EssenceContainerEntity[] entityArray) {
    BlockPos[] posArray = new BlockPos[entityArray.length];
    for (int x = 0; x < entityArray.length; x++) {
      if (entityArray[x] != null) {
        posArray[x] = entityArray[x].getPos();
      }
      else {
        posArray[x] = BlockPosNull();
      }
    }
    return posArray;
  }

  default EssenceContainerEntity[] getEntityArrayFromBlockPosArray(BlockPos[] posArray, World world) {
    EssenceContainerEntity[] entityArray = new EssenceContainerEntity[posArray.length];
    for (int x = 0; x < posArray.length; x++) { // WORLD IS NULL HERE ON LOAD SO IT CANT FETCH THE BLOCK ENTITIES
      if (!posArray[x].equals(BlockPosNull())) {
        BlockEntity entityToAdd = world.getBlockEntity(posArray[x]);
        if (entityToAdd instanceof EssenceContainer) {
          entityArray[x] = (EssenceContainerEntity) entityToAdd;
        }
      }
    }
    return entityArray;
  }

  default EssenceContainerEntity[] checkAndWriteEntityToFirstEmpty(BlockPos position, EssenceContainerEntity[] array, World world) {
    for (int x = 0; x < array.length; x++) {
      if (array[x] == null) {
        BlockEntity toCheck = world.getBlockEntity(position);
        if (toCheck instanceof EssenceContainer) {
          array[x] = (EssenceContainerEntity) toCheck;
          return array;
        }
      }
    }
    return array;
  }

  default boolean checkEntity(EssenceContainerEntity entity) {
    if (entity != null) {
      try {
        entity.getEssenceArray();
        return true;
      } catch (NullPointerException ignored) {
        return false;
      }
    }
    return false;
  }

  default void writeTargets(NbtCompound nbt, EssenceConveyorConstants constants, World world) {
    if (nbt == null) {
      return;
    }

    BlockPos[] sourcePos = readBlockPosNBT(nbt, "Sources");
    BlockPos[] destinationPos = readBlockPosNBT(nbt, "Destinations");

    setSourcePos(sourcePos);
    setDestinationPos(destinationPos);

    EssenceContainerEntity[] sources = new EssenceContainerEntity[constants.getMaxSources()];
    EssenceContainerEntity[] destinations = new EssenceContainerEntity[constants.getMaxDestinations()];

    for (BlockPos position : sourcePos) {
      if (!position.equals(BlockPosNull())) {
        sources = checkAndWriteEntityToFirstEmpty(position, sources, world);
      }
    }

    for (BlockPos position : destinationPos) {
      if (!position.equals(BlockPosNull())) {
        destinations = checkAndWriteEntityToFirstEmpty(position, destinations, world);
      }
    }

    setSources(sources);
    setDestinations(destinations);
  }

  default void deleteEntityFromSourceCache(int index) {
    EssenceContainerEntity[] sources = getSources();
    int[] sourceTimeCache = getSourceTimeCache();
    int[] sourceLossCache = getSourceLossCache();
    sources[index] = null;
    sourceTimeCache[index] = Integer.MIN_VALUE;
    sourceLossCache[index] = Integer.MIN_VALUE;
    setSources(sources);
    setSourceTimeCache(sourceTimeCache);
    setSourceLossCache(sourceLossCache);
  }

  default void deleteEntityFromDestinationCache(int index) {
    EssenceContainerEntity[] destinations = getDestinations();
    int[] destinationTimeCache = getDestinationTimeCache();
    int[] destinationLossCache = getDestinationLossCache();
    destinations[index] = null;
    destinationTimeCache[index] = Integer.MIN_VALUE;
    destinationLossCache[index] = Integer.MIN_VALUE;
    setDestinations(destinations);
    setDestinationTimeCache(destinationTimeCache);
    setDestinationLossCache(destinationLossCache);
  }

  default int[] readSourceCache(BlockPos position) {
    EssenceContainerEntity[] sources = getSources();
    int[] output = new int[2];
    Arrays.fill(output, Integer.MIN_VALUE);
    for (int x = 0; x < sources.length; x++) {
      if (sources[x].getPos().equals(position)) {
        output[0] = getSourceTimeCache()[x];
        output[1] = getSourceLossCache()[x];
        return output;
      }
    }
    return output;
  }

  default int[] readDestinationCache(BlockPos position) {
    EssenceContainerEntity[] destinations = getDestinations();
    int[] output = new int[2];
    Arrays.fill(output, Integer.MIN_VALUE);
    for (int x = 0; x < destinations.length; x++) {
      if (destinations[x].getPos().equals(position)) {
        output[0] = getDestinationTimeCache()[x];
        output[1] = getDestinationLossCache()[x];
        return output;
      }
    }
    return output;
  }

  default void initializeEntity(EssenceConveyorConstants constants) {
    setSources(new EssenceContainerEntity[constants.getMaxSources()]);
    int[] sourceTimeCache = new int[constants.getMaxSources()];
    int[] sourceLossCache = new int[constants.getMaxSources()];
    Arrays.fill(sourceTimeCache, Integer.MIN_VALUE);
    Arrays.fill(sourceLossCache, Integer.MIN_VALUE);
    setSourceTimeCache(sourceTimeCache);
    setSourceLossCache(sourceLossCache);

    setDestinations(new EssenceContainerEntity[constants.getMaxDestinations()]);
    int[] destinationTimeCache = new int[constants.getMaxDestinations()];
    int[] destinationLossCache = new int[constants.getMaxDestinations()];
    Arrays.fill(destinationTimeCache, Integer.MIN_VALUE);
    Arrays.fill(destinationLossCache, Integer.MIN_VALUE);
    setDestinationTimeCache(destinationTimeCache);
    setDestinationLossCache(destinationLossCache);

    setEssenceTransferQueue(new QueuedTransfer[5 * (20 / constants.getTicksBetweenTransfers())]);
    setLastTransferIndex(new int[]{0,0});
  }

  default int findArrayIndex(BlockPos position, EssenceContainerEntity[] arrayToCheckAgainst) {
    for (int x = 0; x < arrayToCheckAgainst.length; x++) {
      if (arrayToCheckAgainst[x].getPos().equals(position)) {
        return x;
      }
    }
    return Integer.MIN_VALUE;
  }

  default float intPercentageToFloat(int lossPercentage) {
    float output;
    output = lossPercentage;
    return output / 100;
  }

  default int[] writeToCache(int valueToWrite, int[] arrayToWrite, int index) {
    arrayToWrite[index] = valueToWrite;
    return arrayToWrite;
  }

  default QueuedTransfer[] expandQueuedTransferArray(QueuedTransfer[] input, EssenceConveyorConstants constants) {
    return Arrays.copyOf(input, input.length + (5 * (20 / constants.getTicksBetweenTransfers())));
  }

  default void deleteQueuedTransfer(int index) {
    QueuedTransfer[] queue = getEssenceTransferQueue();
    queue[index] = null;
    setEssenceTransferQueue(queue);
  }

  default void writeToQueue(BlockPos destination, BlockPos source, EssenceConveyorEntity conveyor, EssenceType essenceType, int quantity, long timeOfTransfer, EssenceConveyorConstants constants, boolean isReturned) {
    int[] sourceCache = readSourceCache(source);
    int[] destinationCache = readDestinationCache(destination);
    float speed = essenceType.getBaseTravelSpeed() * essenceType.getTravelSpeedMult() * constants.getTravelSpeedMult();

    if (checkEntity((EssenceContainerEntity) Objects.requireNonNull(conveyor.getWorld()).getBlockEntity(source))) {

      if (checkEntity((EssenceContainerEntity) Objects.requireNonNull(conveyor.getWorld()).getBlockEntity(destination))) {

        if (sourceCache[0] == Integer.MIN_VALUE) {
          double distanceToSource = Math.sqrt(source.getSquaredDistance(conveyor.getPos()));
          sourceCache[0] = (int) Math.round((distanceToSource / (speed / 20)));
          setSourceTimeCache(writeToCache(sourceCache[0], getSourceTimeCache(), findArrayIndex(source, getSources())));
          if (sourceCache[1] == Integer.MIN_VALUE) {
            sourceCache[1] = essenceType.calculateLossPercentage(distanceToSource, Math.round(essenceType.getbaseDistanceBeforeLoss() * constants.getDistanceBeforeLossMult()));
            setSourceLossCache(writeToCache(sourceCache[1], getSourceLossCache(), findArrayIndex(source, getSources())));
          }
        }
        if (destinationCache[0] == Integer.MIN_VALUE) {
          double distanceToDestination = Math.sqrt(destination.getSquaredDistance(conveyor.getPos()));
          destinationCache[0] = (int) Math.round((distanceToDestination / (speed / 20)));
          setDestinationTimeCache(writeToCache(destinationCache[0], getDestinationTimeCache(), findArrayIndex(destination, getDestinations())));
          if (destinationCache[1] == Integer.MIN_VALUE) {
            destinationCache[1] = essenceType.calculateLossPercentage(distanceToDestination, Math.round(essenceType.getbaseDistanceBeforeLoss() * constants.getDistanceBeforeLossMult()));
            setDestinationLossCache(writeToCache(destinationCache[1], getDestinationLossCache(), findArrayIndex(destination, getDestinations())));
          }
        }

        int actualQuantity = Math.round((quantity - ((quantity * intPercentageToFloat(destinationCache[1])) + (quantity * intPercentageToFloat(destinationCache[1])))) * essenceType.getbaseEfficiencyMult());

        QueuedTransfer[] queue = getEssenceTransferQueue();
        for (int x = 0; x < queue.length; x++) {
          if (queue[x] == null) {
            queue[x] = new QueuedTransfer(source, destination, conveyor.getPos(), essenceType, actualQuantity, timeOfTransfer, sourceCache[0], destinationCache[0], isReturned);
            break;
          }
          else if (x == queue.length - 1) {
            setEssenceTransferQueue(expandQueuedTransferArray(queue, constants));
            queue[x + 1] = new QueuedTransfer(source, destination, conveyor.getPos(), essenceType, actualQuantity, timeOfTransfer, sourceCache[0], destinationCache[0], isReturned);
            break;
          }
        }
      }

      else {
        deleteEntityFromSourceCache(findArrayIndex(source, getSources()));
      }
    }
    else {
      deleteEntityFromDestinationCache(findArrayIndex(destination, getDestinations()));
    }
  }

  default void completeTransfer(World world, QueuedTransfer queuedTransfer) {
    EssenceContainerEntity source = (EssenceContainerEntity) world.getBlockEntity(queuedTransfer.getSource());
    EssenceContainerEntity destination = (EssenceContainerEntity) world.getBlockEntity(queuedTransfer.getDestination());
    if (checkEntity(source) && checkEntity(destination)) {
      try {
        int returnedEssence = destination.addEssence(queuedTransfer.getEssenceType(), queuedTransfer.getQuantity());
        if (returnedEssence > 0 && !queuedTransfer.isReturned()) {
          EssenceConveyorEntity conveyor = (EssenceConveyorEntity) world.getBlockEntity(queuedTransfer.getConveyor());
          if (conveyor != null && conveyor.getType() == blockentitytypes.ESSENCE_CONVEYOR) {
            writeToQueue(queuedTransfer.getSource(), queuedTransfer.getDestination(), conveyor, queuedTransfer.getEssenceType(), returnedEssence, world.getTime(), conveyor.getConveyorConstants(), true);
          }
        }
      } catch (NullPointerException ignored) {
        main.LOGGER.error("Attempt at Essence Transfer caused a NullPointerException!, this means something is very wrong");
      }
    }
  }

  default void readQueue(World world) {
    long gameTime = world.getTime();
    QueuedTransfer[] queue = getEssenceTransferQueue();
    for (int x = 0; x < queue.length; x++) {
      if (queue[x] != null && gameTime >= queue[x].getTimeOfArrival()) {
        completeTransfer(world, queue[x]);
        deleteQueuedTransfer(x);
      }
    }
  }

  default void nextTransfer(int mode, EssenceConveyorEntity conveyor, long gameTime) {
    switch (mode) {
      case 1:
        //mode 1: Transfer Randomly
        Random ran = new Random();


        break;

      case 2:
        //mode 2: Spread Evenly


        break;

      default:
        //mode 0: Round Robin
        EssenceContainerEntity[] sources = getSources();
        EssenceContainerEntity[] destinations = getDestinations();
        int[] lastTransferIndex = getLastTransferIndex();
        TransferRequest request = null;

        for (int x = 0; x < destinations.length; x++) {
          int destinationIndex = (x + 1 + lastTransferIndex[1]) % destinations.length;
          if (destinations[destinationIndex] == null) {
            continue;
          }
          for (int y = 0; y < sources.length; y++) {
            int sourceIndex = (y + 1 + lastTransferIndex[0]) % sources.length;
            if (sources[sourceIndex] == null) {
              continue;
            }
            request = destinations[destinationIndex].negotiateTransfer(sources[sourceIndex], conveyor.getConveyorConstants());
            if (request != null) {
              writeToQueue(destinations[destinationIndex].getPos(), sources[sourceIndex].getPos(), conveyor, request.getType(), request.getQuantity(), gameTime, conveyor.getConveyorConstants(), false);
              lastTransferIndex[0] = sourceIndex;
              lastTransferIndex[1] = destinationIndex;
              break;
            }
          }
          if (request != null) {
            break;
          }
        }
        setLastTransferIndex(lastTransferIndex);
        break;
    }
  }

}
