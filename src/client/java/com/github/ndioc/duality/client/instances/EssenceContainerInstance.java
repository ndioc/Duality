package com.github.ndioc.duality.client.instances;

import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import com.github.ndioc.duality.blocks.blocks;
import com.github.ndioc.duality.blocks.miscellaneous.SelectionOutline;
import com.github.ndioc.duality.main;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import net.minecraft.util.math.BlockPos;

import static com.github.ndioc.duality.blocks.blocks.SELECTION_OUTLINE;

public class EssenceContainerInstance extends BlockEntityInstance<EssenceContainerEntity> implements DynamicInstance  {

  private ModelData containerModel;
  private ModelData selectionModel;

  private float modelOffset = 0.2f;

  public EssenceContainerInstance(MaterialManager matMan, EssenceContainerEntity blockEntity) {
    super(matMan, blockEntity);

    containerModel = matMan.defaultTransparent()
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

    double sine = Math.sin(world.getTime() / 8d);
    double floatingOffset = sine / 128d;
    containerModel.translateY(floatingOffset + modelOffset);

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
  }

}
