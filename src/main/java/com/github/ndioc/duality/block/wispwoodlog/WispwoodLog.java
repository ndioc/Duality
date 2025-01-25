package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentitytypes.AnimatedPillarEntity;
import com.github.ndioc.duality.blockentitytypes.blockentitytypes;
import com.github.ndioc.duality.utilities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.gen.stateprovider.PillarBlockStateProvider;
import org.jetbrains.annotations.Nullable;

public class WispwoodLog extends PillarBlock implements BlockEntityProvider {

  public static final BooleanProperty ANIMATED = BooleanProperty.of("animated");

  public WispwoodLog(Settings settings) {
    super(settings);
    setDefaultState(getDefaultState().with(ANIMATED, false));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(ANIMATED);
    builder.add(Properties.AXIS);
  }

  @Override
  public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
    super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    world.getBlockEntity();

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
