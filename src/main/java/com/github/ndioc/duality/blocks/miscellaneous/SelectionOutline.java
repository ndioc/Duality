package com.github.ndioc.duality.blocks.miscellaneous;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class SelectionOutline extends Block {

  public static final IntProperty SELECTED = IntProperty.of("selected",0, 3);

  public SelectionOutline(Settings settings) {
    super(settings);
    setDefaultState(getDefaultState().with(SELECTED, 0));
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    if (state.get(SELECTED) == 0) {
     return BlockRenderType.INVISIBLE;
    }
    else {
      return BlockRenderType.MODEL;
    }
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(SELECTED);
  }
}
