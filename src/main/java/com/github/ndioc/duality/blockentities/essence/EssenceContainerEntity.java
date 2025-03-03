package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.mechanics.essence.*;
import com.github.ndioc.duality.mechanics.selection.SelectableBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Objects;

public class EssenceContainerEntity extends BlockEntity implements essenceContainer, SelectableBlockEntity {

  int volumePerContainer;
  int maxTransferPerSecond;
  int numberOfContainers;
  int[] allowedContainers;
  boolean unlimited;

  int selected = -1;

  public EssenceContainerEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONTAINER, pos, state);
    setConstants(fetchConstants(this.getCachedState().getBlock().getTranslationKey()));
  }

  essence[] container;
  int[] allocatedContainers;

  public essence[] getEssenceArray() {
    return container;
  }

  public void setConstants(int[][] AiA) {
    if(AiA != null) {
      volumePerContainer = AiA[0][0];
      maxTransferPerSecond = AiA[0][1];
      numberOfContainers = AiA[0][2];
      unlimited = AiA[0][3] == 1;
      allowedContainers = AiA[1];

      if (unlimited) {
        container = new essence[essenceType.getEssenceTypeCount()];
        allocatedContainers = new int[essenceType.getEssenceTypeCount()];

        for (int x = 0; x < allowedContainers.length; x++) {
          container[x] = createEssenceObject(essenceType.getEssenceTypeByID(allowedContainers[x]), volumePerContainer, unlimited);
          allocatedContainers[x] = allowedContainers[x];
        }
      }
      else {
        container = new essence[numberOfContainers];
        allocatedContainers = new int[numberOfContainers];
        Arrays.fill(allocatedContainers, -100);
      }
    }
    else {
      main.LOGGER.error("Translation Key of essenceContainer is incorrect causing the fetching of constants to fail. shit");
    }
  }

  public int getSelected() {
    return selected;
  }

  public void setSelected(int selected) {
    this.selected = selected;
  }

  private int[] writeContainerQuantityToNBT () {
    int[] quantityarray = new int[container.length];
    for (int x = 0; x < container.length; x++) {
      if (container[x] != null) {
        quantityarray[x] = container[x].getQuantity();
      }
    }
    return quantityarray;
  }

  private void recreateContainers(int[] quantity, int[] types) {
    for (int x = 0; x < types.length; x++) {
      if (types[x] != -100) {
      recreateEssenceObject(essenceType.getEssenceTypeByID(types[x]), volumePerContainer, quantity[x]);
      }
    }
  }

  @Override
  public void writeNbt(NbtCompound data) {
    super.writeNbt(data);
    data.putBoolean("unlimited", unlimited);
    data.putIntArray("Essence Container Quantity", writeContainerQuantityToNBT());
    data.putIntArray("Essence Container Types", allocatedContainers);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    unlimited = nbt.getBoolean("unlimited");
    recreateContainers(nbt.getIntArray("Essence Container Quantity"), nbt.getIntArray("Essence Container Types"));
    allocatedContainers = nbt.getIntArray("Essence Container Types");
  }

  public void deleteEssenceObject(int arraynum) {
    container[arraynum] = null;
    allocatedContainers[arraynum] = -100;
    markDirty();
  }

  public boolean canReceiveEssence(essenceType type) {
    for (int x = 0; x < allocatedContainers.length; x++) {
      if (type.getNumericalID() == allocatedContainers[x]) {
        return container[x].getQuantity() < container[x].getCapacity();
      }
    }
    return false;
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
    markDirty();
    return amountRemoved;
  }

  public int addEssence(essenceType type, int amount) {
    int firstfreecontainer = -1;
    int amounttoreturn = 0;

    if (amount > maxTransferPerSecond) {
      amounttoreturn = amount - maxTransferPerSecond;
      amount = amount - amounttoreturn;
    }

    for(int x = 0; x < allowedContainers.length; x++) {
      if (type.getNumericalID() == allocatedContainers[x]) {
          break;
      }
      else if (x == allowedContainers.length - 1) {
        return amount + amounttoreturn;
      }
    }

    for (int x = 0; x < numberOfContainers; x++) {
      if (type.getNumericalID() == allocatedContainers[x]) {
        markDirty();
        if (container[x].getQuantity() < 0) {
          container[x].forceSetQuantity(0);
        }
        return container[x].addEssence(amount)  + amounttoreturn;
      }
      if (allocatedContainers[x] == -100) {
          firstfreecontainer = x;
          break;
      }
      if (container[x].getQuantity() == -1) {
        deleteEssenceObject(x);
        firstfreecontainer = x;
        break;
      }
    }

    if (firstfreecontainer != -1) {
      createEssenceObject(type, volumePerContainer, unlimited);
      markDirty();
      return container[firstfreecontainer].addEssence(amount)  + amounttoreturn;
    }
    markDirty();
    return amount + amounttoreturn;
  }

}
