package com.github.ndioc.duality.blocks.miscellaneous;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class SelectionOutline extends Block {

  public static final IntProperty SELECTED = IntProperty.of("selected",0, 3);

  public SelectionOutline(Settings settings) {
    super(settings);
    setDefaultState(getDefaultState().with(SELECTED, 0));
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(SELECTED);
  }
}
