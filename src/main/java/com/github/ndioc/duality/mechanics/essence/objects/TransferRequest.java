package com.github.ndioc.duality.mechanics.essence.objects;

public class TransferRequest {

  private EssenceType type;
  private int quantity;

  public TransferRequest(EssenceType type, int quantity) {
    this.type = type;
    this.quantity = quantity;
  }

  public EssenceType getType() {
    return type;
  }

  public int getQuantity() {
    return quantity;
  }

}
