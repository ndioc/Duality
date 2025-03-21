package com.github.ndioc.duality.client.animation;

import com.github.ndioc.duality.Duality;
import com.github.ndioc.duality.client.animation.objects.AnimationData;
import net.minecraft.util.math.Vec3d;

public class AnimationBuilder {

  public static AnimationData ContainerAnimation;
  public static AnimationData WispwoodLogAnimation;

  public static void build() {
    // Main method that gets called from mainClient.
    long start = System.currentTimeMillis();
    assembleContainerAnimation();
    assembleWispwoodLogAnimation();
    Duality.LOGGER.info("{} successfully built animations, took {} milliseconds.", Duality.MOD_NAME,System.currentTimeMillis() - start);
    AnimationsToPrint();
  }

  public static void AnimationsToPrint() {
    // use printAnimationToLog(AnimationData); for more information see printAnimationToLog();
    printAnimationToLog(WispwoodLogAnimation);
  }

  public static void assembleContainerAnimation() {
    final int FPS = 60;                     // sets Frame Rate of Animation, this is used together with System.currentTimeMillis() which is stored as the 'AnimationStartTime' in a variable inside the instance, which then together with the FPS gets calculated into the current frame, which makes the animation frame rate independent.
    final int degreesPerCalculation = 20;   // this is the DEGREES for the sine wave generated later IE Math.sin(x * degrees), the degrees get converted to radians before calculating which is an unnecessary step to take, but I find degrees easier to work with.
    final int totalFrames = 534;            // amount of frames to calculate in the animation.
    final float baseRotation = 26.6966f;    // this is the rotation applied every frame, it is VERY important that this is close to zero at the end of the animation to make sure that it gets looped smoothly!

    Vec3d[] PosData = new Vec3d[totalFrames];
    Vec3d[] RotData = new Vec3d[totalFrames];

    PosData[totalFrames - 1] = new Vec3d(0d, 0d, 0d); // set last frame of the animation to zero to make it loop smoother
    RotData[totalFrames - 1] = new Vec3d(0d, 0d, 0d);

    for (int x = 0; x < totalFrames - 1; x++) {
      // this is the loop where each frame of the animation is generated, x = frame.
      // each frame gets generated into 3d double vectors and are put in an array where (index = frameNum). this gets put into an AnimationData object which the instance uses for each frame.
      double rotation = (baseRotation  / (180 * Math.PI)) * x;
      PosData[x] = new Vec3d(0d, (Math.sin((x * degreesPerCalculation) / (180 * Math.PI)) / 10d), 0d);
      RotData[x] = new Vec3d(rotation, (rotation * 0.5f), (rotation * 0.25f) * -1);
    }
    ContainerAnimation = new AnimationData(FPS, PosData, RotData); // use the previously generated arrays to generate an AnimationData object and put it into the ContainerAnimation variable for later reference.
  }

  public static void assembleWispwoodLogAnimation() {
    final int FPS = 20;
    final double offset = 0.0625d;
    final int animationFrames = 23;
    final int animationPause = 46;
    final int totalFrames = animationFrames + animationPause;

    Vec3d[] FramePosData = new Vec3d[totalFrames];
    Vec3d[] FrameRotData = new Vec3d[totalFrames];

    for (int x = 0; x < totalFrames; x++) {
      if (x >= 17 && x <= 23) {
        FramePosData[x] = new Vec3d(0d, 0d, 0d);
        FrameRotData[x] = new Vec3d(3.141592d, 3.141592d, 3.141592d);
        continue;
      }
      if (x >= 7 && x <= 23) {
        FramePosData[x] = new Vec3d(0d, offset * (x - 7), 0d);
        FrameRotData[x] = new Vec3d(0d, 0d, 0d);
        continue;
      }
      FramePosData[x] = new Vec3d(0d, 0d, 0d);
      FrameRotData[x] = new Vec3d(0d, 0d, 0d);
    }
    WispwoodLogAnimation = new AnimationData(FPS, FramePosData, FrameRotData);
  }

  public static void printAnimationToLog(AnimationData data) {

    // this loop gets fed an AnimationData object, fetches each of the frames and prints the vectors to the log, which is used for debugging and smoothing out the animation.

    for (int x = 0; x < data.getTotalFrames(); x++) {
      Duality.LOGGER.info("Frame | {}", x);
      Duality.LOGGER.info("Position Data | {}", data.getFramePosData(x));
      Duality.LOGGER.info("Rotational Data | {}, {}, {}", data.getFrameRotationDataUP(x), data.getFrameRotationDataNORTH(x), data.getFrameRotationDataEAST(x));
    }
  }

}
