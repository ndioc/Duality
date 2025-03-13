package com.github.ndioc.duality.client.animation;

import net.minecraft.client.util.math.MatrixStack;

public class MatrixStackBuilder {

  public static MatrixStack containerMatrixStack = new MatrixStack();

  public static void build() {
    assembleContainerMatrixStack();
  }

  public static void assembleContainerMatrixStack() {

    containerMatrixStack.push();
  }

}
