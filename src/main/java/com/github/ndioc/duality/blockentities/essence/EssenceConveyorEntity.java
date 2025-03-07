package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.mechanics.essence.EssenceConveyor;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceConveyorConstants;
import com.github.ndioc.duality.mechanics.essence.objects.QueuedTransfer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EssenceConveyorEntity extends BlockEntity implements EssenceConveyor {

  public EssenceConveyorEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONVEYOR, pos, state);
    constants = EssenceConveyorConstants.fetchConveyorConstants(state.getBlock().getTranslationKey());
    if (constants == null) {
      throw new RuntimeException("Constants Returned NULL in EssenceConveyorEntity!");
    }
    initializeEntity(constants);
  }

  private final EssenceConveyorConstants constants;
  private QueuedTransfer[] essenceTransferQueue;
  private int transferMode = 0;
  private int[] lastTransferIndex;

  private EssenceContainerEntity[] sources;
  private EssenceContainerEntity[] destinations;

  private int[] sourceTimeCache;
  private int[] destinationTimeCache;
  private int[] sourceLossCache;
  private int[] destinationLossCache;

  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
  }

  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
  }

  public EssenceConveyorConstants getConveyorConstants() {
    return constants;
  }

  public QueuedTransfer[] getEssenceTransferQueue() {
    return essenceTransferQueue;
  }

  public void setEssenceTransferQueue(QueuedTransfer[] queuedTransfer) {
    essenceTransferQueue = queuedTransfer;
  }

  public EssenceContainerEntity[] getSources() {
    return sources;
  }

  public void setSources(EssenceContainerEntity[] sources) {
    this.sources = sources;
  }

  public EssenceContainerEntity[] getDestinations() {
    return destinations;
  }

  public void setDestinations(EssenceContainerEntity[] destinations) {
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

  public int[] getSourceLossCache() {
    return sourceLossCache;
  }

  public void setSourceLossCache(int[] sourceLossCache) {
    this.sourceLossCache = sourceLossCache;
  }

  public int[] getDestinationLossCache() {
    return destinationLossCache;
  }

  public void setDestinationLossCache(int[] destinationLossCache) {
    this.destinationLossCache = destinationLossCache;
  }

  public int[] getLastTransferIndex() {
    return lastTransferIndex;
  }

  public void setLastTransferIndex(int[] lastTransferIndex) {
    this.lastTransferIndex = lastTransferIndex;
  }

  public static void tick(World world, BlockPos position, BlockState state, EssenceConveyorEntity entity) {
    int x = 0;
    x++;
    entity.readQueue(world);

    if (x >= entity.constants.getTicksBetweenTransfers()) {
      entity.nextTransfer(entity.transferMode);
      x = 0;
    }
  }

}