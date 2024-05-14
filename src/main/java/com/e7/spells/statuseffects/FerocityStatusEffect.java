package com.e7.spells.statuseffects;

import com.e7.spells.E7Spells;
import com.e7.spells.ModDamageTypes;
import com.e7.spells.networking.E7Packets;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.util.Scheduler;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;

import java.sql.Array;

public class FerocityStatusEffect extends StatusEffect
{

    private static final float FEROCITY_DAMAGE_MULTIPLIER = 1f;
    private static final float FEROCITY_KNOCKBACK_MULTIPLIER = .8f;
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

            if (world.isClient()) return ActionResult.PASS;
            if (!attacker.hasStatusEffect(E7Spells.FEROCITY)) return ActionResult.PASS;
            if (((LivingEntity) victim).hurtTime > 0) return ActionResult.PASS;

            float damage = (float) attacker.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue();
            Scheduler.addTask(10, () -> {
                victim.damage(ModDamageTypes.of(world, ModDamageTypes.CUSTOM_DAMAGE_TYPE), damage * FEROCITY_DAMAGE_MULTIPLIER);
                Vec3d direction = victim.getPos().subtract(attacker.getPos()).normalize();
                ((LivingEntity) victim).takeKnockback(FEROCITY_KNOCKBACK_MULTIPLIER, -direction.getX(), -direction.getZ());
                E7Spells.getPlayer().sendMessage(Text.literal("ferocity proc for " + damage * FEROCITY_DAMAGE_MULTIPLIER + " damage"));

                victim.handleAttack(attacker);
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeDouble(victim.getX());
                buf.writeDouble(victim.getY());
                buf.writeDouble(victim.getZ());
                for (ServerPlayerEntity player : PlayerLookup.tracking(victim))
                {
                    ServerPacketManager.sendPacketToClient(player, E7Packets.FEROCITY_PARTICLE_ANIMATION, buf);
                }

                // need to send entity hurt packet
//                for (ServerPlayerEntity player : PlayerLookup.tracking(victim)) {
//                    ServerPlayNetworking.send(player, , buf);
//                }
//                world.addParticle(
//                        ,
//                        victim.getX(),
//                        victim.getY(),
//                        victim.getZ(),
//                        0,
//                        0,
//                        0
//                );

                LivingEntity x = (LivingEntity) victim;
                attacker.sendMessage(Text.literal("before: " + x.hurtTime));
                x.hurtTime = 9;
                attacker.sendMessage(Text.literal("after: " + x.hurtTime));

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
            });

            return ActionResult.PASS; // Continue with normal attack handling
        });
    }

    public static void procFerocity(Entity player, World world, Entity entity, float damage)
    {

//        entity.damage(, damage);
    }

    public static void createFerocityParticles(World world, Entity entity)
    {
//        world.addParticle(
//                ,
//                entity.getX(),
//                entity.getY(),
//                entity.getZ(),
//                0,
//                0,
//                0
//        );
    }

//    @Override
//    public void onScheduleEnd(World world, BlockPos pos, int scheduleId, NbtCompound additionalData)
//    {
//
//    }
}
