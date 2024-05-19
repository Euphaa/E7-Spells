package com.e7.spells;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes
{
    public static final RegistryKey<DamageType> MAGIC_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("e7-spells", "magic_damage_type"));


    public static DamageSource of(World world, RegistryKey<DamageType> key) {
//        VanillaDamageTypeTagProvider.
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

}
