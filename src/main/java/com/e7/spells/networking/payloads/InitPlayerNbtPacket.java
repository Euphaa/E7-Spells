package com.e7.spells.networking.payloads;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record InitPlayerNbtPacket() implements CustomPayload
{
    public static final Id<InitPlayerNbtPacket> ID = CustomPayload.id("e7-spells:init_player_nbt");
    public static final PacketCodec<PacketByteBuf, InitPlayerNbtPacket> CODEC = PacketCodec.of(
        (self, buf) -> {
            // encoder (write info from self to buf)
        },
        buf -> {
            // decoder (create new instance by reading from buf)
            return new InitPlayerNbtPacket();
        }
);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
