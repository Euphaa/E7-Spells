//package com.e7.spells.util;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.component.DataComponentType;
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.network.codec.PacketCodec;
//
//public record WitherScrollsDataComponent(boolean hasShield, boolean hasImplosion, boolean hasWarp) implements DataComponentType<WitherScrollsDataComponent>
//{
//    @Override
//    public Codec<WitherScrollsDataComponent> getCodec()
//    {
//        return RecordCodecBuilder.create(instance -> instance.group(
//                    Codec.BOOL.fieldOf("has_wither_shield").forGetter(WitherScrollsDataComponent::hasShield),
//                    Codec.BOOL.fieldOf("has_implosion").forGetter(WitherScrollsDataComponent::hasImplosion),
//                    Codec.BOOL.fieldOf("has_shadow_warp").forGetter(WitherScrollsDataComponent::hasWarp)
//                ).apply(instance, WitherScrollsDataComponent::new)
//        );
//    }
//
//    @Override
//    public PacketCodec<PacketByteBuf, WitherScrollsDataComponent> getPacketCodec()
//    {
//        return PacketCodec.of(
//                (self, buf) -> {
//                    // encoder (write info from self to buf)
//                    buf.writeBoolean(this.hasShield());
//                    buf.writeBoolean(this.hasImplosion());
//                    buf.writeBoolean(this.hasWarp());
//                    System.out.println("encoding");
//                },
//                buf -> {
//                    // decoder (create new instance by reading from buf)
//                    System.out.println("decoding");
//                    return new WitherScrollsDataComponent(buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
//                }
//        );
//    }
//
//
//
//}
