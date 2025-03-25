package com.github.ndioc.duality.mechanics.selection;

import com.github.ndioc.duality.Duality;
import com.github.ndioc.duality.blockentities.essence.EssenceContainerEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.github.ndioc.duality.util.nbt.BlockPosNBT.*;

public interface SelectionItem {

  NbtCompound createNBT();

  default void deleteNBT(ItemStack stack) {
    stack.setNbt(null);
  }

  default void printToClient(PlayerEntity player, String message) {
    if (message != null && player != null) {
      player.sendMessage(Text.of(message), true);
    }
  }

  default void setSelectionOnEntity(World world, BlockPos position, int selection) {
    if (world != null) {
      BlockEntity entitytocheck = world.getBlockEntity(position);
      if (entitytocheck instanceof SelectableEntity) {
        EssenceContainerEntity entity = (EssenceContainerEntity) entitytocheck;
        entity.setSelection(selection);
      }
    }
  }

  default BlockPos[] writePos(BlockPos posToAdd, BlockPos[] array) {
    for (int x = 0; x < array.length; x++) {
      if(array[x].equals(BlockPosNull())) {
        array[x] = posToAdd;
        break;
      }
    }
    return array;
  }

  default BlockPos[] removePos(BlockPos posToRemove, BlockPos[] array) {
    for (int x = 0; x < array.length; x++) {
      if (array[x].equals(posToRemove)) {
        array[x] = BlockPosNull();
        break;
      }
    }
    return array;
  }

  default void deleteSelections(NbtCompound nbt, SelectionStates selectionStates, World world) {
    switch (selectionStates) {
      case TARGETING:
        if (nbt == null) {
          return;
        }
        BlockPos[] sources = readBlockPosNBT(nbt, "Sources");
        BlockPos[] destinations = readBlockPosNBT(nbt, "Destinations");
        for (BlockPos position : sources) {
          if (!position.equals(BlockPosNull())) {
            setSelectionOnEntity(world, position, 0);
          }
        }
        for (BlockPos position : destinations) {
          if (!position.equals(BlockPosNull())) {
            setSelectionOnEntity(world, position, 0);
          }
        }
        break;
      default:
    }
  }

  default String selectBlockEntity(BlockPos position, SelectionStates selectionState, ItemStack stack, World world) {
    switch (selectionState) {
      case TARGETING:
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) {
          nbt = createNBT();
        }
        BlockPos[] sources = readBlockPosNBT(nbt, "Sources");
        BlockPos[] destinations = readBlockPosNBT(nbt, "Destinations");

        final int selectionNONE = 0;
        final int selectable = 1;
        final int selectionSRC = 2;
        final int selectionDST = 3;

        boolean sourcesFull = true;
        boolean destinationsFull = true;
        boolean presentInSources = false;
        boolean presentInDestinations = false;

        for (BlockPos source : sources) {
          if (source.equals(BlockPosNull())) {
            sourcesFull = false;
          }
          if (source.equals(position)) {
            presentInSources = true;
          }
        }

        for (BlockPos destination : destinations) {
          if (destination.equals(BlockPosNull())) {
            destinationsFull = false;
          }
          if (destination.equals(position)) {
            presentInDestinations = true;
          }
        }

        if (!presentInSources && !presentInDestinations) {
          if (sourcesFull && destinationsFull) {
            return "Both Sources and Destinations are full";
          }
          else if (!sourcesFull) {
            sources = writePos(position, sources);
            writeBlockPosNBT(sources, nbt, "Sources");
            setSelectionOnEntity(world, position, selectionSRC);
            stack.setNbt(nbt);
            //added to source
            return null;
          }
          else {
            destinations = writePos(position, destinations);
            writeBlockPosNBT(destinations, nbt, "Destinations");
            setSelectionOnEntity(world, position, selectionDST);
            stack.setNbt(nbt);
            return "Sources full, Added to Destinations";
          }
        }
        if (presentInSources) {
          if (!destinationsFull) {
            sources = removePos(position, sources);
            destinations = writePos(position, destinations);
            writeBlockPosNBT(sources, nbt, "Sources");
            writeBlockPosNBT(destinations, nbt, "Destinations");
            setSelectionOnEntity(world, position, selectionDST);
            stack.setNbt(nbt);
            //added to destinations
            return null;
          }
          else {
            sources = removePos(position, sources);
            writeBlockPosNBT(sources, nbt, "Sources");
            setSelectionOnEntity(world, position, selectionNONE);
            stack.setNbt(nbt);
            return "Destinations full, Removed from Selection";
          }
        }
        else {
          destinations = removePos(position, destinations);
          writeBlockPosNBT(destinations, nbt, "Destinations");
          setSelectionOnEntity(world, position, selectionNONE);
          stack.setNbt(nbt);
          //removed from selection
          return null;
        }
    }
    Duality.LOGGER.error("Invalid Selection State | {} | in SelectionItem", selectionState.name());
    return null;
  }
}
