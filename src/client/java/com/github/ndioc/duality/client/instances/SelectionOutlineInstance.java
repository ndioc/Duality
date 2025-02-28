package com.github.ndioc.duality.client.instances;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.blocks.blocks;
import com.github.ndioc.duality.blocks.natural.wispwood.WispwoodVein;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;

import static com.github.ndioc.duality.blocks.blocks.SELECTION_OUTLINE;

public class SelectionOutlineInstance extends BlockEntityInstance<EssenceContainerEntity> {

  private ModelData selectionModel;
  private ModelData containerModel;

  public SelectionOutlineInstance(MaterialManager matMan, EssenceContainerEntity entity) {
    super(matMan, entity);

    selectionModel = matMan.defaultTransparent()
        .material(Materials.TRANSFORMED)
        .getModel(SELECTION_OUTLINE.getDefaultState())
        .createInstance();

    selectionModel.loadIdentity()
        .translate(getInstancePosition())
        .setBlockLight(15);

    containerModel = matMan.defaultTransparent()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN.getDefaultState().with(WispwoodVein.VEINSTATES, 6))
        .createInstance();

    containerModel.loadIdentity()
        .translate(getInstancePosition());
  }

  @Override
  protected void remove() {
    selectionModel.delete();
    containerModel.delete();
  }

  @Override
  public void updateLight() {
    relight(getWorldPosition(), containerModel);
  }
}
