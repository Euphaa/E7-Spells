package com.e7.spells.item.items;

import com.e7.spells.statuseffects.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class Smile extends Item {

    public Smile(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient){

            if (hand == Hand.MAIN_HAND) {

                player.addStatusEffect (new StatusEffectInstance(ModStatusEffects.FEROCITY, 10*20, 1));

                //player.getStackInHand(hand).decrement(1);
            }
        }
        return TypedActionResult.success((player.getStackInHand(hand)));
    }

}
