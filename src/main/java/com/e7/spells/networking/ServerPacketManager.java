package com.e7.spells.networking;

import com.e7.spells.E7Spells;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ServerPacketManager
{

    public static void sendPacketToClient(ServerPlayerEntity user, Identifier channelName, PacketByteBuf buf)
    {
        ServerPlayNetworking.send(user, channelName, buf);
    }

    public static void registerPacketListeners()
    {
        ServerPlayNetworking.registerGlobalReceiver(E7Packets.FEROCITY_KEY_ACTIVATION, ((server, player, handler, buf, responseSender) -> {

            server.execute(() -> {
                player.addStatusEffect(new StatusEffectInstance(E7Spells.FEROCITY, 10*20, 2));
            });
        }));
    }
}
