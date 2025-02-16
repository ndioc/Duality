package com.github.ndioc.duality.mechanics.essence;

public enum essenceType implements essenceCore {

  HARMONIOUS(0, 0.98f, 1f, 48, 0),
  BLISS(1, 0.90f, 1.5f, 24, 2),
  AGONY(-1, 0.85f, 0.5f, 16, -2);

  private final float baseEfficiencyMult;
  private final float baseTravelSpeed;

  private final int baseDistanceBeforeLoss;
  private final int effectOnLife;

  private final int numericalID;

  essenceType(

      final int numericalID,
      final float baseEfficiencyMult,
      final float baseTravelSpeed,

      final int baseDistanceBeforeLoss,
      final int effectOnLife

  ) {

    this.numericalID = numericalID;
    this.baseEfficiencyMult = baseEfficiencyMult;
    this.baseTravelSpeed = baseTravelSpeed;
    this.baseDistanceBeforeLoss = baseDistanceBeforeLoss;
    this.effectOnLife = effectOnLife;

  }

  @Override
  public int getNumericalID() {
    return this.numericalID;
  }

  @Override
  public essenceType getEssenceType() {
    return this;
  }

  @Override
  public float getbaseEfficiencyMult() {
    return this.baseEfficiencyMult;
  }

  @Override
  public float getbaseTravelSpeed() {
    return this.baseTravelSpeed;
  }

  @Override
  public int getbaseDistanceBeforeLoss() {
    return this.baseDistanceBeforeLoss;
  }

  @Override
  public int geteffectOnLife() {
    return this.effectOnLife;
  }

}
