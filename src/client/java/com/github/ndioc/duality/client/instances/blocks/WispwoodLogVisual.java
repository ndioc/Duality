package com.github.ndioc.duality.client.instances.blocks;

import com.github.ndioc.duality.blockentities.animation.AnimatedPillarEntity;
import com.github.ndioc.duality.main;
import dev.engine_room.flywheel.api.backend.BackendManager;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.material.Material;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.backend.Backends;
import dev.engine_room.flywheel.lib.material.SimpleMaterial;
import dev.engine_room.flywheel.lib.model.part.InstanceTree;
import dev.engine_room.flywheel.lib.model.part.ModelTrees;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleTickableVisual;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public class WispwoodLogVisual extends AbstractBlockEntityVisual<AnimatedPillarEntity> implements SimpleTickableVisual {

  private final Material material = SimpleMaterial.builder()
      .texture(new Identifier(main.MOD_ID, ""))
      .mipmap(false)
      .diffuse(true)
      .build();

  private final InstanceTree instanceTree;
  private final InstanceTree veins;

  public WispwoodLogVisual(VisualizationContext ctx, AnimatedPillarEntity blockEntity, float partialTick){
    super(ctx, blockEntity, partialTick);

    instanceTree = InstanceTree.create(instancerProvider(), ModelTrees.of());
    veins = instanceTree.childOrThrow("WispwoodVeins");

    BlockPos pos = getVisualPosition();
      veins.translateAndRotate(new Matrix4f().translate(pos.getX(), pos.getY(), pos.getZ()));
  }

  @Override
  public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {

  }

  @Override
  public void updateLight(float v) {
  }

  @Override
  protected void _delete() {
    veins.delete();
  }

  @Override
  public void tick(Context context) {

    if (BackendManager.currentBackend() == Backends.INDIRECT) {
      veins.delete();
    }

    else {

      final int animationlength = 23;
      final int animationoverlap = 6;
      final int animationpause = 46;

      int frameoffset = (animationlength - animationoverlap) * blockEntity.getIndex();
      int frame = Math.toIntExact((level.getTime() - frameoffset + blockEntity.randomoffset) % (animationlength + animationpause));

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
/*
      materialManager.defaultSolid()
          .material(Materials.TRANSFORMED)
          .getModel(blocks.WISPWOOD_VEIN.getDefaultState().with(WispwoodVein.VEINSTATES, veinmodel))
          .stealInstance(model);

      model.loadIdentity()
          .translate(getInstancePosition())
          .translate(x, y, z)
          .scale(scalex, scaley, scalez)
          .rotateCentered(direction, rotate)
          .setBlockLight(15);
*/
    }

  }
}
