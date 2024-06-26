package com.e7.spells.item.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Gun extends Item {

    public Gun(Settings settings) {
        super(settings);
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient){

            if (hand == Hand.MAIN_HAND) {

                player.kill();

                //player.getStackInHand(hand).decrement(1);
            }
        }
        return TypedActionResult.success((player.getStackInHand(hand)));
    }

}
