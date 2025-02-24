package com.github.ndioc.duality.items.blockitems.essence.transfer;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.blockentities.essence.EssenceTransferEntity;
import com.github.ndioc.duality.mechanics.essence.essenceBlockConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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

import java.util.Arrays;

import static com.github.ndioc.duality.items.items.TEST_RELAY_ITEM;
import static com.github.ndioc.duality.util.nbt.BlockPosNBT.*;

public class essenceTransferBlockItem extends BlockItem {
  public essenceTransferBlockItem(Block block, Settings settings) {
    super(block, settings);
    setConstants(block);
    createNbt();
  }

  private void setConstants (Block block) {
    int[][] AiA = essenceBlockConstants.fetchConstants(block.getTranslationKey());
    if (AiA != null) {
      maxSources = AiA[0][4];
      maxDestinations = AiA[0][5];
    }
  }

  private int maxSources;
  private int maxDestinations;

  private boolean compareBlockPos(BlockPos pos1, BlockPos pos2) {

    int x1 = pos1.getX();
    int y1 = pos1.getY();
    int z1 = pos1.getZ();
    int x2 = pos2.getX();
    int y2 = pos2.getY();
    int z2 = pos2.getZ();

    return (x1 == x2 && y1 == y2 && z1 == z2);

  }

  private String writeToArrays(BlockPos position, PlayerEntity player, NbtCompound nbt) {
    boolean presentinsources = false;
    boolean presentindestinations = false;

    final BlockPos zeroX3 = new BlockPos(BlockPos.ZERO);

    BlockPos[] sources = readBlockPosNBT(nbt, "Sources");
    BlockPos[] destinations = readBlockPosNBT(nbt, "Destinations");

    for (int x = 0; x < maxSources; x++) {
      if (compareBlockPos(sources[x], position)) {
        presentinsources = true;
        break;
      }
    }
    for (int x = 0; x < maxDestinations && !presentinsources; x++) {
      if (compareBlockPos(destinations[x], position)) {
        presentindestinations = true;
        break;
      }
    }
    if (!presentinsources && !presentindestinations) {
      for (int x = 0; x < maxSources; x++) {
        if (compareBlockPos(sources[x], zeroX3)) {
          sources[x] = position;
          writeBlockPosNBT(sources, nbt, "Sources");
          return "Block set as source.";
        }
        else if (x == maxSources - 1) {
          return "Sources full";
        }
      }
    }
    if (presentinsources) {
      for (int x = 0; x < maxSources; x++) {
        if (compareBlockPos(sources[x], position)) {
          sources[x] = zeroX3;
          writeBlockPosNBT(sources, nbt, "Sources");
          break;
        }
      }
      for (int x = 0; x < maxDestinations; x++) {
        if (compareBlockPos(destinations[x], zeroX3)) {
          destinations[x] = position;
          writeBlockPosNBT(destinations, nbt, "Destinations");
          return "Block set as destination.";
        } else if (x == maxDestinations - 1) {
          return "Destinations full";
        }

      }
    }
    if (presentindestinations) {
      for (int x = 0; x < maxDestinations; x++) {
        if (compareBlockPos(destinations[x], position)) {
          destinations[x] = zeroX3;
          writeBlockPosNBT(destinations, nbt, "Destinations");
          return "Block removed from selection.";
        }
      }
    }
    return "shit something went wrong again";
  }

  private void wipeArrays(NbtCompound nbt, ItemStack itemStack) {
    BlockPos[] sources = readBlockPosNBT(nbt, "Sources");
    Arrays.fill(sources, BlockPos.ZERO);
    writeBlockPosNBT(sources, nbt, "Sources");
    BlockPos[] destinations = readBlockPosNBT(nbt, "Destinations");
    Arrays.fill(destinations, BlockPos.ZERO);
    writeBlockPosNBT(destinations, nbt, "Destinations");
    itemStack.setNbt(nbt);
  }

  private void printToClient(String text, PlayerEntity player) {
    if (player.getWorld().isClient()) {
      Text message = Text.of(text);
      player.sendMessage(message, true);
    }
  }

  private NbtCompound createNbt() {
    NbtCompound nbt = new NbtCompound();
    nbt.putInt("maxSources", maxSources);
    nbt.putInt("maxDestinations", maxDestinations);
    nbt.putIntArray("Sources", new int[maxSources * 3]);
    nbt.putIntArray("Destinations", new int[maxDestinations * 3]);
    return nbt;
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {

    PlayerEntity player = context.getPlayer();
    World world = context.getWorld();
    ItemStack itemStack = context.getStack();

    NbtCompound nbt = itemStack.getNbt();
    if (nbt == null) {
      nbt = createNbt();
    }

    if (player != null && world != null && player.isSneaking()) {
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (entity != null && entity.getType() == blockentitytypes.ESSENCE_CONTAINER) {
          printToClient(writeToArrays(context.getBlockPos(), player, nbt), player);
          itemStack.setNbt(nbt);
            return ActionResult.PASS;
        }
        else {
          printToClient("This block is not an Essence Container", player);
          return ActionResult.PASS;
        }
      }
    return super.useOnBlock(context);
  }

  @Override
  protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
    if (stack.isOf(TEST_RELAY_ITEM)) {
      BlockEntity entitytotypecheck = world.getBlockEntity(pos);
      if (entitytotypecheck != null && entitytotypecheck.getType() == blockentitytypes.ESSENCE_TRANSFER_ENTITY) {
        EssenceTransferEntity entity = (EssenceTransferEntity) world.getBlockEntity(pos);
        NbtCompound nbt = stack.getNbt();
        if (nbt != null) {
            entity.writeTargets(readBlockPosNBT(nbt, "Sources"), readBlockPosNBT(nbt, "Destinations"));
        }
      }
    }
    return super.postPlacement(pos, world, player, stack, state);
  }
}
