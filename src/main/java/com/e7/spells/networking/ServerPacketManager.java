package com.e7.spells.networking;

import com.e7.spells.item.tools.AspectOfTheEnd;
import com.e7.spells.item.tools.WitherBlade;
import com.e7.spells.networking.payloads.UseAotePacket;
import com.e7.spells.networking.payloads.UseHyperionPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;


public class ServerPacketManager
{
    public static void sendPacketToClient(ServerPlayerEntity player, CustomPayload packet)
    {
        ServerPlayNetworking.send(player, packet);
    }

    public static void registerPacketListeners()
    {
//        ServerPlayNetworking.registerGlobalReceiver(InitPlayerNbtPacket.ID, ((payload, context) -> {
//            // sent by the client when they connect to the server.
//
//            ServerPlayerEntity player = context.player();
//            NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
//            // looks stupid, but it just makes sure that it exists so that it can actually regen.
//            int charges = nbt.getInt("zombie_sword_charges");
//            nbt.putInt("zombie_sword_charges", charges);
//            ZombieSwordItem.syncChargesNbtWithPlayer(player);
//        }));
        ServerPlayNetworking.registerGlobalReceiver(UseAotePacket.ID, ((payload, context) -> {

            AspectOfTheEnd.doTeleport(context.player(), payload.vec());
        }));
        ServerPlayNetworking.registerGlobalReceiver(UseHyperionPacket.ID, ((payload, context) -> {

            WitherBlade.doWitherImpact(context.player(), payload.vec());
        }));
    }
}
