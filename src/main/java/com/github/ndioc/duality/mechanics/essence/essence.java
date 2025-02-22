package com.github.ndioc.duality.mechanics.essence;


public class essence {

  private final essenceType TYPE;
  private final int ID;

  private int quantity;
  private final int capacity;

  public essence(essenceType type, int capacity, boolean unlimited) {
    TYPE = type;
    ID = type.getNumericalID();
    this.capacity = capacity;

    if (unlimited) {
      quantity = this.capacity;
    }
    else {
      quantity = 0;
    }

  }

  public essence(essenceType type, int capacity, int Q, boolean unlimited) {
    TYPE = type;
    ID = type.getNumericalID();
    this.capacity = capacity;

    if (unlimited) {
      quantity = this.capacity;
    }
    else {
      quantity = Q;
    }
  }

  public int getQuantity() {
    return quantity;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public int getID() {
    return this.ID;
  }

  public essenceType getType() {
    return this.TYPE;
  }

  public int addEssence(int input) {
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

  public void forceSetQuantity(int amount) {
    quantity = amount;
  }

}
