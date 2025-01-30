package com.github.ndioc.duality.block.wispwoodlog;

import com.github.ndioc.duality.blockentitytypes.AnimatedPillarEntity;
import com.github.ndioc.duality.blocks;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.TickableInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import net.minecraft.util.math.Direction;

public class WispwoodLogInstance extends BlockEntityInstance<AnimatedPillarEntity> implements TickableInstance {

  private final ModelData model;

  public WispwoodLogInstance (MaterialManager matMan, AnimatedPillarEntity entity){
    super(matMan, entity);

    model = matMan.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN.getDefaultState())
        .createInstance();


      model.loadIdentity()
          .translate(getInstancePosition())
          .setBlockLight(15)
          .setSkyLight(15);
  }

  @Override
  public void tick() {

    int veinmodel = 0;

    float flip;
    float x = 0;
    float y = 0;
    float z = 0;

    int frame = blockEntity.frame;

    if (frame >= 18) {
      flip = 3.141592f;
    }
    else {
      flip = 0;
    }

    switch (frame) {
      case 1,23:
        veinmodel = 0;
        break;
      case 2,22:
        veinmodel = 1;
        break;
      case 3,21:
        veinmodel = 2;
        break;
      case 4,20:
        veinmodel = 3;
        break;
      case 5,19:
        veinmodel = 4;
        break;
      case 6,18:
        veinmodel = 5;
        break;
      case 7,8,9,10,11,12,13,14,15,16,17:
        veinmodel = 6;
        switch (blockEntity.axis) {
          case "x":
            x = 0.0625f * (frame - 7);
            break;
          case "y":
            y = 0.0625f * (frame - 7);
            break;
          case "z":
            z = 0.0625f * (frame - 7);
            break;
        }
    }

    if(frame > 17 || frame < 7) {
      model.delete();
    }

    materialManager.defaultSolid()
        .material(Materials.TRANSFORMED)
        .getModel(blocks.WISPWOOD_VEIN.getDefaultState().with(WispwoodVein.VEINSTATES, veinmodel))
        .stealInstance(model);

        model.loadIdentity()
            .translate(getInstancePosition())
            .translate(x, y, z)
            .rotateCentered(Direction.NORTH, flip)
            .setSkyLight(15);

  }

  @Override
  public void updateLight() {
    relight(getWorldPosition(), model);
  }

  @Override
  public void remove(){
    model.delete();
  }

}
