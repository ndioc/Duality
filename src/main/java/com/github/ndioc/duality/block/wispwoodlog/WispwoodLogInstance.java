package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentitytypes.AnimatedPillarEntity;
import com.github.ndioc.duality.blocks;
import com.github.ndioc.duality.main;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.TickableInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;

public class WispwoodLogInstance extends BlockEntityInstance<AnimatedPillarEntity> implements TickableInstance {

  private final ModelData model;

  public WispwoodLogInstance (MaterialManager matMan, AnimatedPillarEntity entity){
    super(matMan, entity);

    int frame = entity.frame;

    model = matMan.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN.getDefaultState())
        .createInstance();


      model.loadIdentity()
          .translate(getInstancePosition());

    main.LOGGER.info("frame of wispwood log @ {} is on frame: {}", getInstancePosition().toShortString(), frame);
  }

  @Override
  public void tick() {

    int frame = blockEntity.frame;


    materialManager.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN.getStateWithProperties(blockState.with(WispwoodVein.VEINSTATES, veinmodel)))
        .stealInstance(model);

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
