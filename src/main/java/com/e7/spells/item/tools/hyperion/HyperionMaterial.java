package com.e7.spells.item.tools.hyperion;

import com.e7.spells.item.tools.zombie_sword.ZombieToolMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class HyperionMaterial implements ToolMaterial
{
    public static final ZombieToolMaterial INSTANCE = new ZombieToolMaterial();

    @Override
    public int getDurability() {
        return 1400;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1f;
    }

    @Override
    public float getAttackDamage() {
        return 6F;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 18;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.STICK);
    }
}
