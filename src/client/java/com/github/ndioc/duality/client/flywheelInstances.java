package com.github.ndioc.duality.client;

import com.github.ndioc.duality.block.wispwoodlog.WispwoodLogInstance;
import com.github.ndioc.duality.blockentitytypes;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;

public class flywheelInstances {
  public static void initialize(){

    InstancedRenderRegistry.configure(blockentitytypes.ANIMATED_PILLAR)
        .alwaysSkipRender()
        .factory(WispwoodLogInstance::new)
        .apply();

  }

}
