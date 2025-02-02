package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentities.AnimatedPillarEntity;
import com.github.ndioc.duality.blockentitytypes;
import com.github.ndioc.duality.utilities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
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
    world.getBlockEntity(pos, blockentitytypes.ANIMATED_PILLAR).ifPresent(AnimatedPillarEntity::onblockupdate);
    world.updateNeighbor(pos.offset(state.get(AXIS), 1),state.getBlock(), pos);
  }

  @Override
  public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
    super.onPlaced(world, pos, state, placer, itemStack);
    world.updateNeighbor(pos.offset(state.get(AXIS), 1),state.getBlock(), pos);
    world.updateNeighbor(pos.offset(state.get(AXIS), -1),state.getBlock(), pos);
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new AnimatedPillarEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return utilities.validateTicker(type, blockentitytypes.ANIMATED_PILLAR, AnimatedPillarEntity::tick);
  }

}
