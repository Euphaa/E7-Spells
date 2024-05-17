package com.e7.spells.networking;

import com.e7.spells.item.zombie_tools.ZombieSwordItem;
import com.e7.spells.statuseffects.FerocityStatusEffect;
import com.e7.spells.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class ClientPacketManager
{

    public static void registerPacketListeners()
    {
        ClientPlayNetworking.registerGlobalReceiver(E7Packets.FEROCITY_PARTICLE_ANIMATION,
                (client, handler, buf, responseSender) -> {

            Vec3d pos = E7Packets.unpackVec3d(buf);
            client.execute(() -> {
                FerocityStatusEffect.createFerocityParticles(client, pos);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(E7Packets.ZOMBIE_SWORD_PARTICLE_ANIMATION,
                (client, handler, buf, responseSender) -> {

            Vec3d pos = client.player.getPos();
            client.execute(() -> {
                ZombieSwordItem.doParticleAnimation(pos, client);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(E7Packets.SYNC_ZOMBIE_SWORD_CHARGES,
                (client, handler, buf, responseSender) -> {

            int charges = buf.readInt();
            client.execute(() -> {
                ((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().putInt("zombie_sword_charges", charges);
            });
        });
    }

    public static void sendPacketToServer(Identifier channel, PacketByteBuf buf)
    {
        ClientPlayNetworking.send(channel, buf);
    }
}
