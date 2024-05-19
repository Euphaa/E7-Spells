package com.e7.spells.networking.payloads;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;

public record AoteParticleAnimationPacket(Vec3d vec) implements CustomPayload
{
    public static final Id<AoteParticleAnimationPacket> ID = CustomPayload.id("e7-spells:aote_particle_animation_packet");
    public static final PacketCodec<PacketByteBuf, AoteParticleAnimationPacket> CODEC = PacketCodec.of(
        (self, buf) -> {
            // encoder (write info from self to buf)
            buf.writeDouble(self.vec.getX());
            buf.writeDouble(self.vec.getY());
            buf.writeDouble(self.vec.getZ());
        },
        buf -> {
            // decoder (create new instance by reading from buf)
            return new AoteParticleAnimationPacket(
                new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble())
            );
        }
);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
