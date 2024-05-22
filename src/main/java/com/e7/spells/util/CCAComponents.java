package com.e7.spells.util;

import com.e7.spells.E7SpellsCommon;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public final class CCAComponents implements EntityComponentInitializer
{
    private static final float MANA_REGEN_MULTIPLIER = .15f;
    public static final ComponentKey<PlayerNbtComponent> PLAYER_NBT =
            ComponentRegistry.getOrCreate(E7SpellsCommon.id("player_nbt"), PlayerNbtComponent.class);


    public static void syncAllPlayers(MinecraftServer server)
    {
        for (ServerPlayerEntity player : PlayerLookup.all(server))
        {
            PLAYER_NBT.sync(player);
        }
    }

    public static void updatePlayerNbt(MinecraftServer server)
    {
        for (ServerPlayerEntity player : PlayerLookup.all(server))
        {
            PLAYER_NBT.get(player).addMana(MANA_REGEN_MULTIPLIER * E7SpellsCommon.PLAYER_NBT_UPDATE_TICK_INTERVAL);
            PLAYER_NBT.get(player).incrementWither_shield_cooldown(E7SpellsCommon.PLAYER_NBT_UPDATE_TICK_INTERVAL);
        }
    }


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerFor(PlayerEntity.class, PLAYER_NBT, PlayerNbtComponent::new);
    }
}
