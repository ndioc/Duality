package com.github.ndioc.duality.client.blocks.natural.logs.wispwood;

import com.github.ndioc.duality.blockentities.animation.AnimatedPillarEntity;
import com.github.ndioc.duality.client.PartialModels;
import com.github.ndioc.duality.client.animation.AnimationBuilder;
import com.github.ndioc.duality.client.animation.objects.AnimationData;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleTickableVisual;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class WispwoodLogVisual extends AbstractBlockEntityVisual<AnimatedPillarEntity> implements SimpleTickableVisual {

  private final TransformedInstance veins;

  private final AnimationData veinAnimation = AnimationBuilder.WispwoodLogAnimation;
  private final PartialModel[] partialModels = PartialModels.WispwoodVeinModels;

  public WispwoodLogVisual(VisualizationContext ctx, AnimatedPillarEntity blockEntity, float partialTick){
    super(ctx, blockEntity, partialTick);

    veins = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(partialModels[0]))
        .createInstance()
        .translate(getVisualPosition())
        .scale(0.9999f)
        .rotateTo(Direction.UP, getInitialRotation())
        .setIdentityTransform();

    veins.light(15,15);
    veins.setChanged();

  }

  private Direction getInitialRotation() {
    return switch (blockState.get(Properties.AXIS)) {
      case X -> Direction.NORTH;
      case Y -> Direction.UP;
      case Z -> Direction.EAST;
    };
  }

  private PartialModel getModel(int frame) {
    return switch (frame) {
      case 0,22 ->partialModels[0];
      case 1,21 -> partialModels[1];
      case 2,20 -> partialModels[2];
      case 3,19 -> partialModels[3];
      case 4,18 -> partialModels[4];
      case 5,17 -> partialModels[5];
      default -> partialModels[6];
    };
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
      veins.setTransform(veins.pose.identity());
      veins.translate(getVisualPosition());

      final int animationoverlap = 6;
      final int animationpause = 46;

      int frameoffset = ((veinAnimation.getTotalFrames() - animationpause) - animationoverlap) * blockEntity.getIndex();
      int frame = Math.toIntExact((level.getTime() - frameoffset + blockEntity.randomoffset) % (veinAnimation.getTotalFrames()));

      // basic transform variables
      PartialModel veinmodel = getModel(frame);

    veins.setVisible(frame <= 22);

      instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(veinmodel))
          .stealInstance(veins);

      veins.translate(veinAnimation.getFramePosData(frame));
      switch (blockEntity.axis) {
        case "y": veins.rotateXCentered(veinAnimation.getFrameRotationDataUP(frame)); break;
        case "z": veins.rotateYCentered(veinAnimation.getFrameRotationDataNORTH(frame)); break;
        case "x": veins.rotateZCentered(veinAnimation.getFrameRotationDataEAST(frame)); break;
      }
      veins.light(15, 15);
      veins.setChanged();
  }
}
