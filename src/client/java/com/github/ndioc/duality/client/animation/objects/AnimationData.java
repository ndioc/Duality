package com.github.ndioc.duality.client.animation.objects;

import net.minecraft.util.math.Vec3d;

public class AnimationData {

  private int FramesPerSecond;
  private Vec3d[] FramePosData;
  private Vec3d[] FrameRotationData;

  public AnimationData(int FramesPerSecond, Vec3d[] FramePosData, Vec3d[] FrameRotationData) {
    this.FramesPerSecond = FramesPerSecond;
    this.FramePosData = FramePosData;
    this.FrameRotationData = FrameRotationData;
  }

  public int getFramesPerSecond() {
    return FramesPerSecond;
  }

  public Vec3d getFramePosData(int frame) {
    return FramePosData[frame];
  }

  public Vec3d getFrameRotationData(int frame) {
    return FrameRotationData[frame];
  }

  public int getCurrentFrame(long StartTime) {
    return Math.round((System.currentTimeMillis() - StartTime) / (1000f / FramesPerSecond));
  }

}
