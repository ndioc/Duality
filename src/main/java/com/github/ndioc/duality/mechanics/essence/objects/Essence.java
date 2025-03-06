package com.github.ndioc.duality.mechanics.essence.objects;


import com.github.ndioc.duality.mechanics.essence.EssenceCore;

public class Essence {

  private final EssenceType TYPE;

  private int quantity;
  private final int capacity;
  private boolean unlimited;

  public Essence(EssenceType type, int capacity, boolean unlimited) {
    TYPE = type;
    this.capacity = capacity;
    this.unlimited = unlimited;

    if (unlimited) {
      quantity = this.capacity;
    }
    else {
      quantity = 0;
    }

  }

  public Essence(EssenceType type, int capacity, int quantity) {
    TYPE = type;
    this.capacity = capacity;
    this.quantity = quantity;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public EssenceType getType() {
    return this.TYPE;
  }

  public int addEssence(int input) {
    if (unlimited) {
      return 0;
    }
    int overflow = (quantity + input) - capacity;

    if(overflow > 0) {
      quantity += (input - overflow);
      return overflow;
    }

    else {
      quantity += input;
      return 0;
    }
  }

  public int removeEssence(int input) {
    if (unlimited) {
      return input;
    }
    int undercut = input + (quantity - input);
    if (quantity <= input) {
      quantity -= undercut;
      return undercut;
    }
    else {
      quantity -= input;
      return input;
    }
  }
}
