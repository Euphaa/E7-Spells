package com.e7.spells.statuseffects;

import com.e7.spells.E7SpellsCommon;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects
{
    public static final StatusEffect FEROCITY = Registry.register(Registries.STATUS_EFFECT, new Identifier(E7SpellsCommon.MODID, "ferocity"), new FerocityStatusEffect());

    public static void registerEffects()
    {

    }
}
