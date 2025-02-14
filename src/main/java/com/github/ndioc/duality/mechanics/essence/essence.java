package com.github.ndioc.duality.mechanics.essence;

public class essence {

  private final essenceType TYPE;
  private int quantity;
  private final int capacity;

  public essence(essenceType type, int capacity) {
    TYPE = type;
    this.capacity = capacity;
    quantity = 0;
  }

  public int getQuantity() {
    return quantity;
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

}
