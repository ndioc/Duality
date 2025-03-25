package com.github.ndioc.duality.client.models;

import dev.engine_room.flywheel.api.material.Material;
import dev.engine_room.flywheel.lib.material.Materials;
import dev.engine_room.flywheel.lib.model.SimpleModel;
import dev.engine_room.flywheel.lib.model.baked.BakedModelBuilder;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class SimpleModels {

  public static SimpleModel[] WispwoodVeinModels;
  public static SimpleModel[] SelectionOutlineModels;

  public static SimpleModel OrbContainerModel;

  public static void bakeAllModels() {
      WispwoodVeinModels = configurePartialArray(PartialModels.WispwoodVeinPartials, Materials.SOLID_BLOCK);
      SelectionOutlineModels = configurePartialArray(PartialModels.SelectionOutlinePartials, Materials.TRANSLUCENT_UNSHADED_BLOCK);
      OrbContainerModel = applyMaterialToModel(PartialModels.OrbContainerPartials, Materials.CUTOUT_BLOCK);
  }

  public static SimpleModel applyMaterialToModel(PartialModel inputModel, Material materialToApply) {
    return BakedModelBuilder.create(inputModel.get()).materialFunc((renderLayer, aBoolean) -> materialToApply).build();
  }

  private static SimpleModel[] configurePartialArray(PartialModel[] inputModels, Material materialToApply) {
    SimpleModel[] outputModels = new SimpleModel[inputModels.length];
    for (int x = 0; x < outputModels.length; x++) {
      outputModels[x] = applyMaterialToModel(inputModels[x], materialToApply);
    }
    return outputModels;
  }

}
