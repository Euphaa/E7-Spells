package com.e7.spells;

import com.e7.spells.item.ModItems;
import com.e7.spells.statuseffects.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;

public class ModPotions
{
    public static final Potion EXAMPLE_POTION =
            Registry.register(Registries.POTION, new Identifier(E7SpellsCommon.MODID, "ferocity_potion_0"),
                    new Potion(new StatusEffectInstance(RegistryEntry.of(ModStatusEffects.FEROCITY), 3600, 0)));
    public static final Potion LONG_EXAMPLE_POTION =
            Registry.register(Registries.POTION, new Identifier("e7-spells", "long_ferocity_potion_0"),
            new Potion(new StatusEffectInstance(RegistryEntry.of(ModStatusEffects.FEROCITY), 9600, 0)));
    public static final Potion EXAMPLE_POTION_2 =
            Registry.register(Registries.POTION, new Identifier("e7-spells", "ferocity_potion_1"),
                    new Potion(new StatusEffectInstance(RegistryEntry.of(ModStatusEffects.FEROCITY), 2400, 1)));
    public static final Potion EXAMPLE_POTION_3 =
            Registry.register(Registries.POTION, new Identifier("e7-spells", "ferocity_potion_2"),
                    new Potion(new StatusEffectInstance(RegistryEntry.of(ModStatusEffects.FEROCITY), 1800, 2)));

    public static void registerPotions()
    {
        // recipes
        BrewingRecipeRegistry.Builder builder = new BrewingRecipeRegistry.Builder(FeatureSet.empty());
        builder.registerPotionRecipe(Potions.AWKWARD, ModItems.SMILE, RegistryEntry.of(EXAMPLE_POTION));
        builder.registerPotionRecipe(RegistryEntry.of(EXAMPLE_POTION), Items.REDSTONE, RegistryEntry.of(LONG_EXAMPLE_POTION));
        builder.registerPotionRecipe(RegistryEntry.of(EXAMPLE_POTION), ModItems.GUN, RegistryEntry.of(EXAMPLE_POTION_2));
        builder.registerPotionRecipe(RegistryEntry.of(EXAMPLE_POTION_2), ModItems.GUN, RegistryEntry.of(EXAMPLE_POTION_3));

    }

}
