package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.mechanics.essence.objects.*;
import net.minecraft.nbt.NbtCompound;

public interface EssenceContainer {

  Essence[] getEssenceArray();
  void setEssenceArray(Essence[] container);
  EssenceContainerConstants getConstants();


  default int getVolumePerContainer() {
    return getConstants().getVolumePerContainer();
  }

  default int getMaxTransferPerSecond() {
    return getConstants().getMaxTransferPerSecond();
  }

  default int getNumberOfContainers() {
    return getConstants().getNumberOfContainers();
  }

  default int[] getAllowedEssenceTypes() {
    return getConstants().getAllowedEssenceTypes();
  }

  default boolean isUnlimited() {
    return getConstants().isUnlimited();
  }


  default boolean createEssenceObject(EssenceType type, int capacity, boolean unlimited) {
    Essence[] array = getEssenceArray();
    for (int x = 0; x < array.length; x++) {
      if (array[x] == null) {
        array[x] = new Essence(type, capacity, unlimited);
        setEssenceArray(array);
        return true;
      }
    }
    return false;
  }

  default void recreateEssenceObject(EssenceType type, int capacity, int quantity) {
    Essence[] array = getEssenceArray();
    for (int x = 0; x < array.length; x++) {
      if (array[x] == null) {
        array[x] = new Essence(type, capacity, quantity);
      }
    }
    setEssenceArray(array);
  }

  default void deleteEssenceObject(int index) {
    Essence[] array = getEssenceArray();
    array[index] = null;
    setEssenceArray(array);
  }

  default int findArrayIndex(EssenceType type) {
    Essence[] array = getEssenceArray();
    for (int x = 0; x < array.length; x++) {
      if (array[x] != null && array[x].getType().getNumericalID() == type.getNumericalID()) {
        return x;
      }
    }
    return Integer.MIN_VALUE;
  }

  default void initializeEntity() {
    EssenceContainerConstants constants = getConstants();
    if (constants == null) {
      main.LOGGER.error("Fetching of constants for EssenceContainerEntity returned null!");
      return;
    }
    Essence[] container = new Essence[constants.getNumberOfContainers()];
    if (isUnlimited()) {
      int[] types = constants.getAllowedEssenceTypes();
      for (int x = 0; x < types.length; x++) {
        container[x] = new Essence(EssenceType.getEssenceTypeByID(types[x]), constants.getVolumePerContainer(), isUnlimited());
      }
    }
    setEssenceArray(container);
  }

  default TransferRequest negotiateTransfer(EssenceContainerEntity entity, EssenceConveyorConstants constants) {
    // destination asks source for sourceEssence
    Essence[] destinationArray = getEssenceArray();
    Essence[] sourceArray = entity.getEssenceArray();
    boolean destinationHasFreeSlot = false;
    EssenceType type = null;
    int amount = Integer.MIN_VALUE;

    for (Essence destinationEssence : destinationArray) {
      for (Essence sourceEssence : sourceArray) {
        if (destinationEssence == null) {
          destinationHasFreeSlot = true;
        }
          if (sourceEssence != null && destinationHasFreeSlot || sourceEssence != null && destinationEssence.getType() == sourceEssence.getType()) {
            int amountToCompare;
            if (destinationHasFreeSlot) {
              amountToCompare = Math.min(getConstants().getVolumePerContainer(), sourceEssence.getQuantity());
            }
            else {
              amountToCompare = Math.min(destinationEssence.getFreeCapacity(), sourceEssence.getQuantity());
            }
            if (amountToCompare > amount) {
              amount = amountToCompare;
              type = sourceEssence.getType();
            }
          }
        }
      }

    if (type != null && amount > 0) {
      amount = Math.min(amount, constants.getAmountPerTransfer());
      return new TransferRequest(type, entity.removeEssence(type, amount));
    }
    return null;
  }

  default int addEssence(EssenceType type, int amount) {
    Essence[] array = getEssenceArray();
    int index = findArrayIndex(type);

    if (index == Integer.MIN_VALUE) {
      if (createEssenceObject(type, getVolumePerContainer(), isUnlimited())) {
        index = findArrayIndex(type);
        return array[index].addEssence(amount);
      }
      else {
        return amount;
      }
    }

    return array[index].addEssence(amount);

  }

  default int removeEssence(EssenceType type, int amount) {
    Essence[] array = getEssenceArray();
    int index = findArrayIndex(type);

    if (index == Integer.MIN_VALUE) {
      return 0;
    }

    int removed = array[index].removeEssence(amount);
    if (removed < amount) {
      deleteEssenceObject(index);
    }
    return removed;
  }

  default void writeContainersToNBT(NbtCompound nbt) {
    Essence[] array = getEssenceArray();

    int[] types = new int[array.length];
    int[] quantities = new int[array.length];

    for (int x = 0; x < array.length; x++) {
      if (array[x] == null) {
        types[x] = -100;
        quantities[x] = 0;
        continue;
      }
      types[x] = array[x].getType().getNumericalID();
      quantities[x] = array[x].getQuantity();
    }

    nbt.putBoolean("Unlimited", isUnlimited());
    nbt.putIntArray("Types", types);
    nbt.putIntArray("Quantities", quantities);

  }

  default void readContainersFromNBT(NbtCompound nbt) {
    boolean unlimited = nbt.getBoolean("Unlimited");
    int[] types = nbt.getIntArray("Types");
    int[] quantities = nbt.getIntArray("Quantities");

    if (unlimited) {
      for (int type : types) {
        createEssenceObject(EssenceType.getEssenceTypeByID(type), getVolumePerContainer(), true);
      }
    }
    else {
      for (int x = 0; x < types.length; x++) {
        if (types[x] != -100) {
          recreateEssenceObject(EssenceType.getEssenceTypeByID(types[x]), getVolumePerContainer(), quantities[x]);
        }
      }
    }
  }
}
