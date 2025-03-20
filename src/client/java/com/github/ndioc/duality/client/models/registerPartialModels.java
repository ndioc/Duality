package com.github.ndioc.duality.client.models;

import com.github.ndioc.duality.main;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.util.Identifier;

public class registerPartialModels {

  public static PartialModel[] WispwoodVeinModels;
  public static PartialModel[] SelectionOutlineModels;

  public static PartialModel OrbContainerModel;

  public static void register() {
    createModels();
    createArrays();
  }

  private static void createArrays() {
    WispwoodVeinModels = createModelArray("model\\partials\\log_veins\\wispwood\\vein_", 7);
    SelectionOutlineModels = createModelArray("model\\partials\\selection_outline\\", 4);
  }

  private static void createModels() {
    OrbContainerModel = PartialModel.of(Identifier.of(main.MOD_ID, "model\\partials\\orb_container"));
  }

  private static PartialModel[] createModelArray(String path, int length) {
    PartialModel[] output = new PartialModel[length];
    for (int x = 0; x < output.length; x++) {
      output[x] = PartialModel.of(Identifier.of(main.MOD_ID, path + x));
    }
    return output;
  }

}
