package com.e7.spells.item.tools.zombie_sword;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ZombieToolMaterial implements ToolMaterial
{
    public static final ZombieToolMaterial INSTANCE = new ZombieToolMaterial();

    @Override
    public int getDurability() {
        return 500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1f;
    }

    @Override
    public float getAttackDamage() {
        return 3F;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.ROTTEN_FLESH);
    }


}
