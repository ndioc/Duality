package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.mechanics.essence.objects.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public interface EssenceConveyor {

  QueuedTransfer[] getEssenceTransferQueue();
  void setEssenceTransferQueue(QueuedTransfer[] queuedTransfer);

  BlockPos[] getSources();
  void setSources(BlockPos[] sources);

  BlockPos[] getDestinations();
  void setDestinations(BlockPos[] destinations);

  int[] getSourceTimeCache();
  void setSourceTimeCache(int[] sourceTimeCache);

  int[] getDestinationTimeCache();
  void setDestinationTimeCache(int[] destinationTimeCache);

  default void writeTargets(NbtCompound nbt) {
    BlockPos[] sources = getSources();
    BlockPos[] destinations = getDestinations();

  }

  default int readSourceCache(BlockPos position) {
    BlockPos[] sources = getSources();

    for (int x = 0; x < sources.length; x++) {
      if (sources[x].equals(position)) {
        return getSourceTimeCache()[x];
      }
    }

    return Integer.MIN_VALUE;
  }

  default int readDestinationCache(BlockPos position) {
    BlockPos[] destinations = getDestinations();

    for (int x = 0; x < destinations.length; x++) {
      if (destinations[x].equals(position)) {
        return  getDestinationTimeCache()[x];
      }
    }
    return Integer.MIN_VALUE;
  }

  default void initializeEntity() {
    setSources(new BlockPos[5]);
    setSourceTimeCache(new int[5]);
    setDestinations(new BlockPos[5]);
    setDestinationTimeCache(new int[5]);
    setEssenceTransferQueue(new QueuedTransfer[5]);
  }

  default void writeToCache() {

  }

  default QueuedTransfer[] expandQueuedTransferArray(QueuedTransfer[] input) {
    return Arrays.copyOf(input, input.length + 5);
  }

  default void writeToQueue(BlockPos destination, BlockPos source, BlockPos conveyor, EssenceType essenceType, int quantity, long timeOfTransfer, EssenceConveyorConstants constants) {
    int sourceDelay = readSourceCache(source);
    int destinationDelay = readDestinationCache(destination);
    float speed = essenceType.getBaseTravelSpeed() * essenceType.getTravelSpeedMult() * constants.getTravelSpeedMult();

    if (sourceDelay == Integer.MIN_VALUE) {
      double distanceToSource = Math.sqrt(source.getSquaredDistance(conveyor));

    }
    if (destinationDelay == Integer.MIN_VALUE) {
      double distanceToDestination = Math.sqrt(destination.getSquaredDistance(conveyor));

    }

  }

  default void readQueue() {

  }

}
