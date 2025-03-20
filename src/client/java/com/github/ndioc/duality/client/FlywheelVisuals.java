package com.github.ndioc.duality.client;

import com.github.ndioc.duality.blockentities.animation.AnimatedPillarEntity;
//import com.github.ndioc.duality.client.instances.blocks.EssenceContainerInstance;
import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.client.instances.blocks.WispwoodLogVisual;
import dev.engine_room.flywheel.api.visualization.VisualizerRegistry;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.function.Predicate;

public class FlywheelVisuals {
  public static void initialize(){

    VisualizerRegistry.setVisualizer(blockentitytypes.ANIMATED_PILLAR, new SimpleBlockEntityVisualizer.Builder<>(blockentitytypes.ANIMATED_PILLAR)
        .factory(WispwoodLogVisual::new)
            .skipVanillaRender(animatedPillarEntity -> true)
                .apply());

/*
    InstancedRenderRegistry.configure(blockentitytypes.ESSENCE_CONTAINER)
        .alwaysSkipRender()
        .factory(EssenceContainerInstance::new)
        .apply();
*/
  }

}
