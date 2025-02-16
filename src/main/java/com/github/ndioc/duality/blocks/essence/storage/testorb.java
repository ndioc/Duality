package com.github.ndioc.duality.blocks.essence.storage;

import com.github.ndioc.duality.blockentities.EssenceContainerEntity;
import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.mechanics.essence.essenceType;
import com.github.ndioc.duality.utilities;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class testorb extends BlockWithEntity {

  public testorb(Settings settings) {
    super(settings);
  }

  @Override
  @Nullable
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new EssenceContainerEntity(pos, state);

  }

  @Override
  public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
    super.onPlaced(world, pos, state, placer, itemStack);
    world.getBlockEntity(pos, blockentitytypes.ESSENCE_CONTAINER).ifPresent(EssenceContainerEntity::setConstantsFromBlock);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    if (!world.isClient()) {
      return utilities.validateTicker(type, blockentitytypes.ESSENCE_CONTAINER, EssenceContainerEntity::tick);
    }
    else {
      return null;
    }
  }
}
