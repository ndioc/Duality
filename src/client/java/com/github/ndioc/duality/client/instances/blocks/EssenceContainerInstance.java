package com.github.ndioc.duality.client.instances.blocks;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.blocks.blocks;
import com.github.ndioc.duality.blocks.miscellaneous.SelectionOutline;
import com.github.ndioc.duality.client.animation.AnimationBuilder;
import com.github.ndioc.duality.client.animation.objects.AnimationData;
import com.github.ndioc.duality.main;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import net.minecraft.util.math.Direction;

import static com.github.ndioc.duality.blocks.blocks.SELECTION_OUTLINE;

public class EssenceContainerInstance extends BlockEntityInstance<EssenceContainerEntity> implements DynamicInstance  {

  private ModelData containerModel;
  private final AnimationData containerAnimation;
  private ModelData selectionModel;

  private long startFrame;
  private boolean isActivated = false;

  public EssenceContainerInstance(MaterialManager matMan, EssenceContainerEntity blockEntity) {
    super(matMan, blockEntity);

    containerModel = matMan.defaultCutout()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.ORB_CONTAINER.getDefaultState())
        .createInstance();

    containerModel.loadIdentity()
        .translate(getInstancePosition());

    containerAnimation = AnimationBuilder.ContainerAnimation;

    selectionModel = matMan.defaultCutout()
        .material(Materials.TRANSFORMED)
        .getModel(SELECTION_OUTLINE.getDefaultState())
        .createInstance();

    selectionModel.loadIdentity()
        .translate(getInstancePosition())
        .setBlockLight(15);

  }

  public void beginFrame() {
    containerModel.loadIdentity();
    containerModel.translate(getInstancePosition());
    if (!isActivated) {
      int frame = containerAnimation.getCurrentFrame(startFrame);
      if (frame > containerAnimation.getTotalFrames() - 1) {
        frame = 0;
        startFrame = System.currentTimeMillis();
      }
      main.LOGGER.info("frame: {}", frame);
      containerModel.translate(containerAnimation.getFramePosData(frame));
      containerModel.rotateCentered(Direction.UP, containerAnimation.getFrameRotationDataUP(frame));
      containerModel.rotateCentered(Direction.NORTH, containerAnimation.getFrameRotationDataNORTH(frame));
      containerModel.rotateCentered(Direction.EAST, containerAnimation.getFrameRotationDataEAST(frame));
    }

    materialManager.defaultTransparent()
        .material(Materials.TRANSFORMED)
        .getModel(SELECTION_OUTLINE.getDefaultState().with(SelectionOutline.SELECTED, blockEntity.getCurrentSelection()))
        .stealInstance(selectionModel);

    selectionModel.loadIdentity()
        .translate(getInstancePosition())
        .setBlockLight(15);

  }

  protected void remove() {
    containerModel.delete();
    selectionModel.delete();
  }

  public void updateLight() {
    relight(getWorldPosition(), containerModel);
  }
}
