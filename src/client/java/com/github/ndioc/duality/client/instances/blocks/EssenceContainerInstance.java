package com.github.ndioc.duality.client.instances.blocks;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.blocks.blocks;
import com.github.ndioc.duality.blocks.miscellaneous.SelectionOutline;
import com.github.ndioc.duality.client.mainClient;
import com.github.ndioc.duality.main;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;

import static com.github.ndioc.duality.blocks.blocks.SELECTION_OUTLINE;

public class EssenceContainerInstance extends BlockEntityInstance<EssenceContainerEntity> implements DynamicInstance  {

  private ModelData containerModel;
  private ModelData selectionModel;

  private double currentY = 0d;

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

    if (!blockEntity.isActivated()) {
      main.LOGGER.info("DEBUG, currentY | {}", currentY);
      containerModel.translate(getWorldPosition());
    }

    else {
      containerModel.translate(0d, currentY * -1, 0d);
      currentY = 0d;
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
