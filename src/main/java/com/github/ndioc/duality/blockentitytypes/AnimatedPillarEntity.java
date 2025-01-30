package com.github.ndioc.duality.blockentitytypes;

import com.github.ndioc.duality.utilities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.minecraft.block.PillarBlock.AXIS;

public class AnimatedPillarEntity extends BlockEntity {

  public String axis = "";
  public int index = 0;

  public boolean firsttick = true;
  public boolean checkindex = true;
  public boolean checkframeoffset = true;

  public int frame = 0;
  public int frameoffset = 0;

  public final int animationlength = 23;
  public final int animationoverlap = 6;
  public final int animationpause = 23;

  @Override
  public void writeNbt(NbtCompound data) {
    data.putInt("index", index);
    data.putString("axis", axis);
    data.putInt("frameoffset", frameoffset);
    super.writeNbt(data);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    index = nbt.getInt("index");
    axis = nbt.getString("axis");
    nbt.getInt("frameoffset");
    super.readNbt(nbt);
  }

  public void checkindex() {
    checkindex = true;
  }

  public boolean compareEntity(String axis, BlockEntity entity) {

    boolean output = false;

    if (entity == null) {
      return output;
    }
    else {
      BlockEntityType<?> typeofme = blockentitytypes.ANIMATED_PILLAR;
      BlockEntityType<?> typeofthem = entity.getType();

      Direction.Axis axisofme = utilities.StringtoAxis(axis);
      NbtCompound nbtofthem = entity.createNbt();
      Direction.Axis axisofthem = utilities.StringtoAxis(nbtofthem.getString("axis"));

      if(axisofthem == null) {
        checkindex = true;
        return output;
      }
      else {
        checkindex = false;
      }
      if (typeofme == typeofthem) {
        if (axisofme == axisofthem) {
          output = true;
        }
      }
      checkframeoffset = true;
      return output;
    }

  }

  public AnimatedPillarEntity(BlockPos position, BlockState state) {
    super(blockentitytypes.ANIMATED_PILLAR, position, state);
  }

  public static void tick(World world, BlockPos position, BlockState state, AnimatedPillarEntity entity) {

    if(entity.checkframeoffset) {
      entity.frameoffset = (entity.animationlength + entity.animationoverlap) * entity.index;
      entity.markDirty();
    }

    entity.frame = Math.toIntExact((world.getTime() + entity.frameoffset) % (entity.animationlength + entity.animationpause));

    if (entity.firsttick) {
      entity.axis = state.get(AXIS).asString();
      entity.checkindex = true;
      entity.firsttick = false;
    }

    if (entity.checkindex) {
      for (int x = 0; x < 256; ) {
        BlockEntity entitytocheck = world.getBlockEntity(position.offset(utilities.StringtoAxis(entity.axis),(x * -1) - 1));
        if (entity.compareEntity(entity.axis, entitytocheck)) {
          x++;
        }
        else {
          entity.index = x;
          break;
        }
      }
      entity.markDirty();
    }

  }

}


