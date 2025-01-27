package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentitytypes.AnimatedPillarEntity;
import com.github.ndioc.duality.blocks;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.AbstractInstancer;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import net.minecraft.data.client.Model;

public class WispwoodLogInstance extends BlockEntityInstance<AnimatedPillarEntity> {

  private final ModelData[] models;

  public WispwoodLogInstance(MaterialManager matMan, AnimatedPillarEntity entity){
    super(matMan, entity);

    long t = world.getTime() % (entity.animationlength + entity.animationpause);
    int x = Math.toIntExact(t);
    int i = entity.index;

    models[0] = matMan.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN())


        .createInstances(models);


      models[x].loadIdentity()
          .translate(getInstancePosition());

  }

  @Override
  public void updateLight() {
    relight(getWorldPosition(), model1);
  }

  @Override
  public void remove(){
    model1.delete();
  }

}
