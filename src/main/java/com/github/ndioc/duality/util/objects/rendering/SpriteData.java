package com.github.ndioc.duality.util.objects.rendering;

import net.minecraft.client.texture.Sprite;
import java.util.Objects;

public class SpriteData {

  private Sprite sprite;
  private int bakeFlags;

  private int vertexIndex;
  private float u;
  private float v;

  public SpriteData (Sprite sprite, int bakeFlags, int vertexIndex, float u, float v) {
    this.sprite = sprite;
    this.bakeFlags = bakeFlags;
    this.vertexIndex = vertexIndex;
    this.u = u;
    this.v = v;
  }

  public Sprite getSprite() {
    return this.sprite;
  }

  public int getBakeFlags() {
    return this.bakeFlags;
  }

  public int getVertexIndex() {
    return this.vertexIndex;
  }

  public float getU() {
    return this.u;
  }

  public float getV() {
    return this.v;
  }

  @Override
  public int hashCode() {
    return Objects.hash(sprite, bakeFlags, vertexIndex, u, v);
  }
}
