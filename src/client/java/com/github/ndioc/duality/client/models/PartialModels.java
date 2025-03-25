package com.github.ndioc.duality.client.models;

import com.github.ndioc.duality.Duality;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.util.Identifier;

public class PartialModels {

  public static PartialModel[] WispwoodVeinPartials;
  public static PartialModel[] SelectionOutlinePartials;

  public static PartialModel OrbContainerPartials;

  public static void fetch() {
    fetchModels();
    fetchArrays();
  }

  private static void fetchModels() {
    OrbContainerPartials = PartialModel.of(Identifier.of(Duality.MOD_ID, "block/partials/orb_container"));
  }

  private static void fetchArrays() {
    WispwoodVeinPartials = createBlockModelArray("partials/log_veins/wispwood/", 7);
    SelectionOutlinePartials = createBlockModelArray("partials/selection_outlines/", 4);
  }

  private static PartialModel[] createBlockModelArray(String path, int length) {
    PartialModel[] output = new PartialModel[length];
    for (int x = 0; x < output.length; x++) {
      output[x] = PartialModel.of(Duality.asResourcePath("block/" + path + x));
    }
    return output;
  }

}