package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.Duality;
import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.mechanics.essence.objects.*;
import com.github.ndioc.duality.networking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Arrays;

import static com.github.ndioc.duality.networking.sendPacketToClient;

public interface EssenceContainer {

  Essence[] getEssenceArray();
  void setEssenceArray(Essence[] container);
  EssenceContainerConstants getConstants();
  boolean isActivated();
  void interfaceMarkDirty();

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

  default void syncEntity(BlockEntity entity) {
    PacketByteBuf packet = PacketByteBufs.create();
    packet.writeBlockPos(entity.getPos());


    for (ServerPlayerEntity player : PlayerLookup.tracking(entity)) {
      sendPacketToClient(player, networking.ESSENCE_CONTAINER_SYNC_PACKET_ID, packet);
    }
  }

  default boolean createEssenceObject(EssenceType type, int capacity, boolean unlimited) {
    Essence[] array = getEssenceArray();
    for (int x = 0; x < array.length; x++) {
      if (array[x] == null) {
        array[x] = new Essence(type, capacity, unlimited);
        setEssenceArray(array);
        interfaceMarkDirty();
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
        interfaceMarkDirty();
        break;
      }
    }
    setEssenceArray(array);
  }

  default void deleteEssenceObject(int index) {
    Essence[] array = getEssenceArray();
    array[index] = null;
    setEssenceArray(array);
    interfaceMarkDirty();
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
      Duality.LOGGER.error("Fetching of constants for EssenceContainerEntity returned null!");
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

  default boolean isFull() {
    Essence[] array = getEssenceArray();
    for (Essence essence : array) {
      if (essence == null) {
        return false;
      }
    }
    return true;
  }

  default TransferRequest negotiateTransfer(EssenceContainerEntity entity, EssenceConveyorConstants constants) {
    // destination asks source for sourceEssence

    Essence[] destinationArray = getEssenceArray();
    Essence[] sourceArray = entity.getEssenceArray();
    int index = 0;

    int arrayLength = Math.max(sourceArray.length, destinationArray.length);

    EssenceType[] availableTypes = new EssenceType[arrayLength];

    int[] amount = new int[arrayLength];
    Arrays.fill(amount, Integer.MIN_VALUE);

    // checking what types are available for transfer

    for (Essence sourceEssence : sourceArray) {
      if (sourceEssence == null) {
        continue;
      }
      for (Essence destinationEssence : destinationArray) {
        if (destinationEssence == null) {
          availableTypes[index] = sourceEssence.getType();
          index++;
          break;
        }
        if (sourceEssence.getType() == destinationEssence.getType()) {
          availableTypes[index] = sourceEssence.getType();
          index++;
          break;
        }
      }
    }

    // checking what type is best to send to maximise amount sent.
    index = 0;
    boolean isFull = isFull();
    for (EssenceType type : availableTypes) {
      int destinationIndex = findArrayIndex(type);
      int sourceIndex = entity.findArrayIndex(type);
      if (destinationIndex == Integer.MIN_VALUE) {
        if (!isFull) {
          amount[index] = Math.min(getVolumePerContainer(), sourceArray[sourceIndex].getQuantity());
          index++;
          continue;
        }
        amount[index] = Integer.MIN_VALUE;
        index++;
        continue;
      }
      amount[index] = Math.min(destinationArray[destinationIndex].getFreeCapacity(), sourceArray[sourceIndex].getQuantity());
      index++;
    }

    // create the Transfer Request

    EssenceType typeToSend = null;
    int amountToSend = Integer.MAX_VALUE;

    for (int x = 0; x < amount.length; x++) {
      if (amount[x] > 0 && amount[x] < amountToSend) {
        typeToSend = availableTypes[x];
        amountToSend = amount[x];
      }
    }

    if (typeToSend != null && amountToSend < Integer.MAX_VALUE) {
      return new TransferRequest(typeToSend, Math.min(constants.getAmountPerTransfer(), amountToSend));
    }
    return null;
  }

  default int addEssence(EssenceType type, int amount) {
    Essence[] array = getEssenceArray();
    int index = findArrayIndex(type);

    if (index == Integer.MIN_VALUE) {
      if (createEssenceObject(type, getVolumePerContainer(), isUnlimited())) {
        index = findArrayIndex(type);
        interfaceMarkDirty();
        return array[index].addEssence(amount);
      }
      else {
        return amount;
      }
    }
    interfaceMarkDirty();
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
    interfaceMarkDirty();
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
