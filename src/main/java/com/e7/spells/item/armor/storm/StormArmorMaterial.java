package com.e7.spells.item.armor.storm;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.HashMap;

public class StormArmorMaterial implements ArmorMaterial
{
    private static final HashMap<ArmorItem.Type, Integer> BASE_DURABILITY = new HashMap<>();
    private static final HashMap<ArmorItem.Type, Integer> PROTECTION_VALUES = new HashMap<>();
    private static final int DURABILITY_MULTIPLIER = 4;
    private static final int ENCHANTABILITY = 14;
    private static final int TOUGHNESS = 0;
    private static final int KNOCKBACK_RESISTANCE = 0;

    static {
        BASE_DURABILITY.put(ArmorItem.Type.HELMET, 11*DURABILITY_MULTIPLIER);
        BASE_DURABILITY.put(ArmorItem.Type.CHESTPLATE, 13*DURABILITY_MULTIPLIER);
        BASE_DURABILITY.put(ArmorItem.Type.LEGGINGS, 12*DURABILITY_MULTIPLIER);
        BASE_DURABILITY.put(ArmorItem.Type.BOOTS, 10*DURABILITY_MULTIPLIER);

        PROTECTION_VALUES.put(ArmorItem.Type.HELMET, 3);
        PROTECTION_VALUES.put(ArmorItem.Type.CHESTPLATE, 7);
        PROTECTION_VALUES.put(ArmorItem.Type.LEGGINGS, 5);
        PROTECTION_VALUES.put(ArmorItem.Type.BOOTS, 3);
    }

    @Override
    public int getDurability(ArmorItem.Type type)
    {
        return BASE_DURABILITY.get(type);
    }

    @Override
    public int getProtection(ArmorItem.Type type)
    {
        return PROTECTION_VALUES.get(type);
    }

    @Override
    public int getEnchantability() {
        return ENCHANTABILITY;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.DIAMOND);
    }

    @Override
    public String getName() {
        // Must be all lowercase
        return "storm";
    }

    @Override
    public float getToughness() {
        return TOUGHNESS;
    }

    @Override
    public float getKnockbackResistance() {
        return KNOCKBACK_RESISTANCE;
    }
}
