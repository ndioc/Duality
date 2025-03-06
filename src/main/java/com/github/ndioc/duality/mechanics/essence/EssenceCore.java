package com.github.ndioc.duality.mechanics.essence;

public interface EssenceCore {

  EssenceType getEssenceType();
  float getbaseEfficiencyMult();
  float getbaseTravelSpeed();
  int getbaseDistanceBeforeLoss();
  int geteffectOnLife();
  int getNumericalID();

}
