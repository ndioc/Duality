package com.github.ndioc.duality.client;

import com.github.ndioc.duality.blockentities.AnimatedPillarEntity;
import com.github.ndioc.duality.blockentitytypes;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.networking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class clientNetworking {

  public static void registerpackets () {
    ClientPlayNetworking.registerGlobalReceiver(networking.ANIMATED_PILLAR_SYNC_PACKET_ID, (client, handler, buffer, responseSender) -> {

      BlockPos position = buffer.readBlockPos();
      String axis = buffer.readString();
      int index = buffer.readInt();
      main.LOGGER.info("index: {}", index);
      int randomoffset = buffer.readInt();
      main.LOGGER.info("randomoffset: {}", randomoffset);

      client.execute(() -> {
        World world = handler.getWorld();
        AnimatedPillarEntity entity = (AnimatedPillarEntity) world.getBlockEntity(position);

        if (entity != null) {
          if (entity.getType() == blockentitytypes.ANIMATED_PILLAR) {
            entity.setAxis(axis);
            entity.setIndex(index);
            entity.setRandomoffset(randomoffset);
          }
        }
      });
    });

    //add more custom packets here

  }

  public static void sendPacketToServer(Identifier id, PacketByteBuf buffer) {
    ClientPlayNetworking.send(id, buffer);
  }

  public static void initialize() {
    registerpackets();
  }

}
