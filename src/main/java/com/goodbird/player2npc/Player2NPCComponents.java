package com.goodbird.player2npc;

import baritone.KeepName;
import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.cache.IWorldProvider;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.IInteractionController;
import baritone.cache.WorldProvider;
import baritone.selection.SelectionManager;
import baritone.utils.player.EntityInteractionController;
import com.goodbird.player2npc.companion.AutomatoneEntity;
import com.goodbird.player2npc.companion.CompanionManager;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

@KeepName
public final class Player2NPCComponents implements EntityComponentInitializer, WorldComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, IInteractionController.KEY, EntityInteractionController::new);
        registry.registerFor(PlayerEntity.class, ISelectionManager.KEY, SelectionManager::new);
        registry.registerFor(PlayerEntity.class, IBaritone.KEY, BaritoneAPI.getProvider().componentFactory());

        registry.registerFor(ServerPlayerEntity.class, CompanionManager.KEY, CompanionManager::new);

        registry.registerFor(AutomatoneEntity.class, IInteractionController.KEY, EntityInteractionController::new);
        registry.registerFor(AutomatoneEntity.class, ISelectionManager.KEY, SelectionManager::new);
        registry.registerFor(AutomatoneEntity.class, IBaritone.KEY, BaritoneAPI.getProvider().componentFactory());
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(IWorldProvider.KEY, WorldProvider::new);
    }
}
