package com.e7.spells.item.tools;

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

public abstract class WeaponItem extends SwordItem
{


    public WeaponItem(ToolMaterial toolMaterial, Settings settings)
    {
        super(toolMaterial, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed) {
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
                .build();
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
