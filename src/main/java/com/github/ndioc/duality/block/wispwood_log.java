package com.github.ndioc.duality.block;

import com.github.ndioc.duality.blockentities.animatedpillar;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class wispwood_log extends PillarBlock implements BlockEntityProvider {

  public wispwood_log(Settings settings) {
    super(settings);
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new animatedpillar(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

}
