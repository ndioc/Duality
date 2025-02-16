package com.github.ndioc.duality.blockentities;

import com.github.ndioc.duality.mechanics.essence.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EssenceContainerEntity extends BlockEntity implements essenceContainer {

  essence[] container;
  int[] allocatedContainers;

  int[] allowedContainers;
  int numberOfContainers;
  int volumePerContainer;

  boolean firsttick = true;

  World world = getWorld();
  EssenceContainerEntity localEntity = getContainerEntity(this.getPos());

  public EssenceContainerEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONTAINER, pos, state);
  }

  public void setConstantsFromBlock() {
    allowedContainers = essenceBlockConstants.TESTORB.getAllowedEssenceTypes();
    numberOfContainers = essenceBlockConstants.TESTORB.getNumberOfContainers();
    volumePerContainer = essenceBlockConstants.TESTORB.getAmountPerContainer();
  }

  public essence createEssenceObject(essenceType type, int capacity) {
     return new essence(type, capacity);
  }

  public void deleteEssenceObject(int arraynum) {
    container[arraynum] = null;
  }

  public EssenceContainerEntity getContainerEntity(BlockPos position) {
    BlockEntity checktype =  world.getBlockEntity(position);
    if (checktype != null && checktype.getType() == blockentitytypes.ESSENCE_CONTAINER) {
      return (EssenceContainerEntity) checktype;
    }
    return null;
  }

  public int removeEssence(essenceType type, int amount) {
    return 0;
  }

  public int addEssence(essenceType type, int amount) {
    return 0;
  }

  public static void tick(World world, BlockPos position, BlockState state, EssenceContainerEntity entity) {
    if (entity.firsttick) {
      entity.container[0] = entity.createEssenceObject(essenceType.HARMONIOUS, entity.volumePerContainer);
      entity.firsttick = false;
    }

    entity.removeEssence(essenceType.HARMONIOUS, 1000);
  }
}
