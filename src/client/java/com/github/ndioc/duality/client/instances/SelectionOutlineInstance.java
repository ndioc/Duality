package com.github.ndioc.duality.client.instances;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.blocks.miscellaneous.SelectionOutline;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.TickableInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import net.minecraft.block.Blocks;

import static com.github.ndioc.duality.blocks.blocks.SELECTION_OUTLINE;

public class SelectionOutlineInstance extends BlockEntityInstance<EssenceContainerEntity> implements TickableInstance {

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
        .getModel(Blocks.GLASS.getDefaultState())
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

  @Override
  public void tick() {

    materialManager.defaultTransparent()
        .material(Materials.TRANSFORMED)
        .getModel(SELECTION_OUTLINE.getDefaultState().with(SelectionOutline.SELECTED, blockEntity.getCurrentSelection()))
        .stealInstance(selectionModel);

    selectionModel.loadIdentity()
        .translate(getInstancePosition())
        .setBlockLight(15);
  }
}
