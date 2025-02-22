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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class essenceTransferBlockItem extends BlockItem {
  public essenceTransferBlockItem(Block block, Settings settings) {
    super(block, settings);
    this.block = block;
    setConstants();
  }

  private void setConstants () {
    int[][] AiA = essenceBlockConstants.fetchConstants(block.getTranslationKey());
    if (AiA != null) {
      maxSources = AiA[0][4];
      maxDestinations = AiA[0][5];
      sources = new BlockPos[maxSources];
      destinations = new BlockPos[maxDestinations];
    }
  }

  private Block block;

  private int maxSources;
  private int maxDestinations;

  BlockPos[] sources;
  BlockPos[] destinations;

  boolean isclient;

  private String writeToArrays(BlockPos position, PlayerEntity player) {
    boolean presentinsources = false;
    boolean presentindestinations = false;
    for (int x = 0; x < sources.length - 1; x++) {
      if (sources[x] == position) {
        presentinsources = true;
        break;
      }
    }
    for (int x = 0; x < destinations.length - 1; x++) {
      if (destinations[x] == position) {
        presentindestinations = true;
        break;
      }
    }
    if (!presentinsources || !presentindestinations) {
      for (int x = 0; x < sources.length - 1; x++) {
        if (sources[x] == null) {
          sources[x] = position;
          return "Block set as source.";
        }
        else if (x == sources.length - 1) {
          sources[x] = position;
          return "Sources full, overwriting last source.";
        }
      }
    }
    if (presentinsources) {
      for (int x = 0; x < sources.length - 1; x++) {
        if (sources[x] == position) {
          sources[x] = null;
          break;
        }
      }
      for (int y = 0; y < destinations.length - 1; y++) {
        if (destinations[y] == null) {
          destinations[y] = position;
          return "Block set as destination.";
        } else if (y == destinations.length - 1) {
          destinations[y] = position;
          return "Destinations full, overwriting last destination.";
        }

      }
    }
    if (presentindestinations) {
      for (int x = 0; x < destinations.length - 1; x++) {
        if (destinations[x] == position) {
          destinations[x] = null;
          return "Block removed from selection.";
        }
      }
    }
    return "shit something went wrong again";
  }

  private void wipeArrays() {
    Arrays.fill(sources, null);
    Arrays.fill(destinations, null);
  }

  private void printToClient(String text, PlayerEntity player) {
    if (isclient) {
      Text message = Text.of(text);
      player.sendMessage(message, true);
    }
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {

    PlayerEntity player = context.getPlayer();
    World world = context.getWorld();

    if (player != null && world != null && player.isSneaking()) {
      isclient = world.isClient;
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (entity != null && entity.getType() == blockentitytypes.ESSENCE_CONTAINER) {
          printToClient(writeToArrays(context.getBlockPos(), player), player);
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
    if (stack.getItem() == this.asItem() && world.getBlockEntity(pos) != null && world.getBlockEntity(pos).getType() == blockentitytypes.ESSENCE_TRANSFER_ENTITY) {
      EssenceTransferEntity entity = (EssenceTransferEntity) world.getBlockEntity(pos);
      essenceTransferBlockItem item = (essenceTransferBlockItem) stack.getItem();
      entity.writeTargets(item.sources, item.destinations);
      item.wipeArrays();
    }
    return super.postPlacement(pos, world, player, stack, state);
  }
}
