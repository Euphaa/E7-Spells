package com.e7.spells.networking;

import com.e7.spells.statuseffects.FerocityStatusEffect;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

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
}
