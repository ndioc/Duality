package com.github.ndioc.duality.blocks.essence.storage;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class creativeorb extends BlockWithEntity {

  public creativeorb(Settings settings) {
    super(settings);
  }

  @Override
  @Nullable
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new EssenceContainerEntity(pos, state);
  }
}
