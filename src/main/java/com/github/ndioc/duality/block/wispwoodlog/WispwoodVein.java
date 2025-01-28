package com.github.ndioc.duality.block.wispwoodlog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class WispwoodVein extends Block {

  public static final IntProperty VEINSTATES = IntProperty.of("veinstate", 0, 6);

  public WispwoodVein(Settings settings) {
    super(settings);
    setDefaultState(getDefaultState().with(VEINSTATES, 0));
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(VEINSTATES);
  }

}
