package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.mechanics.essence.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class EssenceContainerEntity extends BlockEntity implements essenceContainer {

  int volumePerContainer;
  int maxTransferPerSecond;
  int numberOfContainers;
  int[] allowedContainers;

  public EssenceContainerEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONTAINER, pos, state);
    setConstants();
  }

  essence[] container = new essence[numberOfContainers];
  int[] allocatedContainers = new int[numberOfContainers];

  public void setConstants() {
    int[][] AiA = essenceBlockConstants.fetchConstants(this.getCachedState().getBlock().getTranslationKey());
    if(AiA != null) {
      volumePerContainer = AiA[0][0];
      maxTransferPerSecond = AiA[0][1];
      numberOfContainers = AiA[0][2];
      allowedContainers = AiA[1];
    }
  }

  @Override
  public void writeNbt(NbtCompound data) {
    super.writeNbt(data);
    data.putInt("VolumePerContainer", volumePerContainer);
    data.putInt("maxTransferPerSecond", maxTransferPerSecond);
    data.putInt("numberOfContainers", numberOfContainers);
    data.putIntArray("allowedContainers", allowedContainers);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    volumePerContainer = nbt.getInt("VolumePerContainer");
    maxTransferPerSecond = nbt.getInt("maxTransferPerSecond");
    numberOfContainers = nbt.getInt("numberOfContainers");
    allowedContainers = nbt.getIntArray("allowedContainers");
  }

  public essence createEssenceObject(essenceType type, int capacity) {
     return new essence(type, capacity);
  }

  public void deleteEssenceObject(int arraynum) {
    container[arraynum] = null;
    allocatedContainers[arraynum] = 0;
  }

  public EssenceContainerEntity getContainerEntity(BlockPos position) {
    assert world != null;
    BlockEntity checktype = world.getBlockEntity(position);
    if (checktype != null && checktype.getType() == blockentitytypes.ESSENCE_CONTAINER) {
      return (EssenceContainerEntity) checktype;
    }
    return null;
  }

  public int removeEssence(essenceType type, int amount) {
    int amountRemoved = 0;
    amount = Math.min(amount, maxTransferPerSecond);
    for (int x = 0; x < numberOfContainers; x++) {
      if (allocatedContainers[x] == type.getNumericalID()) {
          amountRemoved = container[x].removeEssence(amount);
        if (amountRemoved == 0 && container[x].getQuantity() <= 0) {
          container[x].forceSetQuantity(-1);
        }
        break;
      }
    }
    return amountRemoved;
  }

  public int addEssence(essenceType type, int amount) {
    int firstfreecontainer = -1;
    amount = Math.min(amount, maxTransferPerSecond);

    for(int x = 0; x < allowedContainers.length; x++) {
      if (type.getNumericalID() == allocatedContainers[x]) {
          break;
      }
      else if (x == allowedContainers.length - 1) {
        return amount;
      }
    }

    for (int x = 0; x < numberOfContainers; x++) {
      if (allocatedContainers[x] == 0 && firstfreecontainer == -1) {
          firstfreecontainer = x;
      }
        if (type.getNumericalID() == allocatedContainers[x]) {
        return container[x].addEssence(amount);
      }
    }

    if (firstfreecontainer != -1) {
      container[firstfreecontainer] = createEssenceObject(type, volumePerContainer);
      return container[firstfreecontainer].addEssence(amount);
    }

    return 0;
  }

}
