package com.e7.spells.networking;

import com.e7.spells.networking.payloads.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModPayloads
{
    public static void registerPayloads()
    {
        PayloadTypeRegistry.playC2S().register(UseHyperionPacket.ID, UseHyperionPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(UseAotePacket.ID, UseAotePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(InitPlayerNbtPacket.ID, InitPlayerNbtPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(AoteParticleAnimationPacket.ID, AoteParticleAnimationPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(FerocityParticleAnimationPacket.ID, FerocityParticleAnimationPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(HyperionParticleAnimationPacket.ID, HyperionParticleAnimationPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ZombieSwordParticleAnimationPacket.ID, ZombieSwordParticleAnimationPacket.CODEC);
    }
}
