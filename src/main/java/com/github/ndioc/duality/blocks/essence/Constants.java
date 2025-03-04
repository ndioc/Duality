package com.github.ndioc.duality.blocks.essence;

import com.github.ndioc.duality.mechanics.essence.essenceType;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceContainerConstants;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceConveyorConstants;

public class Constants {

  public static EssenceContainerConstants fetchContainerConstants(String TranslationKey) {
    return switch (TranslationKey) {
      case "block.duality.test_orb" -> new EssenceContainerConstants(64000, 320, 3, false, new int[]{-1, 0, 1});
      case "block.duality.creative_orb" -> new EssenceContainerConstants(256000, 1280, essenceType.getEssenceTypeCount(), true, essenceType.getAllEssenceTypeIDs());
      default -> null;
    };
  }

  public static EssenceConveyorConstants fetchConveyorConstants(String TranslationKey) {
    return switch (TranslationKey) {
      case "block.duality.test_relay" -> new EssenceConveyorConstants(60, 5, 1f, 1f, 2, 3);
      case "block.duality.long_range_test_relay" -> new EssenceConveyorConstants(400, 40, 1.25f, 2f, 1, 1);
      case "block.duality.yeeter_test" -> new EssenceConveyorConstants(3200, 300, 3f, 5f, 1, 1);
      default -> null;
    };
  }
}
