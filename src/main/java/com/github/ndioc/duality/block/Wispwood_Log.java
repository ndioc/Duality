package com.github.ndioc.duality.block;

import com.github.ndioc.duality.blockentities.AnimatedPillarEntity;
import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.utilities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Wispwood_Log extends PillarBlock implements BlockEntityProvider {

  public Wispwood_Log(Settings settings) {
    super(settings);
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new AnimatedPillarEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return utilities.validateTicker(type, blockentitytypes.ANIMATED_PILLAR, AnimatedPillarEntity::tick);
  }

}
