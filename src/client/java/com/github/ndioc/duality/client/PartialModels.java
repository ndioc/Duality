package com.github.ndioc.duality.client;

import com.github.ndioc.duality.Duality;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.util.Identifier;

public class PartialModels {

  public static PartialModel[] WispwoodVeinModels;
  public static PartialModel[] SelectionOutlineModels;

  public static PartialModel OrbContainerModel;

  public static void register() {
    createModels();
    createArrays();
  }

  private static void createArrays() {
    WispwoodVeinModels = createBlockModelArray("/partials/log_veins/wispwood/", 7);
    SelectionOutlineModels = createBlockModelArray("/partials/selection_outlines/", 4);
  }

  private static void createModels() {
    OrbContainerModel = PartialModel.of(Identifier.of(Duality.MOD_ID, "block/partials/orb_container"));
  }

  private static PartialModel[] createBlockModelArray(String path, int length) {
    PartialModel[] output = new PartialModel[length];
    for (int x = 0; x < output.length; x++) {
      output[x] = PartialModel.of(Duality.asResourcePath("block" + path + x));
    }
    return output;
  }

}
