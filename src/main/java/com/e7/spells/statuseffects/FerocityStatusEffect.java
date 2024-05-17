package com.e7.spells.statuseffects;

import com.e7.spells.E7Spells;
import com.e7.spells.ModDamageTypes;
import com.e7.spells.networking.E7Packets;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.util.Scheduler;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class FerocityStatusEffect extends StatusEffect
{

    private static final float FEROCITY_DAMAGE_MULTIPLIER = 1f;
    private static final float FEROCITY_KNOCKBACK_MULTIPLIER = .8f;
    private static final int FEROCITY_HIT_DELAY = 10;
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
        AttackEntityCallback.EVENT.register((attacker, world, hand, victim, hitResult) -> {

            if (victim instanceof EnderDragonPart) victim = ((EnderDragonPart) victim).owner;
            if (!doesFerocityProc(attacker, world, victim)) return ActionResult.PASS;

            procFerocity(attacker, world, victim);

            return ActionResult.PASS; // Continue with normal attack handling
        });
    }

    private static boolean doesFerocityProc(PlayerEntity attacker, World world, Entity victim)
    {
        if (world.isClient()) return false;
        if (!attacker.hasStatusEffect(E7Spells.FEROCITY)) return false;
        if (!(victim instanceof LivingEntity)) return false;
        if (((LivingEntity) victim).hurtTime > 0) return false;
        return true;
    }

    private static boolean doesFerocityProc(PlayerEntity attacker, World world, EnderDragonEntity victim)
    {
        if (world.isClient()) return false;
        if (!attacker.hasStatusEffect(E7Spells.FEROCITY)) return false;
        if (victim.hurtTime > 0) return false;
        return true;
    }

    private static void procFerocity(PlayerEntity attacker, World world, Entity victim)
    {
        float damage = (float) attacker.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue();
        int ferocityLevel = attacker.getStatusEffect(E7Spells.FEROCITY).getAmplifier() + 1;
        int guarenteedProcs = Math.floorDiv(ferocityLevel, 2);
        if (ferocityLevel % 2 == 1 && new Random().nextInt(2) == 1) guarenteedProcs++;
        Vec3d direction = attacker.getPos().subtract(victim.getPos()).normalize();
        float knockback = (float) Math.log(ferocityLevel + 1.5) / 5;

        for (int i = 1; i <= guarenteedProcs; i++)
        {
            Scheduler.addTask(i * FEROCITY_HIT_DELAY + 1, (server) -> {
                doFerocitySwipe(attacker, world, victim, direction, damage, knockback);
            });
        }


    }

    private static void doFerocitySwipe(PlayerEntity attacker, World world, Entity victim, Vec3d direction, float damage, float knockback)
    {
        if (!victim.isAlive()) return;
//        victim.damage(ModDamageTypes.of(world, ModDamageTypes.FEROCITY_DAMAGE_TYPE), damage * (FEROCITY_DAMAGE_MULTIPLIER + 1));
//        victim.damage(, damage * (FEROCITY_DAMAGE_MULTIPLIER + 1));
        victim.damage(attacker.getDamageSources().playerAttack(attacker), damage * FEROCITY_DAMAGE_MULTIPLIER);
        ((LivingEntity) victim).takeKnockback(knockback * FEROCITY_KNOCKBACK_MULTIPLIER, direction.getX(), direction.getZ());

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(victim.getX());
        buf.writeDouble(victim.getY());
        buf.writeDouble(victim.getZ());
        for (ServerPlayerEntity player : PlayerLookup.tracking(victim))
        {
            ServerPacketManager.sendPacketToClient(player, E7Packets.FEROCITY_PARTICLE_ANIMATION, buf);
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


    public static void createFerocityParticles(MinecraftClient client, Vec3d pos)
    {
        client.particleManager.addParticle(ParticleTypes.SWEEP_ATTACK,
                pos.getX(), pos.getY(), pos.getZ(),
                0, 0, 0
        );
    }

//    @Override
//    public void onScheduleEnd(World world, BlockPos pos, int scheduleId, NbtCompound additionalData)
//    {
//
//    }
}
