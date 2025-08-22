package com.goodbird.player2npc.event;

import altoclef.player2api.AICommandBridge;
import com.goodbird.player2npc.Player2NPCForge;
import com.goodbird.player2npc.capability.CompanionCapability;
import com.goodbird.player2npc.capability.CompanionProvider;
import com.goodbird.player2npc.client.ClientSetup;
import com.goodbird.player2npc.client.gui.CharacterSelectionScreen;
import com.goodbird.player2npc.companion.CompanionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEvents {
    private static long lastHeartbeatTime = System.nanoTime();

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(CompanionCapability.INSTANCE).ifPresent(CompanionManager::summonAllCompanionsAsync);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer player) {
            player.getCapability(CompanionCapability.INSTANCE).ifPresent(CompanionManager::serverTick);
        }
    }

    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ServerPlayer player) {
            event.addCapability(new ResourceLocation(Player2NPCForge.MOD_ID, "companion_manager"), new CompanionProvider(player));
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        long now = System.nanoTime();
        if (now - lastHeartbeatTime > 60_000_000_000L) {
            AICommandBridge.sendHeartbeat("player2-ai-npc-minecraft");
            lastHeartbeatTime = now;
        }

        if (ClientSetup.openCharacterScreenKeybind.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                mc.setScreen(new CharacterSelectionScreen());
            }
        }
    }
}