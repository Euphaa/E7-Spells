package com.e7.spells.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ServerPacketManager
{

    public static void sendPacketToClient(ServerPlayerEntity user, Identifier channelName, PacketByteBuf buf)
    {
        ServerPlayNetworking.send(user, channelName, buf);
    }
}
