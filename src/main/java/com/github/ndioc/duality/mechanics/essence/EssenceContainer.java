package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.mechanics.essence.objects.EssenceContainerConstants;
import com.github.ndioc.duality.mechanics.essence.objects.essence;
import net.minecraft.nbt.NbtCompound;

public interface EssenceContainer {

  essence[] getEssenceArray();
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


  default boolean createEssenceObject(essenceType type, int capacity, boolean unlimited) {
    essence[] array = getEssenceArray();
    for (int x = 0; x < array.length; x++) {
      if (array[x] == null) {
        array[x] = new essence(type, capacity, unlimited);
        return true;
      }
    }
    return false;
  }

  default void recreateEssenceObject(essenceType type, int capacity, int quantity) {
    essence[] array = getEssenceArray();
    for (int x = 0; x < array.length; x++) {
      if (array[x] == null) {
        array[x] = new essence(type, capacity, quantity);
      }
    }
  }

  default void deleteEssenceObject(int index) {
    essence[] array = getEssenceArray();
    array[index] = null;
  }

  default int findArrayIndex(essenceType type) {
    essence[] array = getEssenceArray();
    for (int x = 0; x < array.length; x++) {
      if (array[x].getType().getNumericalID() == type.getNumericalID()) {
        return x;
      }
    }
    return -1;
  }

  default boolean canReceiveEssence(essenceType type) {
    essence[] array = getEssenceArray();
    int index = findArrayIndex(type);

    return array[index].getQuantity() < array[index].getCapacity();
  }

  default int addEssence(essenceType type, int amount) {
    essence[] array = getEssenceArray();
    int index = findArrayIndex(type);

    if (index == -1) {
      if (createEssenceObject(type, getVolumePerContainer(), isUnlimited())) {

      }
      else {
        return amount;
      }
    }

    return array[index].addEssence(amount);

  }

  default int removeEssence(essenceType type, int amount) {
    essence[] array = getEssenceArray();
    int index = findArrayIndex(type);

    if (index == -1) {
      return 0;
    }

    int removed = array[index].removeEssence(amount);
    if (removed < amount) {
      deleteEssenceObject(index);
    }
    return removed;
  }

  default void writeContainersToNBT(NbtCompound nbt) {
    essence[] array = getEssenceArray();

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
        createEssenceObject(essenceType.getEssenceTypeByID(type), getVolumePerContainer(), true);
      }
    }
    else {
      for (int x = 0; x < types.length; x++) {
        if (types[x] != -100) {
          recreateEssenceObject(essenceType.getEssenceTypeByID(types[x]), getVolumePerContainer(), quantities[x]);
        }
      }
    }
  }
}
