package com.github.ndioc.duality.util.objects.rendering;

import java.util.Objects;

public class SquareData {
  private float left;
  private float bottom;
  private float right;
  private float top;
  private float depth;

  public SquareData(float left, float bottom, float right, float top, float depth) {
    this.left = left;
    this.bottom = bottom;
    this.right = right;
    this.top = top;
    this.depth = depth;
  }

  public float getLeft() {
  return this.left;
  }

  public float getBottom() {
    return this.bottom;
  }

  public float getRight() {
    return this.right;
  }

  public float getTop() {
    return this.top;
  }

  public float getDepth() {
    return this.depth;
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, bottom, right, top, depth);
  }
}
