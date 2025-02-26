package com.github.ndioc.duality.util.objects.rendering;

import java.util.Objects;

public class QuadData {

  private SquareData squareData;
  private ColorData colorData;
  private SpriteData spriteData;

  public QuadData(SquareData squareData, ColorData colorData, SpriteData spriteData) {
    this.squareData = squareData;
    this.colorData = colorData;
    this.spriteData = spriteData;
  }

  public SquareData getSquareData() {
    return this.squareData;
  }

  public ColorData getColorData() {
    return this.colorData;
  }

  public SpriteData getSpriteData() {
    return this.spriteData;
  }

  public int hashCode() {
    return Objects.hash(squareData, colorData, spriteData);
  }

}
