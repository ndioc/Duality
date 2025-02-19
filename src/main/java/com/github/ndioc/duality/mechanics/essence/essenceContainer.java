package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import net.minecraft.util.math.BlockPos;

public interface essenceContainer {

  essence createEssenceObject(essenceType type, int capacity);
  void deleteEssenceObject(int arraynum);

  EssenceContainerEntity getContainerEntity(BlockPos position);

  int removeEssence(essenceType type, int amount);
  int addEssence(essenceType type, int amount);


}
