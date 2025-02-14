package com.github.ndioc.duality.mechanics.essence;

public enum essenceType implements essenceCore {

  HARMONIOUS(0.98f, 1f, 48, 0),
  BLISS(0.90f, 1.5f, 24, 2),
  AGONY(0.85f, 0.5f, 16, -2);

  private final float baseEfficiencyMult;
  private final float baseTravelSpeed;

  private final int baseDistanceBeforeLoss;
  private final int effectOnLife;

  essenceType(

      final float baseEfficiencyMult,
      final float baseTravelSpeed,

      final int baseDistanceBeforeLoss,
      final int effectOnLife

  ) {

    this.baseEfficiencyMult = baseEfficiencyMult;
    this.baseTravelSpeed = baseTravelSpeed;
    this.baseDistanceBeforeLoss = baseDistanceBeforeLoss;
    this.effectOnLife = effectOnLife;

  }

  @Override
  public essenceType getEssenceType() {
    return this;
  }

  @Override
  public float getbaseEfficiencyMult(essenceType type) {
    return type.baseEfficiencyMult;
  }

  @Override
  public float getbaseTravelSpeed(essenceType type) {
    return type.baseTravelSpeed;
  }

  @Override
  public int getbaseDistanceBeforeLoss(essenceType type) {
    return type.baseDistanceBeforeLoss;
  }

  @Override
  public int geteffectOnLife(essenceType type) {
    return type.effectOnLife;
  }

}
