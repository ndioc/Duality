package com.github.ndioc.duality.mechanics.essence;

import com.github.ndioc.duality.mechanics.essence.objects.EssenceType;

public interface EssenceCore {

  EssenceType getEssenceType();
  float getbaseEfficiencyMult();
  float getTravelSpeedMult();
  int getbaseDistanceBeforeLoss();
  int geteffectOnLife();
  int getNumericalID();

  default float getBaseTravelSpeed() {
    return 2.5f;
  }
}
