package com.e7.spells.item;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.function.Supplier;

public class ModArmorMaterials
{

    public static final HashMap<RegistryEntry<ArmorMaterial>, HashMap<ArmorItem.Type, Integer>> ARMOR_DEFENSE_VALUES = new HashMap<>();
    public static final RegistryEntry<ArmorMaterial> STORM;

    public static final RegistryEntry<ArmorMaterial> GOLDOR;
    public static final RegistryEntry<ArmorMaterial> NECRON;
    public static final RegistryEntry<ArmorMaterial> MAXOR;


    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(new Identifier(id)));
        return register(id, defense, enchantability, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap(ArmorItem.Type.class);
        ArmorItem.Type[] var9 = ArmorItem.Type.values();
        int var10 = var9.length;

        for(int var11 = 0; var11 < var10; ++var11) {
            ArmorItem.Type type = var9[var11];
            enumMap.put(type, defense.get(type));
        }

        return Registry.registerReference(Registries.ARMOR_MATERIAL, new Identifier(id), new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }

    //will give each attribute _per_ piece of armor
    public static AttributeModifiersComponent makeArmorAttributes(ArmorItem.Type type, RegistryEntry<ArmorMaterial> material, ArmorModifier... modifiers)
    {
        AttributeModifierSlot slot = AttributeModifierSlot.ARMOR;
        switch (type)
        {
            case BOOTS -> slot = AttributeModifierSlot.FEET;
            case LEGGINGS -> slot = AttributeModifierSlot.LEGS;
            case CHESTPLATE -> slot = AttributeModifierSlot.CHEST;
            case HELMET -> slot = AttributeModifierSlot.HEAD;
        }
        AttributeModifiersComponent.Builder component = AttributeModifiersComponent.builder();
        component.add(
                EntityAttributes.GENERIC_ARMOR,
                new EntityAttributeModifier(UUID.randomUUID(), "Armor Attributes", ARMOR_DEFENSE_VALUES.get(material).get(type), EntityAttributeModifier.Operation.ADD_VALUE),
                slot
        );
        for (ArmorModifier modifier : modifiers)
        {
            component.add(
                    modifier.attribute(),
                    new EntityAttributeModifier(UUID.randomUUID(), "Armor Attributes", modifier.value(), modifier.operation()),
                    slot
            );
        }
        return component.build();

    }


    static {
        final HashMap<ArmorItem.Type, Integer> storm = new HashMap<>(
                Map.ofEntries(
                        Map.entry(ArmorItem.Type.BOOTS, 3),
                        Map.entry(ArmorItem.Type.LEGGINGS, 6),
                        Map.entry(ArmorItem.Type.CHESTPLATE, 8),
                        Map.entry(ArmorItem.Type.HELMET, 3)
                )
        );
        STORM = register(
                "storm",
                Util.make(
                        new EnumMap(ArmorItem.Type.class),
                        (map) -> map.putAll(storm)
                ),
                15,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                0.0F,
                0.0F,
                () -> Ingredient.ofItems(Items.ENDER_PEARL)
        );
        ARMOR_DEFENSE_VALUES.put(STORM, storm);

        final HashMap<ArmorItem.Type, Integer> necron = new HashMap<>(
                Map.ofEntries(
                        Map.entry(ArmorItem.Type.BOOTS, 3),
                        Map.entry(ArmorItem.Type.LEGGINGS, 6),
                        Map.entry(ArmorItem.Type.CHESTPLATE, 8),
                        Map.entry(ArmorItem.Type.HELMET, 4)
                )
        );
        NECRON = register(
                "necron",
                Util.make(
                        new EnumMap(ArmorItem.Type.class),
                        (map) -> map.putAll(necron)
                ),
                15,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                2F,
                0.0F,
                () -> Ingredient.ofItems(Items.ENDER_PEARL)
        );
        ARMOR_DEFENSE_VALUES.put(NECRON, necron);

        final HashMap<ArmorItem.Type, Integer> maxor = new HashMap<>(
                Map.ofEntries(
                        Map.entry(ArmorItem.Type.BOOTS, 3),
                        Map.entry(ArmorItem.Type.LEGGINGS, 6),
                        Map.entry(ArmorItem.Type.CHESTPLATE, 8),
                        Map.entry(ArmorItem.Type.HELMET, 3)
                )
        );
        MAXOR = register(
                "maxor",
                Util.make(
                        new EnumMap(ArmorItem.Type.class),
                        (map) -> map.putAll(maxor)
                ),
                15,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                2F,
                0.0F,
                () -> Ingredient.ofItems(Items.ENDER_PEARL)
        );
        ARMOR_DEFENSE_VALUES.put(MAXOR, maxor);

        final HashMap<ArmorItem.Type, Integer> goldor = new HashMap<>(
                Map.ofEntries(
                        Map.entry(ArmorItem.Type.BOOTS, 4),
                        Map.entry(ArmorItem.Type.LEGGINGS, 8),
                        Map.entry(ArmorItem.Type.CHESTPLATE, 9),
                        Map.entry(ArmorItem.Type.HELMET, 5)
                )
        );
        GOLDOR = register(
                "goldor",
                Util.make(new EnumMap(ArmorItem.Type.class),
                        (map) -> map.putAll(goldor)),
                15,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                3F,
                2F,
                () -> Ingredient.ofItems(Items.GOLD_INGOT)
        );
        ARMOR_DEFENSE_VALUES.put(GOLDOR, goldor);
    }
}
