package com.e7.spells.item;


import com.e7.spells.E7SpellsCommon;
import com.e7.spells.item.items.Gun;
import com.e7.spells.item.items.Smile;
import com.e7.spells.item.tools.AspectOfTheEnd;
import com.e7.spells.item.tools.Hyperion;
import com.e7.spells.item.tools.ZombieSword;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    private static final HashMap<ArmorItem.Type, String> armorSuffixes = new HashMap<>();

    static {
        armorSuffixes.put(ArmorItem.Type.HELMET, "_helmet");
        armorSuffixes.put(ArmorItem.Type.CHESTPLATE, "_chestplate");
        armorSuffixes.put(ArmorItem.Type.LEGGINGS, "_leggings");
        armorSuffixes.put(ArmorItem.Type.BOOTS, "_boots");
    }

    public static final Item SMILE = registerItem("smile", new Smile(new Item.Settings()));
    public static final Item GUN = registerItem("gun", new Gun(new Item.Settings()));
    public static final Item ZOMBIE_SWORD = registerItem("zombie_sword", new ZombieSword());
    public static final Item ASPECT_OF_THE_END_SWORD = registerItem("aspect_of_the_end_sword", new AspectOfTheEnd());
    public static final Item HYPERION_SWORD = registerItem("hyperion_sword", new Hyperion());
//    public static final ArmorMaterial STORM_MATERIAL = new StormArmorMaterial();
    public static final Map<ArmorItem.Type, Item> STORM_ARMOR = makeArmorSet("storm", ModArmorMaterials.STORM, 49);
    public static final Map<ArmorItem.Type, Item> NECRON_ARMOR = makeArmorSet(
            "necron",
            ModArmorMaterials.NECRON,
            49,
            new ArmorModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, .2f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );
    public static final Map<ArmorItem.Type, Item> GOLDOR_ARMOR = makeArmorSet(
            "goldor",
            ModArmorMaterials.GOLDOR,
            49,
            new ArmorModifier(EntityAttributes.GENERIC_MAX_HEALTH, 4, EntityAttributeModifier.Operation.ADD_VALUE)
    );
    public static final Map<ArmorItem.Type, Item> MAXOR_ARMOR = makeArmorSet(
            "maxor",
            ModArmorMaterials.MAXOR,
            49,
            new ArmorModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, .15f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );


    //Registers the item into the game

    public static void registerModItemsForServer()
    {
        E7SpellsCommon.E7SPELLS.info("Registering Mod Items for " + E7SpellsCommon.MODID);


    }

    public static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(E7SpellsCommon.MODID, name), item);
    }
    public static Item makeArmorPiece(String name, RegistryEntry<ArmorMaterial> material, int durability, ArmorItem.Type type)
    {
        return registerItem(
                name + armorSuffixes.get(type),
                new ArmorItem(
                        material,
                        type,
                        new Item.Settings()
                                // gives durability
                                .maxDamage(type.getMaxDamage(durability))
                                .attributeModifiers(ModArmorMaterials.makeArmorAttributes(type, material))
                )
        );
    }

    public static HashMap<ArmorItem.Type, Item> makeArmorSet(String name, RegistryEntry<ArmorMaterial> material, int durability)
    {
        HashMap<ArmorItem.Type, Item> map = new HashMap<>();
        map.put(
            ArmorItem.Type.HELMET,
            makeArmorPiece(name, material, durability, ArmorItem.Type.HELMET)
        );
        map.put(
            ArmorItem.Type.CHESTPLATE,
            makeArmorPiece(name, material, durability, ArmorItem.Type.CHESTPLATE)
        );
        map.put(
            ArmorItem.Type.LEGGINGS,
            makeArmorPiece(name, material, durability, ArmorItem.Type.LEGGINGS)
        );
        map.put(
            ArmorItem.Type.BOOTS,
            makeArmorPiece(name, material, durability, ArmorItem.Type.BOOTS)
        );
        return map;
    }

    public static Item makeArmorPiece(String name, RegistryEntry<ArmorMaterial> material, int durability, ArmorItem.Type type, ArmorModifier... modifiers)
    {
        return registerItem(
                name + armorSuffixes.get(type),
                new ArmorItem(
                        material,
                        type,
                        new Item.Settings()
                                .maxDamage(type.getMaxDamage(durability))
                                .attributeModifiers(ModArmorMaterials.makeArmorAttributes(type, material, modifiers))
                )
        );
    }

    public static HashMap<ArmorItem.Type, Item> makeArmorSet(String name, RegistryEntry<ArmorMaterial> material, int durability, ArmorModifier... modifiers)
    {
        HashMap<ArmorItem.Type, Item> map = new HashMap<>();
        map.put(
            ArmorItem.Type.HELMET,
            makeArmorPiece(name, material, durability, ArmorItem.Type.HELMET, modifiers)
        );
        map.put(
            ArmorItem.Type.CHESTPLATE,
            makeArmorPiece(name, material, durability, ArmorItem.Type.CHESTPLATE, modifiers)
        );
        map.put(
            ArmorItem.Type.LEGGINGS,
            makeArmorPiece(name, material, durability, ArmorItem.Type.LEGGINGS, modifiers)
        );
        map.put(
            ArmorItem.Type.BOOTS,
            makeArmorPiece(name, material, durability, ArmorItem.Type.BOOTS, modifiers)
        );
        return map;
    }
}
