package com.github.ndioc.duality.mechanics.essence;

public enum essenceType implements essenceCore {

  HARMONIOUS(0, 0.98f, 1f, 48, 0),
  BLISS(1, 0.90f, 1.5f, 24, 2),
  AGONY(-1, 0.85f, 0.5f, 16, -2);

  private final int numericalID;

  private final float baseEfficiencyMult;
  private final float baseTravelSpeed;

  private final int baseDistanceBeforeLoss;
  private final int effectOnLife;

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

  public int getNumericalID() {
    return this.numericalID;
  }

  public essenceType getEssenceType() {
    return this;
  }

  public static essenceType getEssenceTypeByID(int ID) {
    return switch (ID) {
      case -1 -> essenceType.AGONY;
      case 0 -> essenceType.HARMONIOUS;
      case 1 -> essenceType.BLISS;
      default -> null;
    };
  }
  public static int[] getAllEssenceTypeIDs() {
    return new int[]{-1, 0, 1};
  }

  public static int getEssenceTypeCount() {
    return essenceType.values().length;
  }

  public float getbaseEfficiencyMult() {
    return this.baseEfficiencyMult;
  }

  public float getbaseTravelSpeed() {
    return this.baseTravelSpeed;
  }

  public int getbaseDistanceBeforeLoss() {
    return this.baseDistanceBeforeLoss;
  }

  public int geteffectOnLife() {
    return this.effectOnLife;
  }

}
