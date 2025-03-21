package com.github.ndioc.duality.client.blocks.essence.storage.generic;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.client.PartialModels;
import com.github.ndioc.duality.client.animation.AnimationBuilder;
import com.github.ndioc.duality.client.animation.objects.AnimationData;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public class OrbContainerVisual extends AbstractBlockEntityVisual<EssenceContainerEntity> implements SimpleDynamicVisual {

  private final TransformedInstance container;
  private final OrientedInstance selection;

  private final PartialModel[] selectionModels = PartialModels.SelectionOutlineModels;
  private final AnimationData containerAnimation = AnimationBuilder.ContainerAnimation;

  private boolean isActivated = false;
  private long startFrame = 0;
  private int currentSelection = 0;

  public OrbContainerVisual(VisualizationContext ctx, EssenceContainerEntity blockEntity, float partialTick) {
    super(ctx, blockEntity, partialTick);

    container = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(PartialModels.OrbContainerModel))
        .createInstance()
        .translate(getVisualPosition())
        .setIdentityTransform();

    selection = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(selectionModels[0]))
        .createInstance()
        .position(getVisualPosition());
  }

  @Override
  public void collectCrumblingInstances(Consumer consumer) {
    consumer.accept(container);
  }

  @Override
  public void updateLight(float v) {
    relight(getVisualPosition(), container);
  }

  @Override
  protected void _delete() {
    container.delete();
  }

  @Override
  public void beginFrame(Context context) {
    selection.setVisible(currentSelection != 0);

    container.setTransform(container.pose.identity());
    container.translate(getVisualPosition());
    if (!isActivated) {
      int frame = containerAnimation.getCurrentFrame(startFrame);
      if (frame > containerAnimation.getTotalFrames() - 1) {
        frame = 0;
        startFrame = System.currentTimeMillis();
      }
      container.translate(containerAnimation.getFramePosData(frame));
      container.rotateCentered(containerAnimation.getFrameRotationDataUP(frame), Direction.UP);
      container.rotateCentered(containerAnimation.getFrameRotationDataNORTH(frame), Direction.NORTH);
      container.rotateCentered(containerAnimation.getFrameRotationDataEAST(frame), Direction.EAST);
    }

    instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(PartialModels.SelectionOutlineModels[blockEntity.getCurrentSelection()]))
        .stealInstance(selection);

    container.setChanged();
    selection.setChanged();
  }
}
