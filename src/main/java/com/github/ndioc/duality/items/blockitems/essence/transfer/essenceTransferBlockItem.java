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

  boolean isclient;

  private String writeToArrays(BlockPos position, PlayerEntity player, NbtCompound nbt) {
    boolean presentinsources = false;
    boolean presentindestinations = false;

    final BlockPos zeroX3 = new BlockPos(BlockPos.ZERO);

    BlockPos[] sources = readNBT(nbt, "Sources");
    BlockPos[] destinations = readNBT(nbt, "Destinations");

    if (sources.length == 0){
      sources = new BlockPos[maxSources];
    }
    if (destinations.length == 0) {
      destinations = new BlockPos[maxDestinations];
    }

    for (int x = 0; x < maxSources; x++) {
      if (sources[x] == position) {
        presentinsources = true;
        break;
      }
    }
    for (int x = 0; x < maxDestinations; x++) {
      if (destinations[x] == position) {
        presentindestinations = true;
        break;
      }
    }
    if (!presentinsources || !presentindestinations) {
      for (int x = 0; x < maxSources; x++) {
        if (sources[x] == zeroX3) {
          sources[x] = position;
          writeNBT(sources, nbt, "Sources");
          return "Block set as source.";
        }
        else if (x == maxSources - 1) {
          sources[x] = position;
          writeNBT(sources, nbt, "Sources");
          return "Sources full, overwriting last source.";
        }
      }
    }
    if (presentinsources) {
      for (int x = 0; x < maxSources; x++) {
        if (sources[x] == position) {
          sources[x] = zeroX3;
          writeNBT(sources, nbt, "Sources");
          break;
        }
      }
      for (int y = 0; y < maxDestinations; y++) {
        if (destinations[y] == zeroX3) {
          destinations[y] = position;
          writeNBT(destinations, nbt, "Destinations");
          return "Block set as destination.";
        } else if (y == maxDestinations - 1) {
          destinations[y] = position;
          writeNBT(destinations, nbt, "Destinations");
          return "Destinations full, overwriting last destination.";
        }

      }
    }
    if (presentindestinations) {
      for (int x = 0; x < maxDestinations; x++) {
        if (destinations[x] == position) {
          destinations[x] = zeroX3;
          writeNBT(destinations, nbt, "Destinations");
          return "Block removed from selection.";
        }
      }
    }
    return "shit something went wrong again";
  }

  private void wipeArrays(NbtCompound nbt) {
    BlockPos[] sources = readNBT(nbt, "Sources");
    Arrays.fill(sources, BlockPos.ZERO);
    writeNBT(sources, nbt, "Sources");
    BlockPos[] destinations = readNBT(nbt, "Destinations");
    Arrays.fill(destinations, BlockPos.ZERO);
    writeNBT(destinations, nbt, "Destinations");
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
    nbt.putIntArray("Sources", new int[maxSources * 3]);
    nbt.putIntArray("Destinations", new int[maxDestinations * 3]);
    return nbt;
  }

  private void writeNBT(BlockPos[] positionarray, NbtCompound nbt, String key) {
    int[] positionNBT = new int[positionarray.length * 3];

    if (positionarray.length == 0) {
      Arrays.fill(positionNBT,0);
      nbt.putIntArray(key, positionNBT);
      return;
    }

    for (int x = 0, y = 0; x < positionarray.length - 1; x++) {
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

    nbt.putIntArray(key, positionNBT);
  }

  private BlockPos[] readNBT(NbtCompound nbt, String key) {
    int[] array = nbt.getIntArray(key);
    int arrayDepth = array.length / 3;
    BlockPos[] output = new BlockPos[arrayDepth];
    for (int u = 0, v = 0; u < arrayDepth - 1; u++) {
      int x = array[v];
      v++;
      int y = array[v];
      v++;
      int z = array[v];
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

    if (player != null && world != null && player.isSneaking()) {
      isclient = world.isClient;
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (entity != null && entity.getType() == blockentitytypes.ESSENCE_CONTAINER) {
          printToClient(writeToArrays(context.getBlockPos(), player, nbt), player);
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
    if (stack.getItem() == this.asItem() && world.getBlockEntity(pos) != null && world.getBlockEntity(pos).getType() == blockentitytypes.ESSENCE_TRANSFER_ENTITY && stack.getNbt() != null) {
      EssenceTransferEntity entity = (EssenceTransferEntity) world.getBlockEntity(pos);
      essenceTransferBlockItem item = (essenceTransferBlockItem) stack.getItem();
      entity.writeTargets(item.readNBT(stack.getNbt(), "Sources"), item.readNBT(stack.getNbt(), "Destinations"));
      item.wipeArrays(stack.getNbt());
    }
    return super.postPlacement(pos, world, player, stack, state);
  }
}
