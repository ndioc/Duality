package com.github.ndioc.duality.client.models;

import com.github.ndioc.duality.client.models.blockentities.SelectionOutlineModel;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import static com.github.ndioc.duality.main.MOD_ID;

public class registerModels implements ModelLoadingPlugin {

  public static final ModelIdentifier[] WispwoodVeinModels = new ModelIdentifier[7];

  private void createWispwoodModelIDs() {
    final String[] variants = new String[]{"0","1","2","3","4","5","6"};
    for (int x = 0; x < 7; x++) {
      WispwoodVeinModels[x] = new ModelIdentifier(MOD_ID, "wispwood_vein", variants[x]);
    }
  }

  public static final ModelIdentifier SELECTION_OUTLINE_MODEL = new ModelIdentifier(new Identifier(MOD_ID, "selection_outline"),"");

  @Override
  public void onInitializeModelLoader(Context loaderContext) {
    loaderContext.modifyModelOnLoad().register((originalModel, context) -> {
      final Identifier modelID = context.id();
      if (modelID != null && modelID.equals(SELECTION_OUTLINE_MODEL)) {
        return new SelectionOutlineModel();
      }
      else {
        return originalModel;
      }
    });
  }
}
