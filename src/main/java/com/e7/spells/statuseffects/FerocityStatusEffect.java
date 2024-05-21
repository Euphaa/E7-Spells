package com.e7.spells.statuseffects;

import com.e7.spells.E7SpellsCommon;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.networking.payloads.FerocityParticleAnimationPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.swing.*;
import java.util.Map;
import java.util.Random;

public class FerocityStatusEffect extends StatusEffect
{

    private static final float FEROCITY_DAMAGE_MULTIPLIER = 1f;
    private static final float FEROCITY_KNOCKBACK_MULTIPLIER = .3f;
    private static final int FEROCITY_HIT_DELAY = 7;
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

    // This method is called when it applies the status effect. implement custom functionality here.
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        return super.applyUpdateEffect(entity, amplifier);
    }



    public static void registerEffect()
    {
        TagKey<StatusEffect> effect = TagKey.of(RegistryKeys.STATUS_EFFECT, new Identifier(E7SpellsCommon.MODID, "ferocity"));
        AttackEntityCallback.EVENT.register((attacker, world, hand, victim, hitResult) -> {
            System.out.println("event fired");
            if (victim instanceof EnderDragonPart) victim = ((EnderDragonPart) victim).owner;
            if (world.isClient()) return ActionResult.PASS;
            StatusEffectInstance instance = null;
            for (StatusEffectInstance effectInstance : attacker.getStatusEffects())
            {
                System.out.println(effectInstance.getEffectType());
                if (effectInstance.getEffectType().value() == ModStatusEffects.FEROCITY_EFFECT)
                {
                    instance = effectInstance;
                }
            }
            if (instance == null) return ActionResult.PASS;
//            if (!attacker.hasStatusEffect(ModStatusEffects.FEROCITY)) return ActionResult.PASS;
//            if (!attacker.getActiveStatusEffects().keySet().toString().contains("FerocityStatusEffect")) return ActionResult.PASS;
            System.out.println("has status effect");
            if (!(victim instanceof LivingEntity)) return ActionResult.PASS;
            System.out.println("is living entity");
            procFerocity(attacker, world, (LivingEntity) victim, instance);

            return ActionResult.PASS; // Continue with normal attack handling
        });
    }

    private static void procFerocity(PlayerEntity attacker, World world, LivingEntity victim, StatusEffectInstance instance)
    {
        float damage = (float) attacker.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue();
        int ferocityLevel = instance.getAmplifier() + 1;
        int guarenteedProcs = Math.floorDiv(ferocityLevel, 2);
        if (ferocityLevel % 2 == 1 && new Random().nextInt(2) == 1) guarenteedProcs++;
        Vec3d direction = attacker.getPos().subtract(victim.getPos()).normalize();
        float knockback = (float) Math.log(ferocityLevel + 1.5) / 5;

        for (int i = 1; i <= guarenteedProcs; i++)
        {
            E7SpellsCommon.getScheduler().addTask(i * FEROCITY_HIT_DELAY, (server) -> {
                doFerocitySwipe(attacker, world, victim, direction, damage, knockback);
            });
        }
    }

    private static void doFerocitySwipe(PlayerEntity attacker, World world, LivingEntity victim, Vec3d direction, float damage, float knockback)
    {
        if (!victim.isAlive()) return;
        System.out.println("swipe");
        DamageSource source = attacker.getDamageSources().playerAttack(attacker);
        victim.applyDamage(source, damage * FEROCITY_DAMAGE_MULTIPLIER);
        victim.getWorld().sendEntityDamage(victim, source);
        victim.takeKnockback(knockback * FEROCITY_KNOCKBACK_MULTIPLIER, direction.getX(), direction.getZ());

        CustomPayload p = new FerocityParticleAnimationPacket(victim.getPos());
        for (ServerPlayerEntity player : PlayerLookup.tracking(victim))
        {
            ServerPacketManager.sendPacketToClient(player, p);
        }

        world.playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                BlockPos.ofFloored(victim.getPos()), // The position of where the sound will come from
                SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, // The sound that will play, in this case, the sound the anvil plays when it lands.
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                1.5f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                .8f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
        world.playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                BlockPos.ofFloored(victim.getPos()), // The position of where the sound will come from
                SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, // The sound that will play, in this case, the sound the anvil plays when it lands.
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                1.5f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1.2f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
    }

    @Environment(EnvType.CLIENT)
    public static void createFerocityParticles(Vec3d pos)
    {
        MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.SWEEP_ATTACK,
                pos.getX(), pos.getY(), pos.getZ(),
                0, 0, 0
        );
    }
}
