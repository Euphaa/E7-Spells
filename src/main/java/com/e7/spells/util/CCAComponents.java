package com.e7.spells.util;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public final class CCAComponents implements EntityComponentInitializer
{
    private static final int MANA_REGEN_MULTIPLIER = 1;
    public static final ComponentKey<PlayerNbtComponent> PLAYER_NBT =
            ComponentRegistry.getOrCreate(new Identifier("e7-spells", "player_nbt"), PlayerNbtComponent.class);


    public static void syncAllPlayers(MinecraftServer server)
    {
        for (ServerPlayerEntity player : PlayerLookup.all(server))
        {
            PLAYER_NBT.sync(player);
        }
    }

    public static void addManaToAllPlayers(MinecraftServer server)
    {
        for (ServerPlayerEntity player : PlayerLookup.all(server))
        {
            PLAYER_NBT.get(player).addMana(MANA_REGEN_MULTIPLIER);
        }
    }


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerFor(PlayerEntity.class, PLAYER_NBT, PlayerNbtComponent::new);
    }
}
