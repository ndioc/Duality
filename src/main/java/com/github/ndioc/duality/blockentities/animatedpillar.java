package com.github.ndioc.duality.blockentities;


import com.github.ndioc.duality.block.wispwood_log;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.PillarBlockStateProvider;

import static net.minecraft.block.PillarBlock.AXIS;

public class animatedpillar extends BlockEntity {

  public String axis = wispwood_log.AXIS.toString();
  public int index = 0;

  public animatedpillar(BlockPos position, BlockState state) {
    super(blockentitytypes.ANIMATED_PILLAR, position, state);
  }

  @Override
  public void writeNbt(NbtCompound data) {
    data.putInt("index", index);
    data.putString("axis", axis);
    super.writeNbt(data);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);

    index = nbt.getInt("number");
    axis = nbt.getString("axis");
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

}


