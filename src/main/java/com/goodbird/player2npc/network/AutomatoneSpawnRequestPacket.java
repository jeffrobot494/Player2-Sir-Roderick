package com.goodbird.player2npc.network;

import altoclef.player2api.Character;
import altoclef.player2api.utils.CharacterUtils;
import com.goodbird.player2npc.capability.CompanionCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AutomatoneSpawnRequestPacket {
   private final Character character;

   private AutomatoneSpawnRequestPacket(Character character) {
      this.character = character;
   }

   public AutomatoneSpawnRequestPacket(FriendlyByteBuf buf) {
      this.character = CharacterUtils.readFromBuf(buf);
   }

   public static void send(Character character) {
      ForgeNetwork.sendToServer(new AutomatoneSpawnRequestPacket(character));
   }

   public void write(FriendlyByteBuf buf) {
      CharacterUtils.writeToBuf(buf, this.character);
   }

   public static void handle(AutomatoneSpawnRequestPacket msg, Supplier<NetworkEvent.Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (player != null) {
            player.getCapability(CompanionCapability.INSTANCE).ifPresent(manager -> {
               manager.ensureCompanionExists(msg.character);
            });
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
