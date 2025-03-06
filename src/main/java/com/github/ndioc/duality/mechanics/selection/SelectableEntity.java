package com.github.ndioc.duality.mechanics.selection;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public interface SelectableEntity {

  int getCurrentSelection();
  void setSelection(int selection);
  SelectionStates getSelectionStates();

  default void cycleSelection() {
    int current = getCurrentSelection();
    int[] states = getSelectionStates().getStates();
    for (int x = 0; x < states.length; x++) {
      if (current == states[x]) {
        if (x == states.length - 1) {
          setSelection(states[0]);
        }
        else {
          setSelection(states[x + 1]);
        }
      }
    }
  }
}
