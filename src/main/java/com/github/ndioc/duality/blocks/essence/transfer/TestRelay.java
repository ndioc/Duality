package com.github.ndioc.duality.blocks.essence.transfer;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.blockentities.essence.EssenceConveyorEntity;
import com.github.ndioc.duality.utilities;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TestRelay extends BlockWithEntity {

  public TestRelay(Settings settings) {
    super(settings);
  }

  @Override
  public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new EssenceConveyorEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    if (!world.isClient()) {
      return utilities.validateTicker(type, blockentitytypes.ESSENCE_TRANSFER_ENTITY, EssenceConveyorEntity::tick);
    }
    else {
      return null;
    }
  }
}


