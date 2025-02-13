package com.github.ndioc.duality;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class networking {

  // declare packet IDs
  public static final Identifier ANIMATED_PILLAR_SYNC_PACKET_ID = new Identifier(main.MOD_ID, "animated_pillar");

  // a general purpose networking method straight from the wiki

  public static void sendPacketToClient(ServerPlayerEntity player, Identifier id, PacketByteBuf buffer) {
    ServerPlayNetworking.send(player, id, buffer);
  }

}
