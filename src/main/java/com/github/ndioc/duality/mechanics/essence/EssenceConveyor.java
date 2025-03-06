package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public interface EssenceConveyor {

  EssenceContainerEntity checkCacheForTarget(BlockPos position);
  EssenceContainerEntity getEntity(BlockPos position);
  void writeSelection(NbtCompound nbt);

  void transferEssence(EssenceContainerEntity source, EssenceContainerEntity target, int amount, EssenceType type);

}
