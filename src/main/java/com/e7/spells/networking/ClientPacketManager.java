package com.e7.spells.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientPacketManager
{

    public static void registerPacketListeners()
    {
        ClientPlayNetworking.registerGlobalReceiver(E7Packets.FEROCITY_PARTICLE_ANIMATION,
                (client, handler, buf, responseSender) -> {

        });
    }
}
