package com.github.ndioc.duality.mechanics.essence;

import net.minecraft.util.math.BlockPos;

public interface essenceContainer {

  essence createEssenceObject(essenceType type, int capacity);
  void deleteEssenceObject();

  int transferEssence(BlockPos position, essenceType type, int amount);
  int receivedEssence(essenceType type, int amount);



}
