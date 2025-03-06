package com.github.ndioc.duality.mechanics.essence.objects;

import net.minecraft.util.math.BlockPos;

public class QueuedTransfer {

  private BlockPos source;
  private BlockPos destination;
  private BlockPos conveyor;
  private EssenceType essenceType;
  int quantity;
  long timeOfTransfer;
  long timeOfArrival;

  public QueuedTransfer(BlockPos source, BlockPos destination, BlockPos conveyor, EssenceType essenceType, int quantity, long timeOfTransfer, long timeOfArrival) {
    this.source = source;
    this.destination = destination;
    this.conveyor = conveyor;
    this.essenceType = essenceType;
    this.quantity = quantity;
    this.timeOfTransfer = timeOfTransfer;
    this.timeOfArrival = timeOfArrival;
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

  public long getTimeOfArrival() {
    return timeOfArrival;
  }
}
