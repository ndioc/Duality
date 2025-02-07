package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentities.AnimatedPillarEntity;
import com.github.ndioc.duality.blocks;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.TickableInstance;
import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.config.BackendType;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import net.minecraft.util.math.Direction;

public class WispwoodLogInstance extends BlockEntityInstance<AnimatedPillarEntity> implements TickableInstance {

  private final ModelData model;

  public WispwoodLogInstance (MaterialManager matMan, AnimatedPillarEntity entity){
    super(matMan, entity);

    model = matMan.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN.getDefaultState())
        .createInstance();

      model.loadIdentity()
          .translate(getInstancePosition())
          .setBlockLight(15)
          .setSkyLight(15);
  }

  @Override
  public void tick() {

    if (Backend.getBackendType() == BackendType.BATCHING || Backend.getBackendType() == BackendType.OFF) {
      model.delete();
    }

    else {

      final int animationlength = 23;
      final int animationoverlap = 6;
      final int animationpause = 23;

      int frameoffset = (animationlength + animationoverlap) * blockEntity.getIndex();
      int frame = Math.toIntExact((world.getTime() + frameoffset) % (animationlength + animationpause));

      //main.LOGGER.info("Wispwood Log Instance @ {}, frame: {}, frameoffset: {}, index: {}, axis: {}", pos.toShortString(), frame, frameoffset , blockEntity.getIndex(), blockEntity.axis);

      // basic transform variables
      int veinmodel;

      float rotate;

      float x = 0;
      float y = 0;
      float z = 0;

      Direction direction;

      // to stop clipping
      float scalex = 1;
      float scaley = 1;
      float scalez = 1;

      // also to stop clipping
      float offset = 0.0025f;

      if (frame > 15) {
        offset = -0.0025f;
      }

      if (frame > 16) {
        rotate = 3.141592f;
      } else {
        rotate = 0;
      }

      switch (blockEntity.axis) {
        case "x":
          scalex = 0.995f;
          x = offset;
          rotate += 1.570796f;
          direction = Direction.NORTH;

          break;
        case "y":
          scaley = 0.995f;
          y = offset;
          direction = Direction.NORTH;
          break;
        case "z":
          scalez = 0.995f;
          z = offset;
          rotate += 1.570796f;
          direction = Direction.EAST;
          break;
        default:
          scaley = 0.995f;
          y = offset;
          direction = Direction.NORTH;
          break;
      }

      switch (frame) {
        case 1, 22:
          veinmodel = 0;
          break;
        case 2, 21:
          veinmodel = 1;
          break;
        case 3, 20:
          veinmodel = 2;
          break;
        case 4, 19:
          veinmodel = 3;
          break;
        case 5, 18:
          veinmodel = 4;
          break;
        case 6, 17:
          veinmodel = 5;
          break;
        case 7, 8, 9, 10, 11, 12, 13, 14, 15, 16:
          veinmodel = 6;
          switch (blockEntity.axis) {
            case "x":
              x = x + 0.0625f * (frame - 7);
              break;
            case "y":
              y = y + 0.0625f * (frame - 7);
              break;
            case "z":
              z = z + 0.0625f * (frame - 7);
              break;
          }
          break;

        default:
          veinmodel = 0;
          break;
      }

      if (frame > 22) {
        scalex = 0.25f;
        scaley = 0.25f;
        scalez = 0.25f;
      }

      materialManager.defaultSolid()
          .material(Materials.TRANSFORMED)
          .getModel(blocks.WISPWOOD_VEIN.getDefaultState().with(WispwoodVein.VEINSTATES, veinmodel))
          .stealInstance(model);

      model.loadIdentity()
          .translate(getInstancePosition())
          .translate(x, y, z)
          .scale(scalex, scaley, scalez)
          .rotateCentered(direction, rotate)
          .setSkyLight(15);

    }
  }

  @Override
  public void updateLight() {
    relight(getWorldPosition(), model);
  }

  @Override
  public void remove(){
    model.delete();
  }

}
