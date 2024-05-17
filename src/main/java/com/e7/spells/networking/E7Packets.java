package com.e7.spells.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class E7Packets
{
    public static final Identifier FEROCITY_PARTICLE_ANIMATION = new Identifier("e7-spells", "ferocity_particle_animation");
    public static final Identifier FEROCITY_KEY_ACTIVATION = new Identifier("e7-spells", "ferocity_key_activation");
    public static final Identifier ZOMBIE_SWORD_PARTICLE_ANIMATION = new Identifier("e7-spells", "zombie_sword_particle_animation");
    public static final Identifier INITIATE_PLAYER_NBT = new Identifier("e7-spells", "init_player_nbt");
    public static final Identifier SYNC_ZOMBIE_SWORD_CHARGES = new Identifier("e7-spells", "sync_zombie_sword_charges");
    public static final Identifier USE_ASPECT_OF_THE_END = new Identifier("e7-spells", "use_aspect_of_the_end");
    public static final Identifier AOTE_PARTICLE_ANIMATION = new Identifier("e7-spells", "aote_particle_animation");


    public static Vec3d unpackVec3d(PacketByteBuf buf)
    {
        return new Vec3d(
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble()
        );
    }

    public static void packVec3d(PacketByteBuf buf, Vec3d pos)
    {
        buf.writeDouble(pos.getX());
        buf.writeDouble(pos.getY());
        buf.writeDouble(pos.getZ());
    }


}
