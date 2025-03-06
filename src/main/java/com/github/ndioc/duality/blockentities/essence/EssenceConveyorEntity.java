package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.mechanics.essence.EssenceConveyor;
import com.github.ndioc.duality.mechanics.essence.objects.QueuedTransfer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EssenceConveyorEntity extends BlockEntity implements EssenceConveyor {

  public EssenceConveyorEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONVEYOR, pos, state);
    initializeEntity();
  }

  private QueuedTransfer[] essenceTransferQueue;

  private BlockPos[] sources;
  private BlockPos[] destinations;

  private int[] sourceTimeCache;
  private int[] destinationTimeCache;

  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
  }

  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
  }

  public QueuedTransfer[] getEssenceTransferQueue() {
    return essenceTransferQueue;
  }

  public void setEssenceTransferQueue(QueuedTransfer[] queuedTransfer) {
    essenceTransferQueue = queuedTransfer;
  }

  public BlockPos[] getSources() {
    return sources;
  }

  public void setSources(BlockPos[] sources) {
    this.sources = sources;
  }

  public BlockPos[] getDestinations() {
    return destinations;
  }

  public void setDestinations(BlockPos[] destinations) {
    this.destinations = destinations;
  }

  public int[] getSourceTimeCache() {
    return sourceTimeCache;
  }

  public void setSourceTimeCache(int[] sourceTimeCache) {
    this.sourceTimeCache = sourceTimeCache;
  }

  public int[] getDestinationTimeCache() {
    return destinationTimeCache;
  }

  public void setDestinationTimeCache(int[] destinationTimeCache) {
    this.destinationTimeCache = destinationTimeCache;
  }

  public static void tick(World world, BlockPos position, BlockState state, EssenceConveyorEntity entity) {
  }

}