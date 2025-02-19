package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import net.minecraft.util.math.BlockPos;

public interface essenceTransfer {

  EssenceContainerEntity checkCacheForTarget(BlockPos position);
  EssenceContainerEntity getEntity(BlockPos position);


  void transferEssence(EssenceContainerEntity source, EssenceContainerEntity target, int amount, essenceType type);

}
