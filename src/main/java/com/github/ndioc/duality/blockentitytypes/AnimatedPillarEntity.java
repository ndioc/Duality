package com.github.ndioc.duality.blockentitytypes;

import com.github.ndioc.duality.main;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.github.ndioc.duality.block.wispwoodlog.WispwoodLog.ANIMATED;
import static net.minecraft.block.PillarBlock.AXIS;

public class AnimatedPillarEntity extends BlockEntity {

  public String axis = "y";
  public int index = 0;
  public boolean trigger = false;
  public boolean sync = false;
  public boolean firsttick = true;
  public boolean checkindex = true;
  public int tickssincereset = 0;

  public final int animationlength = 23;
  public final int animationoverlap = 4;
  public final int animationpause = 20;

  @Override
  public void writeNbt(NbtCompound data) {
    data.putInt("index", index);
    data.putString("axis", axis);
    super.writeNbt(data);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    index = nbt.getInt("index");
    axis = nbt.getString("axis");
    super.readNbt(nbt);
  }

  public int getIndex() {
    return index;
  }

  public String getAxis() {
    return axis;
  }

  public AnimatedPillarEntity(BlockPos position, BlockState state) {
    super(blockentitytypes.ANIMATED_PILLAR, position, state);
  }

  public static void tick(World world, BlockPos position, BlockState state, AnimatedPillarEntity entity) {
    entity.tickssincereset++;

    if (entity.firsttick) {
      entity.axis = state.get(AXIS).asString();
      entity.checkindex = true;
      entity.firsttick = false;
      entity.markDirty();
    }

    if(entity.checkindex) {
      entity.sync = true;

      entity.checkindex = false;
    }

    if (entity.sync && state.get(ANIMATED)) {
      world.setBlockState(position, state.with(ANIMATED, false));
      entity.sync = false;
      main.LOGGER.info("syncing pillar @ " + position.toShortString());
    }

    if (entity.tickssincereset % ((entity.animationlength - entity.animationoverlap) * entity.index) == 0) {
      entity.trigger = true;
    }

    if (entity.trigger && !state.get(ANIMATED)) {
      world.setBlockState(position, state.with(ANIMATED, true));
      main.LOGGER.info("triggering pillar @ " + position.toShortString());
    }

    if (entity.tickssincereset >= ((entity.animationlength + entity.animationpause) * 512)) {
      entity.tickssincereset = 0;
    }

  }

}


