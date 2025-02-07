package com.github.ndioc.duality.blockentities;

import com.github.ndioc.duality.blockentitytypes;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.networking;
import com.github.ndioc.duality.utilities;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.minecraft.block.PillarBlock.AXIS;

public class AnimatedPillarEntity extends BlockEntity {

  public String axis = "";
  public int index = 0;
  public int delay = 1;

  public boolean firsttick = true;
  public boolean checkindex = true;

  @Override
  public void writeNbt(NbtCompound data) {
    data.putInt("index", index);
    data.putString("axis", axis);
    super.writeNbt(data);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    index = nbt.getInt("index");
    axis = nbt.getString("axis");
    super.readNbt(nbt);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt() {
    return createNbt();
  }

  public void onblockupdate() {
    checkindex = true;
    delay = 1;
    main.LOGGER.info("block update triggered on {}", pos.toShortString());
  }

  public String getAxis() {
    return axis;
  }

  public void setAxis(String input) {
   axis = input;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int input) {
    index = input;
  }

  public void syncentity (BlockEntity entity) {
    PacketByteBuf packet = PacketByteBufs.create();
    packet.writeBlockPos(pos);
    packet.writeString(axis);
    packet.writeInt(index);

    for (ServerPlayerEntity player : PlayerLookup.tracking(entity)) {
      networking.sendPacketToClient(player, networking.ANIMATED_PILLAR_SYNC_PACKET_ID, packet);
    }
  }

  public boolean compareEntity(BlockEntity entity) {

    boolean output = false;

    if (entity != null) {
      BlockEntityType<?> typeofme = blockentitytypes.ANIMATED_PILLAR;
      BlockEntityType<?> typeofthem = entity.getType();

      Direction.Axis axisofme = utilities.StringtoAxis(axis);
      NbtCompound nbtofthem = entity.createNbt();
      Direction.Axis axisofthem = utilities.StringtoAxis(nbtofthem.getString("axis"));

      if (typeofme == typeofthem) {
        if (axisofthem == null) {
          return output;
        } else if (axisofme == axisofthem) {
          output = true;
        }
      }

    }
    return output;
  }

  public AnimatedPillarEntity(BlockPos position, BlockState state) {
    super(blockentitytypes.ANIMATED_PILLAR, position, state);
  }

  public static void tick(World world, BlockPos position, BlockState state, AnimatedPillarEntity entity) {

    if (entity.firsttick) {
      entity.axis = state.get(AXIS).asString();
      entity.checkindex = true;
      entity.delay = 1;
      entity.firsttick = false;
    }

    if (entity.checkindex && entity.delay <= 0) {

      for (int x = 0; x < 256; ) {
        BlockEntity entitytocheck = world.getBlockEntity(position.offset(utilities.StringtoAxis(entity.axis), (x * -1) - 1));

          if (entity.compareEntity(entitytocheck)) {
            x++;
          }

        else {
          entity.setIndex(x);
          entity.checkindex = false;
          entity.syncentity(entity);
          break;
        }
      }

      entity.markDirty();
    }

   if (entity.delay > 0) {
     entity.delay--;
   }

  }
}


