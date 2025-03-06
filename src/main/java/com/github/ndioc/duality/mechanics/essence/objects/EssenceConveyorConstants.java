package com.github.ndioc.duality.mechanics.essence.objects;

public enum EssenceConveyorConstants {

  TEST_RELAY(60, 5, 1f, 1f, 2, 3),
  LONG_RANGE(400, 40, 1.25f, 2f, 1, 1),
  YEETER(3200, 300, 3f, 5f, 1, 1);

  private final int AmountPerTransfer;
  private final int TicksBetweenTransfers;

  private final float TravelSpeedMult;
  private final float DistanceBeforeLossMult;

  private final int MaxSources;
  private final int MaxDestinations;

  EssenceConveyorConstants(int AmountPerTransfer, int TicksbetweenTransfers, float TravelSpeedMult, float DistanceBeforeLossMult, int MaxSources, int MaxDestinations) {
    this.AmountPerTransfer = AmountPerTransfer;
    this.TicksBetweenTransfers = TicksbetweenTransfers;
    this.TravelSpeedMult = TravelSpeedMult;
    this.DistanceBeforeLossMult = DistanceBeforeLossMult;
    this.MaxSources = MaxSources;
    this.MaxDestinations = MaxDestinations;
  }

  public int getAmountPerTransfer() {
    return AmountPerTransfer;
  }

  public int getTicksBetweenTransfers() {
    return TicksBetweenTransfers;
  }

  public float getTravelSpeedMult() {
    return TravelSpeedMult;
  }

  public float getDistanceBeforeLossMult() {
    return DistanceBeforeLossMult;
  }

  public int getMaxSources() {
    return MaxSources;
  }

  public int getMaxDestinations() {
    return MaxDestinations;
  }

  public static EssenceConveyorConstants fetchConveyorConstants(String TranslationKey) {
    return switch (TranslationKey) {
      case "block.duality.test_relay" -> TEST_RELAY;
      case "block.duality.long_range_test_relay" -> LONG_RANGE;
      case "block.duality.yeeter_test" -> YEETER;
      default -> null;
    };
  }
}
