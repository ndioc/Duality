package com.github.ndioc.duality.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class animatedpillar extends BlockEntity {

  public int index = 0;

  public animatedpillar(BlockPos position, BlockState state) {
    super(blockentitytypes.ANIMATED_PILLAR, position, state);
  }

  @Override
  public void writeNbt(NbtCompound data) {
    data.putInt("index", index);

    super.writeNbt(data);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);

    index = nbt.getInt("number");
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public void triggeranimation(){
      
  }

}


