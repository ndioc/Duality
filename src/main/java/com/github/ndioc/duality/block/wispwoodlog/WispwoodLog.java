package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentitytypes.AnimatedPillarEntity;
import com.github.ndioc.duality.blockentitytypes.blockentitytypes;
import com.github.ndioc.duality.utilities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WispwoodLog extends PillarBlock implements BlockEntityProvider {

  public WispwoodLog(Settings settings) {
    super(settings);
  }

  @Override
  public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
    super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    world.getBlockEntity(pos, blockentitytypes.ANIMATED_PILLAR).ifPresent(AnimatedPillarEntity::checkindex);
    world.updateNeighbor(pos.offset(state.get(AXIS), 1),state.getBlock(), pos);
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new AnimatedPillarEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) { //delete this later
    return BlockRenderType.INVISIBLE;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return utilities.validateTicker(type, blockentitytypes.ANIMATED_PILLAR, AnimatedPillarEntity::tick);
  }

}
