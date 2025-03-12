package com.github.ndioc.duality.client.instances.blocks;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.blocks.blocks;
import com.github.ndioc.duality.blocks.miscellaneous.SelectionOutline;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;

import static com.github.ndioc.duality.blocks.blocks.SELECTION_OUTLINE;

public class EssenceContainerInstance extends BlockEntityInstance<EssenceContainerEntity> implements DynamicInstance  {

  private ModelData containerModel;
  private ModelData selectionModel;

  private int frame = 0; //mainClient.random.nextInt(90);

  private final float rotationPerFrame = 0.05454f; // ~ 3.125 deg per frame

  public EssenceContainerInstance(MaterialManager matMan, EssenceContainerEntity blockEntity) {
    super(matMan, blockEntity);

    containerModel = matMan.defaultCutout()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.ORB_CONTAINER.getDefaultState())
        .createInstance();

    containerModel.loadIdentity()
        .translate(getInstancePosition());

    selectionModel = matMan.defaultTransparent()
        .material(Materials.TRANSFORMED)
        .getModel(SELECTION_OUTLINE.getDefaultState())
        .createInstance();

    selectionModel.loadIdentity()
        .translate(getInstancePosition())
        .setBlockLight(15);

  }

  public void beginFrame() {

    if (frame > Integer.MAX_VALUE * 0.99f) {
      frame = 0;
    }

    if (!blockEntity.isActivated()) {
      double sine = Math.sin(frame / 10f);
      double floatingOffset = sine * 0.0078125d; // sine / 128
      containerModel.translateY(floatingOffset);
      //containerModel.rotateCentered(Direction.UP, rotationPerFrame * 0.5f);
      //containerModel.rotateCentered(Direction.NORTH, rotationPerFrame * -0.37f);
      //containerModel.rotateCentered(Direction.EAST, rotationPerFrame * 0.21f);
    }

    materialManager.defaultTransparent()
        .material(Materials.TRANSFORMED)
        .getModel(SELECTION_OUTLINE.getDefaultState().with(SelectionOutline.SELECTED, blockEntity.getCurrentSelection()))
        .stealInstance(selectionModel);

    selectionModel.loadIdentity()
        .translate(getInstancePosition())
        .setBlockLight(15);

    frame++;
  }

  protected void remove() {
    containerModel.delete();
    selectionModel.delete();
  }

  public void updateLight() {
    relight(getWorldPosition(), containerModel);
  }
}
