package com.github.ndioc.duality.client.animation;

import com.github.ndioc.duality.client.animation.objects.AnimationData;
import com.github.ndioc.duality.main;
import net.minecraft.util.math.Vec3d;

public class AnimationBuilder {

  public static AnimationData ContainerAnimation;

  public static void build() {
    long start = System.currentTimeMillis();
    assembleContainerAnimation();
    main.LOGGER.info("{} successfully built animations, took {} milliseconds.", main.MOD_NAME,System.currentTimeMillis() - start);
    AnimationsToPrint();
  }

  public static void AnimationsToPrint() {
  }

  public static void assembleContainerAnimation() {
    int FPS = 60;
    int degreesPerCalculation = 20;
    int totalFrames = 534;
    float baseRotation = 26.6966f;

    Vec3d[] PosData = new Vec3d[totalFrames];
    Vec3d[] RotData = new Vec3d[totalFrames];

    PosData[totalFrames - 1] = new Vec3d(0d, 0d, 0d);
    RotData[totalFrames - 1] = new Vec3d(0d, 0d, 0d);

    for (int x = 0; x < totalFrames - 1; x++) {
      double rotation = (baseRotation  / (180 * Math.PI)) * x;
      PosData[x] = new Vec3d(0d, (Math.sin((x * degreesPerCalculation) / (180 * Math.PI)) / 10d), 0d);
      RotData[x] = new Vec3d(rotation, (rotation * 0.5f), (rotation * 0.25f) * -1);
    }
    ContainerAnimation = new AnimationData(FPS, PosData, RotData);
    for (int x = 0; x < PosData.length ; x++) {
      main.LOGGER.warn("position: {} | frame: {}",PosData[x], x);
      main.LOGGER.warn("rotation: {} | frame: {}",RotData[x], x);
    }
  }

  public static void printAnimationToLog(AnimationData data) {
    for (int x = 0; x < data.getTotalFrames(); x++) {
      main.LOGGER.info("Frame | {}", x);
      main.LOGGER.info("Position Data | {}", data.getFramePosData(x));
      main.LOGGER.info("Rotational Data | {}, {}, {}", data.getFrameRotationDataUP(x), data.getFrameRotationDataNORTH(x), data.getFrameRotationDataEAST(x));
    }
  }

}
