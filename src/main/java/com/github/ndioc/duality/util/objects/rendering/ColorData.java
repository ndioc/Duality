package com.github.ndioc.duality.util.objects.rendering;

import java.util.Objects;

public class ColorData {

  private int red;
  private int green;
  private int blue;
  private int alpha;

  public ColorData (int red, int green, int blue, int alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public int getRed() {
    return this.red;
  }

  public int getGreen() {
    return this.green;
  }

  public int getBlue() {
    return this.blue;
  }

  public int getAlpha() {
    return this.alpha;
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue, alpha);
  }
}
