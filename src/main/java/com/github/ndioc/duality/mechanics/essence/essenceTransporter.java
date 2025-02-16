package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.blockentities.EssenceContainerEntity;
import net.minecraft.util.math.BlockPos;

public interface essenceTransporter {

  EssenceContainerEntity getSourceEntity(BlockPos position);
  EssenceContainerEntity getTargetEntity(BlockPos position);

  void transferEssence(EssenceContainerEntity source, EssenceContainerEntity target, int amount, essenceType type);

}
