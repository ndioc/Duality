package com.github.ndioc.duality.client;

import com.github.ndioc.duality.blockentities.AnimatedPillarEntity;
import com.github.ndioc.duality.blockentitytypes;
import com.github.ndioc.duality.networking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.BlockPos;

public class clientNetworking {


  public static void registerpackets () {
    ClientPlayNetworking.registerGlobalReceiver(networking.ANIMATED_PILLAR_PACKET_ID, (client, handler, buffer, responseSender) -> {

      BlockPos position = buffer.readBlockPos();
      String axis = buffer.readString();
      int index = buffer.readInt();

      client.execute(() -> {
        assert client.world != null;
        AnimatedPillarEntity entity = (AnimatedPillarEntity) client.world.getBlockEntity(position);

        if (entity != null) {
          if (entity.getType() == blockentitytypes.ANIMATED_PILLAR) {
            entity.setAxis(axis);
            entity.setIndex(index);
          }
        }
      });
    });

    

  }

  public static void initialize() {
  }

}
