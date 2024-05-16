package com.e7.spells;

import com.e7.spells.item.ModItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions
{
    public static Potion EXAMPLE_POTION;
    public static Potion LONG_EXAMPLE_POTION;
    public static Potion EXAMPLE_POTION_2;
    public static Potion EXAMPLE_POTION_3;

    public static void registerPotions()
    {
        EXAMPLE_POTION =
                Registry.register(Registries.POTION, new Identifier(E7Spells.MODID, "ferocity_potion_0"),
                        new Potion(new StatusEffectInstance(E7Spells.FEROCITY, 3600, 0)));
        LONG_EXAMPLE_POTION =
                Registry.register(Registries.POTION, new Identifier("e7-spells", "long_ferocity_potion_0"),
                        new Potion(new StatusEffectInstance(E7Spells.FEROCITY, 9600, 0)));
        EXAMPLE_POTION_2 =
                Registry.register(Registries.POTION, new Identifier("e7-spells", "ferocity_potion_1"),
                        new Potion(new StatusEffectInstance(E7Spells.FEROCITY, 2400, 1)));
        EXAMPLE_POTION_3 =
                Registry.register(Registries.POTION, new Identifier("e7-spells", "ferocity_potion_2"),
                        new Potion(new StatusEffectInstance(E7Spells.FEROCITY, 1800, 2)));
        registerPotionsRecipes();
    }

    public static void registerPotionsRecipes(){
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ModItems.SMILE, EXAMPLE_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(EXAMPLE_POTION, Items.REDSTONE, LONG_EXAMPLE_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(EXAMPLE_POTION, ModItems.GUN, EXAMPLE_POTION_2);
        BrewingRecipeRegistry.registerPotionRecipe(EXAMPLE_POTION_2, ModItems.GUN, EXAMPLE_POTION_3);
    }
}
