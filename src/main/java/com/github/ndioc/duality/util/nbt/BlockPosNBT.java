package com.github.ndioc.duality.util.nbt;

import com.github.ndioc.duality.mechanics.essence.objects.EssenceContainerConstants;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceConveyorConstants;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public class BlockPosNBT {

  public static void writeBlockPosNBT(BlockPos[] positionarray, NbtCompound nbt, String key) {
    int[] positionNBT = new int[positionarray.length * 3];

    if (positionarray.length == 0) {
      Arrays.fill(positionNBT,0);
      nbt.putIntArray(key, positionNBT);
      return;
    }

    for (int x = 0, y = 0; x < positionarray.length; x++) {
      positionNBT[y] = positionarray[x].getX();
      y++;
      positionNBT[y] = positionarray[x].getY();
      y++;
      positionNBT[y] = positionarray[x].getZ();
      y++;
    }

    nbt.putIntArray(key, positionNBT);
  }

  public static BlockPos[] readBlockPosNBT(NbtCompound nbt, String key) {
    int[] array = nbt.getIntArray(key);
    int arrayDepth = array.length / 3;
    BlockPos[] output = new BlockPos[arrayDepth];
    for (int u = 0, v = 0; u < arrayDepth; u++) {
      int x = array[v];
      v++;
      int y = array[v];
      v++;
      int z = array[v];
      v++;
      output[u] = new BlockPos(x, y, z);
    }
    return output;
  }

  public static BlockPos BlockPosNull() {
    return new BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
  }

  public static NbtCompound createTargetingNBT(EssenceConveyorConstants constants) {
    NbtCompound nbt = new NbtCompound();
    int[] sources = new int[3 * constants.getMaxSources()];
    int[] destinations = new int[3 * constants.getMaxDestinations()];
    Arrays.fill(sources, Integer.MIN_VALUE);
    Arrays.fill(destinations, Integer.MIN_VALUE);
    nbt.putIntArray("Sources", sources);
    nbt.putIntArray("Destinations", destinations);
    return nbt;
  }

}
