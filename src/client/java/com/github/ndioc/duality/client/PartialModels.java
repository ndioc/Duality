package com.github.ndioc.duality.client;

import com.github.ndioc.duality.Duality;
import dev.engine_room.flywheel.api.material.Material;
import dev.engine_room.flywheel.lib.material.Materials;
import dev.engine_room.flywheel.lib.model.SimpleModel;
import dev.engine_room.flywheel.lib.model.baked.BakedModelBuilder;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.Identifier;

public class PartialModels {

  public static SimpleModel[] WispwoodVeinModels;
  public static SimpleModel[] SelectionOutlineModels;

  public static SimpleModel OrbContainerModel;



  public static void fetch() {
    createModels();
    createArrays();
  }

  private static void createModels() {
    OrbContainerModel = applyMaterialToModel(PartialModel.of(Identifier.of(Duality.MOD_ID, "block/partials/orb_container")), Materials.CUTOUT_BLOCK);
  }

  private static void createArrays() {
    WispwoodVeinModels = createBlockModelArray("partials/log_veins/wispwood/", 7, Materials.SOLID_BLOCK);
    SelectionOutlineModels = createBlockModelArray("partials/selection_outlines/", 4, Materials.TRANSLUCENT_UNSHADED_BLOCK);
  }

  private static SimpleModel[] createBlockModelArray(String path, int length, Material materialToApply) {
    SimpleModel[] output = new SimpleModel[length];
    for (int x = 0; x < output.length; x++) {
      output[x] = applyMaterialToModel(PartialModel.of(Duality.asResourcePath("block/" + path + x)), materialToApply);
    }
    return output;
  }

  private static SimpleModel applyMaterialToModel(PartialModel inputModel, Material materialToApply) {
    return BakedModelBuilder.create(inputModel.get()).materialFunc((renderLayer, aBoolean) -> materialToApply).build();
  }

}