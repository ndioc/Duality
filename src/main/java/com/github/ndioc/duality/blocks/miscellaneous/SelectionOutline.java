package com.github.ndioc.duality.blocks.miscellaneous;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class SelectionOutline extends Block {

  public static final IntProperty SELECTION = IntProperty.of("selected",0, 2);

  public SelectionOutline(Settings settings) {
    super(settings);
    setDefaultState(getDefaultState().with(SELECTION, 0));
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(SELECTION);
  }
}
