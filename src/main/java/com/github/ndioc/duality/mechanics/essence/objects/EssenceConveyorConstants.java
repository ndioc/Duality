package com.github.ndioc.duality.mechanics.essence.objects;

public class EssenceConveyorConstants {
  private final int AmountPerTransfer;
  private final int TicksBetweenTransfers;

  private final float TravelSpeedMult;
  private final float DistanceBeforeLossMult;

  private final int MaxSources;
  private final int MaxDestinations;

  public EssenceConveyorConstants(int AmountPerTransfer, int TicksbetweenTransfers, float TravelSpeedMult, float DistanceBeforeLossMult, int MaxSources, int MaxDestinations) {
    this.AmountPerTransfer = AmountPerTransfer;
    this.TicksBetweenTransfers = TicksbetweenTransfers;
    this.TravelSpeedMult = TravelSpeedMult;
    this.DistanceBeforeLossMult = DistanceBeforeLossMult;
    this.MaxSources = MaxSources;
    this.MaxDestinations = MaxDestinations;
  }

  public int getAmountPerTransfer() {
    return this.AmountPerTransfer;
  }

  public int getTicksBetweenTransfers() {
    return this.TicksBetweenTransfers;
  }

  public float getTravelSpeedMult() {
    return this.TravelSpeedMult;
  }

  public float getDistanceBeforeLossMult() {
    return this.DistanceBeforeLossMult;
  }

  public int getMaxSources() {
    return this.MaxSources;
  }

  public int getMaxDestinations() {
    return this.MaxDestinations;
  }
}
