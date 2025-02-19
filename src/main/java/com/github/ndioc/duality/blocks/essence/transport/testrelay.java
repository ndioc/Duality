package com.github.ndioc.duality.blocks.essence.transport;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class testrelay extends BlockWithEntity {

  public testrelay(Settings settings) {
    super(settings);
  }

  @Override
  public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return null;
  }

  @Override
  public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return super.getTicker(world, state, type);
  }

  @Override
  public @Nullable <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
    return super.getGameEventListener(world, blockEntity);
  }
}
