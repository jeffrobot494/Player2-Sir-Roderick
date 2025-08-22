package com.goodbird.player2npc.capability;

import com.goodbird.player2npc.companion.CompanionManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompanionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private final CompanionManager companionManager;
    private final LazyOptional<CompanionManager> optional;

    public CompanionProvider(ServerPlayer player) {
        this.companionManager = new CompanionManager(player);
        this.optional = LazyOptional.of(() -> this.companionManager);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CompanionCapability.INSTANCE.orEmpty(cap, this.optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        companionManager.writeToNbt(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        companionManager.readFromNbt(nbt);
    }
}