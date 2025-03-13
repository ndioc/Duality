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
  }

  public static void assembleContainerAnimation() {
    int FPS = 60;
    int degreesPerCalculation = 20;
    int totalFrames = 180;

    Vec3d[] PosData = new Vec3d[totalFrames];
    Vec3d[] RotData = new Vec3d[totalFrames];

    PosData[0] = new Vec3d(0d, 0d, 0d);
    RotData[0] = new Vec3d(0d, 0d, 0d);

    for (int x = 1; x < totalFrames; x++) {
      PosData[x] = new Vec3d(0d, (Math.sin((x * degreesPerCalculation) / (180 * Math.PI))), 0d);
      RotData[x] = new Vec3d(0d, (0d), 0d);
    }
    ContainerAnimation = new AnimationData(FPS, PosData, RotData);

    for (int x = 0; x < totalFrames; x++) {
      main.LOGGER.warn("{} | index: {}", PosData[x], x);
    }
  }

  public static Vec3d sumOfPreviousEntries(Vec3d[] entries) {
    double x = 0;
    double y = 0;
    double z = 0;
    for (Vec3d vector : entries) {
      if (vector == null) {
        break;
      }
      x += vector.x;
      y += vector.y;
      z += vector.z;
    }
    return new Vec3d(x, y, z);
  }

}
