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
  }

  @Override
  public void tick() {

    int frame = blockEntity.frame;

    main.LOGGER.info("frame of wispwood log @ {} is on frame: {}", getInstancePosition().toShortString(), frame);

    int veinmodel = 0;

    switch (frame) {
      case 1,23:
        veinmodel = 0;
        break;
      case 2,22:
        veinmodel = 1;
        break;
      case 3,21:
        veinmodel = 2;
        break;
      case 4,20:
        veinmodel = 3;
        break;
      case 5,19:
        veinmodel = 4;
        break;
      case 6,18:
        veinmodel = 5;
        break;
      case 7,8,9,10,11,12,13,14,15,16,17:
        veinmodel = 6;
    }

    if (frame > 23) {
      model.delete();
    }

    materialManager.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN.getDefaultState().with(WispwoodVein.VEINSTATES, veinmodel))
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
