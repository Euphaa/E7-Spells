package com.e7.spells.mixin;

import com.e7.spells.E7SpellsCommon;
import com.e7.spells.item.ModItems;
import com.e7.spells.util.WitherScrollsData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler
{


    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
    {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "onTakeOutput", at = @At("HEAD"))
    protected void injectWriteMethod(PlayerEntity player, ItemStack stack, CallbackInfo cir) {
        if (!player.getWorld().isClient())
        {
            System.out.println("output taken on server");
            if (stack.isOf(ModItems.HYPERION_SWORD))
            {
                System.out.println("output taken");

            }
        }

    }

    @Inject(method = "updateResult(Ljava/lang/String;)V", at = @At("RETURN"), cancellable = true)
    protected void injectWriteMethod(CallbackInfo ci) {
        AnvilScreenHandler handler = (AnvilScreenHandler)(Object)this; // WHY DOES THIS FUCKING WORK?????
        ItemStack input0 = handler.input.getStack(0);
        ItemStack input1 = handler.input.getStack(1);


        if (input0.isOf(ModItems.HYPERION_SWORD) || input0.isOf(ModItems.ASTRAEA_SWORD)
                || input0.isOf(ModItems.VALKYRIE_SWORD) || input0.isOf(ModItems.SCYLLA_SWORD))
        {
            WitherScrollsData currentItem = input0.get(WitherScrollsData.COMPONENT_TYPE);
            if (currentItem == null)
            {
                input0.set(WitherScrollsData.COMPONENT_TYPE, new WitherScrollsData((byte) 0));
                currentItem = input0.get(E7SpellsCommon.WITHER_SCROLLS_DATA);
            }
            boolean hasShield = (currentItem.state() & 0b00000001) != 0;
            boolean hasImplosion = (currentItem.state() & 0b00000010) != 0;
            boolean hasWarp = (currentItem.state() & 0b00000100) != 0;

            if (input1.getItem() == ModItems.WITHER_SHIELD_SCROLL && !hasShield)
            {
                ItemStack copy = input0.copy();
                copy.set(E7SpellsCommon.WITHER_SCROLLS_DATA, new WitherScrollsData((byte) (currentItem.state() | 0b00000001)));
                handler.output.setStack(0, copy);
                handler.levelCost.set(1);
                ci.cancel();
                return;
            }
            else if (input1.getItem() == ModItems.IMPLOSION_SCROLL && !hasImplosion)
            {
                ItemStack copy = input0.copy();
                copy.set(E7SpellsCommon.WITHER_SCROLLS_DATA, new WitherScrollsData((byte) (currentItem.state() | 0b00000010)));
                handler.output.setStack(0, copy);
                handler.levelCost.set(1);
                ci.cancel();
                return;
            }
            else if (input1.getItem() == ModItems.SHADOW_WARP_SCROLL && !hasWarp)
            {
                ItemStack copy = input0.copy();
                copy.set(E7SpellsCommon.WITHER_SCROLLS_DATA, new WitherScrollsData((byte) (currentItem.state() | 0b00000100)));
                handler.output.setStack(0, copy);
                handler.levelCost.set(1);
                ci.cancel();
                return;
            }

        }

    }
}
