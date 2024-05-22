package com.e7.spells.item;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;

public record ItemModifier(RegistryEntry<EntityAttribute> attribute, double value, EntityAttributeModifier.Operation operation)
{
    public static ItemModifier create(RegistryEntry<EntityAttribute> attribute, double value)
    {
        return new ItemModifier(attribute, value, EntityAttributeModifier.Operation.ADD_VALUE);
    }
}
