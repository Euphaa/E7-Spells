package com.e7.spells;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;

public class EffectHandler
{
    public static void applyPotionEffect(PlayerEntity player, StatusEffect effect, int duration, int amplifier) {
        StatusEffectInstance effectInstance = new StatusEffectInstance(effect, duration, amplifier);
        player.addStatusEffect(effectInstance);
    }
}