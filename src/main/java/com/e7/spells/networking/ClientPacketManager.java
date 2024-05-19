package com.e7.spells.networking;

import com.e7.spells.item.tools.AspectOfTheEndSwordItem;
import com.e7.spells.item.tools.HyperionSwordItem;
import com.e7.spells.item.tools.ZombieSwordItem;
import com.e7.spells.networking.payloads.AoteParticleAnimationPacket;
import com.e7.spells.networking.payloads.FerocityParticleAnimationPacket;
import com.e7.spells.networking.payloads.HyperionParticleAnimationPacket;
import com.e7.spells.networking.payloads.ZombieSwordParticleAnimationPacket;
import com.e7.spells.statuseffects.FerocityStatusEffect;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class ClientPacketManager
{

    public static void registerPacketListeners()
    {
        ClientPlayNetworking.registerGlobalReceiver(FerocityParticleAnimationPacket.ID, (payload, context) -> {

            FerocityStatusEffect.createFerocityParticles(payload.vec());
        });
        ClientPlayNetworking.registerGlobalReceiver(ZombieSwordParticleAnimationPacket.ID, (payload, context) -> {

            ZombieSwordItem.doParticleAnimation(payload.vec());
        });
//        ClientPlayNetworking.registerGlobalReceiver(E7Packets.SYNC_ZOMBIE_SWORD_CHARGES,
//                (client, handler, buf, responseSender) -> {
//
//            int charges = buf.readInt();
//            ((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().putInt("zombie_sword_charges", charges);
//        });
        ClientPlayNetworking.registerGlobalReceiver(AoteParticleAnimationPacket.ID, (payload, context) -> {

            AspectOfTheEndSwordItem.doParticleAnimation(payload.vec());
        });
        ClientPlayNetworking.registerGlobalReceiver(HyperionParticleAnimationPacket.ID, (payload, context) -> {

            HyperionSwordItem.doParticleAnimation(payload.vec());
        });
    }

    public static void sendPacketToServer(CustomPayload payload)
    {
        ClientPlayNetworking.send(payload);
    }
}
