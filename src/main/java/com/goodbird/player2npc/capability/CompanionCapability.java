package com.goodbird.player2npc.capability;

import com.goodbird.player2npc.companion.CompanionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CompanionCapability {
    public static final Capability<CompanionManager> INSTANCE =
            CapabilityManager.get(new CapabilityToken<>() {});
}