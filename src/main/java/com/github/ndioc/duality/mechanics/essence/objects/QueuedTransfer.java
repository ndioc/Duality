package com.github.ndioc.duality.mechanics.essence.objects;

import net.minecraft.util.math.BlockPos;

public class QueuedTransfer {

  private BlockPos source;
  private BlockPos destination;
  private BlockPos conveyor;
  private EssenceType essenceType;
  int quantity;
  long timeOfTransfer;
  int timeFromSourceToConveyor;
  int timeFromConveyorToDestination;
  long timeOfArrival;
  boolean isReturned;

  public QueuedTransfer(BlockPos source, BlockPos destination, BlockPos conveyor, EssenceType essenceType, int quantity, long timeOfTransfer, int timeFromSourceToConveyor, int timeFromConveyorToDestination, boolean isReturned) {
    this.source = source;
    this.destination = destination;
    this.conveyor = conveyor;
    this.essenceType = essenceType;
    this.quantity = quantity;
    this.timeOfTransfer = timeOfTransfer;
    this.timeFromSourceToConveyor = timeFromSourceToConveyor;
    this.timeFromConveyorToDestination = timeFromConveyorToDestination;
    timeOfArrival = timeOfTransfer + timeFromSourceToConveyor + timeFromConveyorToDestination;
    this.isReturned = isReturned;
  }

  public BlockPos getSource() {
    return source;
  }

  public BlockPos getDestination() {
    return destination;
  }

  public BlockPos getConveyor() {
    return conveyor;
  }

  public EssenceType getEssenceType() {
    return essenceType;
  }

  public int getQuantity() {
    return quantity;
  }

  public long getTimeOfTransfer() {
    return timeOfTransfer;
  }

  public int getTimeFromSourceToConveyor() {
    return timeFromSourceToConveyor;
  }

  public int getTimeFromConveyorToDestination() {
    return timeFromConveyorToDestination;
  }

  public long getTimeOfArrival() {
    return timeOfArrival;
  }

  public boolean isReturned() {
    return isReturned;
  }
}
