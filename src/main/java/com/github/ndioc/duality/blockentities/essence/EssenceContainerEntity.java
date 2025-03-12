package com.github.ndioc.duality.blockentities.essence;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.mechanics.essence.*;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceContainerConstants;
import com.github.ndioc.duality.mechanics.essence.objects.Essence;
import com.github.ndioc.duality.mechanics.selection.SelectableEntity;
import com.github.ndioc.duality.mechanics.selection.SelectionStates;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.github.ndioc.duality.mechanics.essence.objects.EssenceContainerConstants.fetchContainerConstants;
import static com.github.ndioc.duality.mechanics.selection.SelectionStates.fetchSelectionStates;

public class EssenceContainerEntity extends BlockEntity implements EssenceContainer, SelectableEntity {

  public EssenceContainerEntity(BlockPos pos, BlockState state) {
    super(blockentitytypes.ESSENCE_CONTAINER, pos, state);
    initializeEntity();
  }

  private Essence[] container;
  private EssenceContainerConstants constants;

  private int selection = 0;
  private SelectionStates selectionState;

  private boolean activated = false;
  private int timer = 0;

  public EssenceContainerConstants getConstants() {
    if (constants != null) {
      return constants;
    }
    else {
      constants = fetchContainerConstants(this.getCachedState().getBlock().getTranslationKey());
      return constants;
    }
  }
  public Essence[] getEssenceArray() {
    return container;
  }
  public void setEssenceArray(Essence[] container) {
   this.container = container;
  }
  public int getCurrentSelection() {
    return selection;
  }
  public void setSelection(int selection) {
    this.selection = selection;
  }
  public SelectionStates getSelectionStates() {
    if (selectionState == null) {
      selectionState = fetchSelectionStates(this.getCachedState().getBlock().getTranslationKey());
    }
    return selectionState;
  }
  public boolean isActivated() {
    return activated;
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

  public static void tick(World world, BlockPos position, BlockState state, EssenceContainerEntity entity) {

    if (entity.timer >= 20) {
      for (Essence essence : entity.getEssenceArray()) {
        if (essence != null && essence.getQuantity() > 0) {
          entity.activated = true;
          break;
        }
        entity.activated = false;
      }
      entity.timer = 0;
    }
    entity.timer++;
  }
}
