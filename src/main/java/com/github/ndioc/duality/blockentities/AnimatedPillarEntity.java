package com.github.ndioc.duality.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AnimatedPillarEntity extends BlockEntity {

  public String axis = "y";
  public int index = 0;
  public boolean trigger = false;
  public int tickssincelast = 0;

  @Override
  public void writeNbt(NbtCompound data) {
    data.putInt("index", index);
    data.putString("axis", axis);
    data.putBoolean("trigger", trigger);
    data.putInt("tickssincelast", tickssincelast);
    super.writeNbt(data);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    index = nbt.getInt("index");
    axis = nbt.getString("axis");
    trigger = nbt.getBoolean("trigger");
    tickssincelast = nbt.getInt("tickssincelast");
    super.readNbt(nbt);
  }

  public void setIndex(int index) {
    this.index = index;
    markDirty();
  }

  public int getIndex() {
    return index;
  }

  public void setAxis(String axis) {
    this.axis = axis;
    markDirty();
  }

  public String getAxis() {
    return axis;
  }

  public AnimatedPillarEntity(BlockPos position, BlockState state) {
    super(blockentitytypes.ANIMATED_PILLAR, position, state);
  }

  public static void tick(World world, BlockPos position, BlockState state, AnimatedPillarEntity entity) {
    entity.tickssincelast++;

    if(entity.tickssincelast < 10) {
      entity.tickssincelast = 0;
    }

  }

}


