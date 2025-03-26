package com.github.ndioc.duality.client.blocks.essence.storage.generic;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.client.animation.AnimationBuilder;
import com.github.ndioc.duality.client.animation.objects.AnimationData;
import com.github.ndioc.duality.client.models.SimpleModels;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.SimpleModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;

import java.util.function.Consumer;

public class OrbContainerVisual extends AbstractBlockEntityVisual<EssenceContainerEntity> implements SimpleDynamicVisual {

  private final TransformedInstance container;
  private final OrientedInstance selection;

  private final SimpleModel[] selectionModels = fetchModels();
  private final AnimationData containerAnimation = AnimationBuilder.ContainerAnimation;

  private boolean isActivated = false;
  private long startFrame = 0;
  private int currentSelection = 0;

  public OrbContainerVisual(VisualizationContext ctx, EssenceContainerEntity blockEntity, float partialTick) {
    super(ctx, blockEntity, partialTick);

    container = instancerProvider().instancer(InstanceTypes.TRANSFORMED, SimpleModels.OrbContainerModel)
        .createInstance()
        .translate(getVisualPosition())
        .setIdentityTransform();

    selection = instancerProvider().instancer(InstanceTypes.ORIENTED, selectionModels[0])
        .createInstance()
        .position(getVisualPosition());

    selection.light(15,15);
  }

  private SimpleModel[] fetchModels() {
    if (SimpleModels.SelectionOutlineModels == null || SimpleModels.OrbContainerModel == null) {
      SimpleModels.bakeAllModels();
    }
    return SimpleModels.SelectionOutlineModels;
  }

  @Override
  public void collectCrumblingInstances(Consumer consumer) {
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
    currentSelection = blockEntity.getCurrentSelection();
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
      container.rotateYCentered(containerAnimation.getFrameRotationDataUP(frame));
      container.rotateXCentered(containerAnimation.getFrameRotationDataNORTH(frame));
      container.rotateZCentered(containerAnimation.getFrameRotationDataEAST(frame));
    }

    instancerProvider().instancer(InstanceTypes.ORIENTED, selectionModels[blockEntity.getCurrentSelection()])
        .stealInstance(selection);

    container.setChanged();
    selection.setChanged();
  }
}
