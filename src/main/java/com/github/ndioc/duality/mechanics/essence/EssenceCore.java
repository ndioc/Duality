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

  default int calculateLossPercentage(double rawDistance, int distanceBeforeLoss) {
    double distance = rawDistance - distanceBeforeLoss;
    if (distance <= 0) {
      return 1;
    }
    double lossPercentage = 1.3f * Math.pow(1.3f, distance);
    if (lossPercentage >= 100) {
      return 100;
    }
    if (lossPercentage < 1) {
      return 1;
    }
    return (int) lossPercentage;
  }
}
