package com.github.ndioc.duality.client.animation.objects;

import net.minecraft.util.math.Vec3d;

public class AnimationData {

  private final int FramesPerSecond;
  private final Vec3d[] FramePosData;
  private final Vec3d[] FrameRotationData;

  public AnimationData(int FramesPerSecond, Vec3d[] FramePosData, Vec3d[] FrameRotationData) {
    this.FramesPerSecond = FramesPerSecond;
    this.FramePosData = FramePosData;
    this.FrameRotationData = FrameRotationData;
  }

  public Vec3d getFramePosData(int frame) {
    return FramePosData[frame];
  }

  public float getFrameRotationDataUP(int frame) {
    return (float) FrameRotationData[frame].x;
  }

  public float getFrameRotationDataNORTH(int frame) {
    return (float) FrameRotationData[frame].y;
  }

  public float getFrameRotationDataEAST(int frame) {
    return (float) FrameRotationData[frame].z;
  }

  public int getCurrentFrame(long StartTime) {
    return Math.round((System.currentTimeMillis() - StartTime) / (1000f / FramesPerSecond));
  }

  public int getTotalFrames() {
    return FramePosData.length;
  }

}
