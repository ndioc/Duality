package com.github.ndioc.duality.client.util.objects.rendering;

import java.util.Objects;

public class SquareData {
  private float left;
  private float bottom;
  private float right;
  private float top;
  private float depth;

  private int vertexIndex;
  private float x;
  private float y;
  private float z;

  public SquareData(float left, float bottom, float right, float top, float depth, int vertexIndex, int x, int y, int z) {
    this.left = left;
    this.bottom = bottom;
    this.right = right;
    this.top = top;
    this.depth = depth;
    this.vertexIndex = vertexIndex;
    this.x = x;
    this.y = y;
    this.z = z;
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

  public int getVertexIndex() {
    return this.vertexIndex;
  }

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }

  public float getZ() {
    return this.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, bottom, right, top, depth);
  }
}
