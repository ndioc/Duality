package com.github.ndioc.duality;

import com.github.ndioc.duality.block.wispwoodlog.WispwoodLogInstance;
import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;

public class FlywheelInstances {
  public static void initialize(){

    InstancedRenderRegistry.configure(blockentitytypes.ANIMATED_PILLAR)
        .alwaysSkipRender()
        .factory(WispwoodLogInstance::new)
        .apply();

  }

}
