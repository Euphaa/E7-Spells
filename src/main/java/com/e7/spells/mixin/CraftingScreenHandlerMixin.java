package com.e7.spells.mixin;

import com.e7.spells.E7SpellsCommon;
import com.e7.spells.item.ModItems;
import com.e7.spells.util.WitherScrollsData;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin extends AbstractRecipeScreenHandler<RecipeInputInventory>
{


    public CraftingScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i)
    {
        super(screenHandlerType, i);
    }

    @Inject(method = "updateResult", at = @At("RETURN"), cancellable = true)
    private static void injectWriteMethod(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, CallbackInfo ci) {

        if (craftingInventory.getStack(4).isOf(ModItems.HYPERION_SWORD) || craftingInventory.getStack(4).isOf(ModItems.ASTRAEA_SWORD)
                || craftingInventory.getStack(4).isOf(ModItems.VALKYRIE_SWORD) || craftingInventory.getStack(4).isOf(ModItems.SCYLLA_SWORD))
        {
            Item upgradeItem = craftingInventory.getStack(0).getItem();
            if (upgradeItem == null) return;
            System.out.println(upgradeItem);
            for (int i = 1; i < 4; i++)
            {
                if (!craftingInventory.getStack(i).isOf(upgradeItem)) {
                    System.out.println("Slot " + i + " has a different item");
                    return;
                }
            }
            for (int i = 5; i < 8; i++)
            {
                if (!craftingInventory.getStack(i).isOf(upgradeItem)) {
                    System.out.println("Slot " + i + " has a different item");
                    return;
                }
            }
            ItemStack newOutput;
            switch (Registries.ITEM.getId(upgradeItem).toString())
            {
                case "minecraft:ender_eye" -> newOutput = new ItemStack(ModItems.HYPERION_SWORD);
                case "minecraft:gold_ingot" -> newOutput = new ItemStack(ModItems.ASTRAEA_SWORD);
                case "minecraft:chorus_fruit" -> newOutput = new ItemStack(ModItems.SCYLLA_SWORD);
                case "minecraft:apple" -> newOutput = new ItemStack(ModItems.VALKYRIE_SWORD);
                default ->
                {
                    return;
                }
            }



            newOutput.applyComponentsFrom(craftingInventory.getStack(4).getComponents());
            newOutput.applyComponentsFrom(newOutput.getItem().getComponents());
            System.out.println(newOutput.getItem().getComponents());
            resultInventory.setStack(0, newOutput);
            ci.cancel();
        }
    }
}
