package com.e7.spells.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record WitherScrollsData(byte state)
//        implements DataComponentType<WitherScrollsDataComponent>
{
    public static final WitherScrollsData EMPTY = new WitherScrollsData((byte) 0);
    public static final Codec<WitherScrollsData> CODEC = Codec.BYTE.xmap(WitherScrollsData::new, WitherScrollsData::state);
    public static final PacketCodec<ByteBuf, WitherScrollsData> PACKET_CODEC = PacketCodecs.BYTE.xmap(WitherScrollsData::new, WitherScrollsData::state);
    public static final DataComponentType<WitherScrollsData> COMPONENT_TYPE
            = DataComponentType.<WitherScrollsData>builder()
            .codec(CODEC)
            .packetCodec(PACKET_CODEC)
            .build();
}
