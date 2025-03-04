package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.mechanics.essence.*;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceContainerConstants;
import com.github.ndioc.duality.mechanics.essence.objects.essence;
import com.github.ndioc.duality.mechanics.selection.SelectableEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import static com.github.ndioc.duality.blocks.essence.Constants.fetchContainerConstants;

public class EssenceContainerEntity extends BlockEntity implements EssenceContainer, SelectableEntity {

  public EssenceContainerEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONTAINER, pos, state);
    container = new essence[getConstants().getNumberOfContainers()];
  }

  private essence[] container;
  private EssenceContainerConstants constants;

  public EssenceContainerConstants getConstants() {
    if (constants != null) {
      return constants;
    }
    else {
      return fetchContainerConstants(this.getCachedState().getBlock().getTranslationKey());
    }
  }

  public essence[] getEssenceArray() {
    return container;
  }

  @Override
  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    writeContainersToNBT(nbt);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    readContainersFromNBT(nbt);
  }

}
