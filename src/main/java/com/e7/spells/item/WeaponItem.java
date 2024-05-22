package com.e7.spells.item;

import com.e7.spells.item.ItemModifier;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class WeaponItem extends SwordItem
{
    public WeaponItem(ToolMaterial material, int damage, float attackSpeed, ItemModifier... modifiers)
    {
        super(material, new Settings().attributeModifiers(createAttributeModifiers(material, damage, attackSpeed, modifiers)));
    }


    public WeaponItem(ToolMaterial toolMaterial, Settings settings)
    {
        super(toolMaterial, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int damage, float attackSpeed, ItemModifier... modifiers) {
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
        builder.add(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        ATTACK_DAMAGE_MODIFIER_ID, "Weapon Modifier", ((float) damage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        )
        .add(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon Modifier", attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                AttributeModifierSlot.MAINHAND
        );
        for (ItemModifier modifier : modifiers)
        {
            builder.add(
                    modifier.attribute(),
                    new EntityAttributeModifier(UUID.randomUUID(), "Weapon Modifier", modifier.value(), modifier.operation()),
                    AttributeModifierSlot.MAINHAND
            );
        }
        return builder.build();
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed, float reach) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", ((float) baseAttackDamage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", reach, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }
}
