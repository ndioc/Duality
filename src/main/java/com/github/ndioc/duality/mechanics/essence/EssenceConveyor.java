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

import static com.github.ndioc.duality.util.nbt.BlockPosNBT.BlockPosNull;
import static com.github.ndioc.duality.util.nbt.BlockPosNBT.readBlockPosNBT;

public interface EssenceConveyor {

  EssenceConveyorConstants getConveyorConstants();

  QueuedTransfer[] getEssenceTransferQueue();
  void setEssenceTransferQueue(QueuedTransfer[] queuedTransfer);

  EssenceContainerEntity[] getSources();
  void setSources(EssenceContainerEntity[] sources);

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
    BlockPos[] sourcePos = readBlockPosNBT(nbt, "Sources");
    BlockPos[] destinationPos = readBlockPosNBT(nbt, "Destinations");

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
          }
          else if (x == queue.length - 1) {
            setEssenceTransferQueue(expandQueuedTransferArray(queue, constants));
            queue[x + 1] = new QueuedTransfer(source, destination, conveyor.getPos(), essenceType, actualQuantity, timeOfTransfer, sourceCache[0], destinationCache[0], isReturned);
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
      if (queue[x] != null && gameTime == queue[x].getTimeOfArrival()) {
        completeTransfer(world, queue[x]);
        deleteQueuedTransfer(x);
      }
    }
  }

  default void nextTransfer(int mode) {
    int[] lastTransferIndex = getLastTransferIndex();
    switch (mode) {
      case 1:
        //mode 1: random
        new Random().nextInt();

        break;

      case 2:
        //mode 2: currently nothing
        break;
      default:
        //mode 0: Round Robin

        EssenceContainerEntity[] sources = getSources();
        EssenceContainerEntity[] destinations = getDestinations();

        for (int x = 0; x < sources.length; x++) {

        }
        setLastTransferIndex(lastTransferIndex);
        break;
    }
  }

}
