package com.github.ndioc.duality.client.util.objects.rendering;

import net.minecraft.client.texture.Sprite;

import java.util.Objects;

public class SpriteData {

  private Sprite sprite;
  private int bakeFlags;

  private float u;
  private float v;

  public SpriteData (Sprite sprite, int bakeFlags, float u, float v) {
    this.sprite = sprite;
    this.bakeFlags = bakeFlags;
    this.u = u;
    this.v = v;
  }

  public Sprite getSprite() {
    return this.sprite;
  }

  public int getBakeFlags() {
    return this.bakeFlags;
  }

  public float getU() {
    return this.u;
  }

  public float getV() {
    return this.v;
  }

  @Override
  public int hashCode() {
    return Objects.hash(sprite, bakeFlags, u, v);
  }
}
