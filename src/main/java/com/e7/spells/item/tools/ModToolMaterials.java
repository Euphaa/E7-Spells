package com.e7.spells.item.tools;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial
{
    END(BlockTags.INCORRECT_FOR_STONE_TOOL, 263, 0f, 4f, 12, () -> Ingredient.ofItems(Items.ENDER_EYE)),
    NECRON(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2020, 0f, 6f, 25, () -> Ingredient.ofItems(Items.NETHER_STAR)),
    ZOMBIE(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 250, 0f, 4f, 9, () -> Ingredient.ofItems(Items.ROTTEN_FLESH));

    private final TagKey<Block> inverseTag;
    private final int durability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(TagKey<Block> inverseTag, int durability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    ModToolMaterials(ToolMaterials material) {
        this(material.getInverseTag(),
                material.getDurability(),
                material.getMiningSpeedMultiplier(),
                material.getAttackDamage(),
                material.getEnchantability(),
                material::getRepairIngredient
        );
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return inverseTag;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
