package com.goodbird.player2npc;

import com.goodbird.player2npc.client.ClientSetup;
import com.goodbird.player2npc.companion.AutomatoneEntity;
import com.goodbird.player2npc.event.ForgeEvents;
import com.goodbird.player2npc.network.ForgeNetwork;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Player2NPCForge.MOD_ID)
public class Player2NPCForge {
   public static final Logger LOGGER = LogManager.getLogger("Otomaton");
   public static final String MOD_ID = "player2npc";

   private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

   public static final RegistryObject<EntityType<AutomatoneEntity>> AUTOMATONE = ENTITY_TYPES.register("aicompanion",
           () -> EntityType.Builder.<AutomatoneEntity>of(AutomatoneEntity::new, MobCategory.MISC)
                   .sized(EntityType.PLAYER.getWidth(), EntityType.PLAYER.getHeight())
                   .clientTrackingRange(64)
                   .updateInterval(1)
                   .build("aicompanion"));

   public Player2NPCForge() {
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

      ENTITY_TYPES.register(modEventBus);

      modEventBus.addListener(this::onEntityAttributeCreation);

      MinecraftForge.EVENT_BUS.register(new ForgeEvents());
      MinecraftForge.EVENT_BUS.register(this);

      ForgeNetwork.register();
   }

   @SubscribeEvent
   public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
      event.put(AUTOMATONE.get(), Zombie.createAttributes().build());
   }
}