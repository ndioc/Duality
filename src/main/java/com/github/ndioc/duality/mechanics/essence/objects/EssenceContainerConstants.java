package com.github.ndioc.duality.mechanics.essence.objects;

import com.github.ndioc.duality.mechanics.essence.EssenceType;

public enum EssenceContainerConstants {

  TEST_ORB(64000, 320, 3, false, new int[]{-1, 0, 1}),
  CREATIVE_ORB(256000, 1280,EssenceType.getEssenceTypeCount(), true, EssenceType.getAllEssenceTypeIDs());

  private int VolumePerContainer;
  private int MaxTransferPerSecond;
  private int NumberOfContainers;
  private int[] AllowedEssenceTypes;
  private boolean isUnlimited;

  EssenceContainerConstants(int VolumePerContainer, int MaxTransferPerSecond, int NumberOfContainers, boolean isUnlimited, int[] AllowedEssenceTypes) {
    this.VolumePerContainer = VolumePerContainer;
    this.MaxTransferPerSecond = MaxTransferPerSecond;
    this.NumberOfContainers = NumberOfContainers;
    this.isUnlimited = isUnlimited;
    this.AllowedEssenceTypes = AllowedEssenceTypes;
  }

  public int getVolumePerContainer() {
    return VolumePerContainer;
  }

  public int getMaxTransferPerSecond() {
    return MaxTransferPerSecond;
  }

  public int getNumberOfContainers() {
    return NumberOfContainers;
  }

  public boolean isUnlimited() {
    return isUnlimited;
  }

  public int[] getAllowedEssenceTypes() {
    return AllowedEssenceTypes;
  }

  public static EssenceContainerConstants fetchContainerConstants(String TranslationKey) {
    return switch (TranslationKey) {
      case "block.duality.test_orb" -> TEST_ORB;
      case "block.duality.creative_orb" -> CREATIVE_ORB;
      default -> null;
    };
  }
}
