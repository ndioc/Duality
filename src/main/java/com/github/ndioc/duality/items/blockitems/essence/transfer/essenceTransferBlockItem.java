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
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class essenceTransferBlockItem extends BlockItem {
  public essenceTransferBlockItem(Block block, Settings settings) {
    super(block, settings);
    setConstants(block);
  }

  private void setConstants (Block block) {
    int[][] AiA = essenceBlockConstants.fetchConstants(block.getTranslationKey());
    if (AiA != null) {
      maxSources = AiA[0][4];
      maxDestinations = AiA[0][5];
      sources = new BlockPos[maxSources];
      destinations = new BlockPos[maxDestinations];
    }
  }

  private int maxSources;
  private int maxDestinations;

  BlockPos[] sources;
  BlockPos[] destinations;

  boolean isclient;

  private String writeToArrays(BlockPos position, PlayerEntity player) {
    boolean presentinsources = false;
    boolean presentindestinations = false;

    for (int x = 0; x < maxSources - 1; x++) {
      if (sources[x] == position) {
        presentinsources = true;
        break;
      }
    }
    for (int x = 0; x < maxDestinations - 1; x++) {
      if (destinations[x] == position) {
        presentindestinations = true;
        break;
      }
    }
    if (!presentinsources || !presentindestinations) {
      for (int x = 0; x < maxSources - 1; x++) {
        if (sources[x] == null) {
          sources[x] = position;
          return "Block set as source.";
        }
        else if (x == maxSources - 1) {
          sources[x] = position;
          return "Sources full, overwriting last source.";
        }
      }
    }
    if (presentinsources) {
      for (int x = 0; x < maxSources - 1; x++) {
        if (sources[x] == position) {
          sources[x] = null;
          break;
        }
      }
      for (int y = 0; y < maxDestinations - 1; y++) {
        if (destinations[y] == null) {
          destinations[y] = position;
          return "Block set as destination.";
        } else if (y == maxDestinations - 1) {
          destinations[y] = position;
          return "Destinations full, overwriting last destination.";
        }

      }
    }
    if (presentindestinations) {
      for (int x = 0; x < maxDestinations - 1; x++) {
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

  private NbtCompound createNbt() {
    NbtCompound nbt = new NbtCompound();
    nbt.putInt("maxSources", maxSources);
    nbt.putInt("maxDestinations", maxDestinations);
    nbt.putIntArray("Sources", new int[maxSources]);
    nbt.putIntArray("Destinations", new int[maxDestinations]);
    return nbt;
  }

  private int[] writeNBT(BlockPos[] positionarray, int arrayDepth) {
    int[] positionNBT = new int[arrayDepth * 3];

    if (positionarray.length == 0) {
      Arrays.fill(positionNBT,0);
      return positionNBT;
    }

    for (int x = 0, y = 0; x < arrayDepth - 1; x++) {
      if(positionarray[x] == null) {
        positionNBT[y] = 0;
        y++;
        positionNBT[y] = 0;
        y++;
        positionNBT[y] = 0;
        y++;
        continue;
      }
      positionNBT[y] = positionarray[x].getX();
      y++;
      positionNBT[y] = positionarray[x].getY();
      y++;
      positionNBT[y] = positionarray[x].getZ();
      y++;
    }

    return positionNBT;
  }

  private BlockPos[] readNBT(int[] inputArray) {
    int arrayDepth = inputArray.length / 3;
    BlockPos[] output = new BlockPos[arrayDepth];
    for (int u = 0, v = 0; u < arrayDepth - 1; u++) {
      int x = inputArray[v];
      v++;
      int y = inputArray[v];
      v++;
      int z = inputArray[v];
      v++;
      output[u] = new BlockPos(x, y, z);
    }
    return output;
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {

    NbtCompound nbt = context.getStack().getNbt();
    if (nbt == null) {
      nbt = createNbt();
    }

    PlayerEntity player = context.getPlayer();
    World world = context.getWorld();

    sources = readNBT(nbt.getIntArray("sources"));
    destinations = readNBT(nbt.getIntArray("destinations"));

    if (player != null && world != null && player.isSneaking()) {
      isclient = world.isClient;
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (entity != null && entity.getType() == blockentitytypes.ESSENCE_CONTAINER) {
          printToClient(writeToArrays(context.getBlockPos(), player), player);
          nbt.putIntArray("sources", writeNBT(sources, nbt.getInt("maxSources")));
          nbt.putIntArray("destinations", writeNBT(destinations, nbt.getInt("maxDestinations")));
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
