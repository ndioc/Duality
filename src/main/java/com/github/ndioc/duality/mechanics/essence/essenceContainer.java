package com.github.ndioc.duality.mechanics.essence;

import net.minecraft.util.math.BlockPos;

public interface essenceContainer extends essenceCore {

  int startEssenceTransaction(BlockPos position);
  int transferEssence(BlockPos position, essenceType type, int amount);
  int receiveEssence(essenceType type, int amount);



}
