package com.e7.spells.networking;

import com.e7.spells.statuseffects.FerocityStatusEffect;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ClientPacketManager
{

    public static void registerPacketListeners()
    {
        ClientPlayNetworking.registerGlobalReceiver(E7Packets.FEROCITY_PARTICLE_ANIMATION,
                (client, handler, buf, responseSender) -> {
            double[] coords = new double[]{
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble()
            };
            client.execute(() -> {
                FerocityStatusEffect.createFerocityParticles(client, coords[0], coords[1], coords[2]);
            });
        });
    }

    public static void sendPacketToClient(Identifier channel, PacketByteBuf buf)
    {
        ClientPlayNetworking.send(channel, buf);
    }
}
