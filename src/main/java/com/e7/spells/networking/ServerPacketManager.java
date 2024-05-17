package com.e7.spells.networking;

import com.e7.spells.E7Spells;
import com.e7.spells.item.aote.AspectOfTheEndSwordItem;
import com.e7.spells.item.zombie_tools.ZombieSwordItem;
import com.e7.spells.util.IEntityDataSaver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;


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
        ServerPlayNetworking.registerGlobalReceiver(E7Packets.INITIATE_PLAYER_NBT, ((server, player, handler, buf, responseSender) -> {
            // sent by the client when they connect to the server.
            server.execute(() -> {
                NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
                // looks stupid, but it just makes sure that it exists so that it can actually regen.
                int charges = nbt.getInt("zombie_sword_charges");
                nbt.putInt("zombie_sword_charges", charges);
                ZombieSwordItem.syncChargesNbtWithPlayer(player);
            });
        }));
        ServerPlayNetworking.registerGlobalReceiver(E7Packets.USE_ASPECT_OF_THE_END, ((server, player, handler, buf, responseSender) -> {

            Vec3d pos = E7Packets.unpackVec3d(buf);
            server.execute(() -> {
                AspectOfTheEndSwordItem.doTeleport(player, pos);
//                AspectOfTheEndSwordItem.doTeleport(player, E7Packets.unpackVec3d(buf));
            });
        }));
    }
}
