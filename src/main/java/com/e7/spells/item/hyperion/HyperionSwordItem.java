package com.e7.spells.item.hyperion;

import com.e7.spells.E7Spells;
import com.e7.spells.ModDamageTypes;
import com.e7.spells.networking.ClientPacketManager;
import com.e7.spells.networking.E7Packets;
import com.e7.spells.networking.ServerPacketManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class HyperionSwordItem extends SwordItem
{
    public static final int TELEPORT_DISTANCE = 10;
    public static final int IMPLOSION_RANGE = 7;
    public static final int IMPLOSION_DAMAGE_MULTIPLIER = 16;
    private static final int HEALING_AMOUNT = 2;

    public HyperionSwordItem()
    {
        super(HyperionMaterial.INSTANCE, 2, -2f, new Settings());
    }


    public static void doWitherImpact(ServerPlayerEntity user, Vec3d pos)
    {
        user.requestTeleport(pos.getX(), pos.getY(), pos.getZ());
        BlockPos center = user.getBlockPos();
        Box BoundingBox = new Box(
                center.getX()- IMPLOSION_RANGE,
                center.getY()- IMPLOSION_RANGE,
                center.getZ()- IMPLOSION_RANGE,
                center.getX()+ IMPLOSION_RANGE,
                center.getY()+ IMPLOSION_RANGE,
                center.getZ()+ IMPLOSION_RANGE
        );
        DamageSource dmgSource = ModDamageTypes.of(user.getWorld(), ModDamageTypes.MAGIC_DAMAGE_TYPE);
        for (LivingEntity victim : user.getWorld().getEntitiesByClass(LivingEntity.class, BoundingBox, entity -> !(entity instanceof PlayerEntity)))
        {
//            victim.damage(user.getDamageSources().playerAttack(user), IMPLOSION_DAMAGE_MULTIPLIER);
            victim.damage(dmgSource, IMPLOSION_DAMAGE_MULTIPLIER);
        }
        PacketByteBuf buf = PacketByteBufs.create();
        E7Packets.packVec3d(buf, new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
        for (ServerPlayerEntity player : PlayerLookup.tracking(user))
        {
            ServerPacketManager.sendPacketToClient(player, E7Packets.HYPERION_PARTICLE_ANIMATION, buf);
        }
        ServerPacketManager.sendPacketToClient(user, E7Packets.HYPERION_PARTICLE_ANIMATION, buf);
        // cosmetic stuff
        user.getWorld().playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                pos.getX(), pos.getY(), pos.getZ(), // The position of where the sound will come from
                SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, // The sound that will play
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                .8f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1.2f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
        user.getWorld().playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                pos.getX(), pos.getY(), pos.getZ(), // The position of where the sound will come from
                SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, // The sound that will play
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                .8f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1.2f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
    }

    public static void doParticleAnimation(Vec3d pos)
    {
        Random r = E7Spells.random;
        int s = 1; // particle separation
        int n = 15; // number of particles
        pos = pos.add(0, .5, 0);

        for (int i = 0; i < n; i++)
        {
            MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.PORTAL,
                    r.nextFloat(-1, 1)*s + pos.getX(),
                    r.nextFloat(-1, 1)*s*1.5 + pos.getY(),
                    r.nextFloat(-1, 1)*s + pos.getZ(),
                    0, 0, 0
            );
        }
        MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.EXPLOSION_EMITTER,
                r.nextFloat(-1, 1)*s + pos.getX(),
                r.nextFloat(-1, 1)*s*1.5 + pos.getY(),
                r.nextFloat(-1, 1)*s + pos.getZ(),
                0, 0, 0
        );
    }


    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
    {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§6Item Ability: Wither Impact §e§lRIGHT CLICK"));
        tooltip.add(Text.literal("§7Teleports §e%d blocks §7ahead of you. Then implode".formatted(TELEPORT_DISTANCE)));
        tooltip.add(Text.literal("§7dealing §412 damage to nearby enemies.".formatted(TELEPORT_DISTANCE)));
        tooltip.add(Text.literal("§7tAlso applies the wither shield "));
        tooltip.add(Text.literal("§7scroll ability granting §c+%d ❤".formatted(HEALING_AMOUNT)));
        tooltip.add(Text.literal(""));
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if (!world.isClient()) return super.use(world, user, hand);
        double maxReach = TELEPORT_DISTANCE; //The farthest target the cameraEntity can detect
        float tickDelta = 1.0F; //Used for tracking animation progress; no tracking is 1.0F
        boolean includeFluids = false; //Whether to detect fluids as blocks
        HitResult hit = user.raycast(maxReach, tickDelta, includeFluids);
        Vec3d pos = null;
        Vec3d beforeRound;

        switch (hit.getType())
        {
            case MISS, ENTITY ->
            {
                beforeRound = hit.getPos();
                pos = new Vec3d(
                        Math.round(beforeRound.getX() - .5) + .5,
                        Math.floor(beforeRound.getY()),
                        Math.round(beforeRound.getZ() - .5) + .5
                );
            }
            case BLOCK ->
            {
                double yaw = user.getYaw() * (Math.PI / 180.0);
                double pitch = -user.getPitch() * (Math.PI / 180.0);

                Vec3d direction = new Vec3d(
                        -Math.sin(yaw)*Math.cos(pitch),
                        Math.sin(pitch),
                        Math.cos(yaw)*Math.cos(pitch)
                ).normalize();
                beforeRound = hit.getPos().add(direction.multiply(-.2));
                pos = new Vec3d(
                        Math.round(beforeRound.getX() - .5) + .5,
                        Math.floor(beforeRound.getY()),
                        Math.round(beforeRound.getZ() - .5) + .5
                );
            }
        }

        if (pos == null) return super.use(world, user, hand);
        // i = number of attempts to find another block above current one
        for (int i = 0; i < 2; i++)
        {
            BlockState state = world.getBlockState(new BlockPos(((int) Math.floor(pos.getX())), ((int) pos.getY()), ((int) Math.floor(pos.getZ()))));
            // if the block we are trying to teleport to is solid, then go one above
            if (!state.isAir() && !state.isLiquid())
            {
                pos = pos.add(0, 1, 0);
            }
            else break;
        }
        PacketByteBuf buf = PacketByteBufs.create();
        E7Packets.packVec3d(buf, pos);
        ClientPacketManager.sendPacketToServer(E7Packets.USE_HYPERION, buf);

        return super.use(world, user, hand);
    }
}
