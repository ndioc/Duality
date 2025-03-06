package com.github.ndioc.duality.mechanics.selection;

public enum SelectionStates {

  TARGETING(new int[]{0, 1, 2, 3});

  private final int[] states;

  SelectionStates(int[] states) {
    this.states = states;
  }

  public int[] getStates() {
    return states;
  }

  public static SelectionStates fetchSelectionStates(String TranslationKey) {
    return switch (TranslationKey) {
      case "block.duality.test_orb", "block.duality.creative_orb" -> TARGETING;
      default -> null;
    };
  }
}
