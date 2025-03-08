package com.github.ndioc.duality.items.blockitems.essence.transfer;

import com.github.ndioc.duality.blockentities.essence.EssenceConveyorEntity;
import com.github.ndioc.duality.mechanics.essence.EssenceConveyor;
import com.github.ndioc.duality.mechanics.essence.objects.EssenceConveyorConstants;
import com.github.ndioc.duality.mechanics.selection.SelectableEntity;
import com.github.ndioc.duality.mechanics.selection.SelectionItem;
import com.github.ndioc.duality.mechanics.selection.SelectionStates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.github.ndioc.duality.util.nbt.BlockPosNBT.createTargetingNBT;

public class EssenceConveyorItem extends BlockItem implements SelectionItem {

  public EssenceConveyorItem(Block block, Settings settings) {
    super(block, settings);
    constants = EssenceConveyorConstants.fetchConveyorConstants(block.getTranslationKey());
  }

  private final EssenceConveyorConstants constants;

  public NbtCompound createNBT() {
    return createTargetingNBT(constants);
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    PlayerEntity player = context.getPlayer();
    if (player != null && player.isSneaking()) {
      printToClient(player, selectBlockEntity(context.getBlockPos(), SelectionStates.TARGETING, context.getStack(), context.getWorld()));
      return ActionResult.PASS;
    }
    return super.useOnBlock(context);
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    if (stack.hasNbt() && !selected) {
      deleteSelections(stack.getNbt(), SelectionStates.TARGETING, world);
      deleteNBT(stack);
    }
    super.inventoryTick(stack, world, entity, slot, selected);
  }

  @Override
  protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
    BlockEntity entitytocheck = world.getBlockEntity(pos);
    NbtCompound nbt = stack.getNbt();
    if (entitytocheck instanceof EssenceConveyor) {
      EssenceConveyorEntity entity = (EssenceConveyorEntity) entitytocheck;
      entity.writeTargets(nbt, constants, world);
    }
      deleteSelections(nbt, SelectionStates.TARGETING, world);
      deleteNBT(stack);
    return super.postPlacement(pos, world, player, stack, state);
  }
}
