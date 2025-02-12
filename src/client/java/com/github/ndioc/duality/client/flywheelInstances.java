package com.github.ndioc.duality.client;

import com.github.ndioc.duality.client.instances.WispwoodLogInstance;
import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;

public class flywheelInstances {
  public static void initialize(){

    InstancedRenderRegistry.configure(blockentitytypes.ANIMATED_PILLAR)
        .alwaysSkipRender()
        .factory(WispwoodLogInstance::new)
        .apply();

  }

}
