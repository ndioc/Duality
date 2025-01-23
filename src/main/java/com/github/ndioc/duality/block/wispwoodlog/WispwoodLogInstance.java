package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentitytypes.AnimatedPillarEntity;
import com.github.ndioc.duality.blocks;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;

public class WispwoodLogInstance extends BlockEntityInstance<AnimatedPillarEntity> {

  private final ModelData model;

  public WispwoodLogInstance(MaterialManager matMan, AnimatedPillarEntity entity){
    super(matMan, entity);

    model = matMan.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_LOG.getDefaultState())
        .createInstance();

    model.loadIdentity()
        .translate(getInstancePosition());
  }

  @Override
  public void updateLight() {
    relight(getWorldPosition(), model);
  }

  @Override
  public void remove(){
    model.delete();
  }

}
