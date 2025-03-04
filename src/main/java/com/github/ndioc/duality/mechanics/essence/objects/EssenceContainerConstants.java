package com.github.ndioc.duality.mechanics.essence.objects;

public class EssenceContainerConstants {

  private int VolumePerContainer;
  private int MaxTransferPerSecond;
  private int NumberOfContainers;
  private int[] AllowedEssenceTypes;
  private boolean isUnlimited;

  public EssenceContainerConstants(int VolumePerContainer, int MaxTransferPerSecond, int NumberOfContainers, boolean isUnlimited, int[] AllowedEssenceTypes) {
    this.VolumePerContainer = VolumePerContainer;
    this.MaxTransferPerSecond = MaxTransferPerSecond;
    this.NumberOfContainers = NumberOfContainers;
    this.isUnlimited = isUnlimited;
    this.AllowedEssenceTypes = AllowedEssenceTypes;
  }

  public int getVolumePerContainer() {
    return this.VolumePerContainer;
  }

  public int getMaxTransferPerSecond() {
    return this.MaxTransferPerSecond;
  }

  public int getNumberOfContainers() {
    return this.NumberOfContainers;
  }

  public boolean isUnlimited() {
    return this.isUnlimited;
  }

  public int[] getAllowedEssenceTypes() {
    return this.AllowedEssenceTypes;
  }
}
