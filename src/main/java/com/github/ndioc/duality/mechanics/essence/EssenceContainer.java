package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceContainerConstants;
import com.github.ndioc.duality.mechanics.essence.objects.Essence;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceType;
import com.github.ndioc.duality.mechanics.essence.objects.TransferRequest;
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
      if (array[x].getType().getNumericalID() == type.getNumericalID()) {
        return x;
      }
    }
    return Integer.MIN_VALUE;
  }

  default TransferRequest negotiateTransfer(EssenceContainerEntity entity) {
    // destination asks source for theirEssence
    Essence[] destinationArray = getEssenceArray();
    Essence[] sourceArray = entity.getEssenceArray();
    EssenceType type = null;
    int amount = Integer.MIN_VALUE;

    for (Essence myEssence : destinationArray) {
      for (Essence theirEssence : sourceArray) {
        if (myEssence != null) {
          if (theirEssence != null && myEssence.getType() == theirEssence.getType()) {
            int amountToCompare = Math.min(myEssence.getFreeCapacity(), theirEssence.getQuantity());
            if (amountToCompare > amount) {
              amount = amountToCompare;
              type = myEssence.getType();
            }
          }
        }
      }
    }

    if (type != null) {
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

  default int getQuantityByType(EssenceType type) {
    Essence[] array = getEssenceArray();
    int index = findArrayIndex(type);
    return array[index].getQuantity();
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
