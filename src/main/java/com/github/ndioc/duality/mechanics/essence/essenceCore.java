package com.github.ndioc.duality.mechanics.essence;

public interface essenceCore {

  essenceType getEssenceType();
  float getbaseEfficiencyMult(essenceType type);
  float getbaseTravelSpeed(essenceType type);
  int getbaseDistanceBeforeLoss(essenceType type);
  int geteffectOnLife(essenceType type);

}
