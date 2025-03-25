package com.github.ndioc.duality.client;

//import com.github.ndioc.duality.client.instances.blocks.EssenceContainerInstance;
import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.client.blocks.essence.storage.generic.OrbContainerVisual;
import com.github.ndioc.duality.client.blocks.natural.logs.wispwood.WispwoodLogVisual;
import dev.engine_room.flywheel.api.visualization.VisualizerRegistry;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;

public class FlywheelVisuals {
  public static void initialize(){

    VisualizerRegistry.setVisualizer(blockentitytypes.ANIMATED_PILLAR, new SimpleBlockEntityVisualizer.Builder<>(blockentitytypes.ANIMATED_PILLAR)
        .factory(WispwoodLogVisual::new)
        .skipVanillaRender(animatedPillarEntity -> true)
        .apply());

    VisualizerRegistry.setVisualizer(blockentitytypes.ESSENCE_CONTAINER, new SimpleBlockEntityVisualizer.Builder<>(blockentitytypes.ESSENCE_CONTAINER)
        .factory(OrbContainerVisual::new)
        .skipVanillaRender(essenceContainerEntity -> true)
        .apply());
  }

}
