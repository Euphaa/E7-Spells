package com.e7.spells.statuseffects;

import com.e7.spells.E7Spells;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.tick.SimpleTickScheduler;

public class FerocityStatusEffect extends StatusEffect
{

    public FerocityStatusEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0x880808);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {

    }

    public static void registerEffect()
    {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

//
//            // Check if the attacking entity is a player and has the custom status effect
//            if (!player.hasStatusEffect(E7Spells.FEROCITY)) return ActionResult.PASS;
//
////            StatusEffectInstance effectInstance = player.getStatusEffect(E7Spells.FEROCITY);
//            double damage = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue();
//
//
//
//            // Print some information about the attack
//            System.out.println("Attacker: " + player.getName().getString());
//            System.out.println("Victim: " + entity.getName().getString());
//            System.out.println("Original Damage: " + damage);
//
////            Scheduler.Builder(, world).schedule(10);
////                entity.damage();
            return ActionResult.PASS; // Continue with normal attack handling
        });
    }

    public static void procFerocity(Entity player, World world, Entity entity, float damage)
    {

//        entity.damage(, damage);
    }

//    @Override
//    public void onScheduleEnd(World world, BlockPos pos, int scheduleId, NbtCompound additionalData)
//    {
//
//    }
}
