package com.e7.spells.item.tools;

import com.e7.spells.E7SpellsCommon;
import com.e7.spells.networking.ClientPacketManager;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.networking.payloads.AoteParticleAnimationPacket;
import com.e7.spells.networking.payloads.UseAotePacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class AspectOfTheEnd extends WeaponItem
{
    public static final ToolMaterial MATERIAL = ModToolMaterials.END;
    public static final int ATTACK_DAMAGE = 2;
    public static final float ATTACK_SPEED = -2.4f;
    public static final int TELEPORT_DISTANCE = 12;
    public AspectOfTheEnd()
    {
        super(MATERIAL, new Settings().attributeModifiers(createAttributeModifiers(MATERIAL, ATTACK_DAMAGE, ATTACK_SPEED)));
    }


    public static void doTeleport(ServerPlayerEntity user, Vec3d pos)
    {
        user.requestTeleport(pos.getX(), pos.getY(), pos.getZ());
        user.fallDistance = 0;
        for (PlayerEntity player : PlayerLookup.tracking(user))
        {
            ServerPacketManager.sendPacketToClient(user, new AoteParticleAnimationPacket(pos));
        }

        user.getWorld().playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                pos.getX(), pos.getY(), pos.getZ(), // The position of where the sound will come from
                SoundEvents.ENTITY_ENDERMAN_TELEPORT, // The sound that will play
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                .8f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1.2f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
    }

    public static void doParticleAnimation(Vec3d pos)
    {
        Random r = E7SpellsCommon.random;
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
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type)
    {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§6Ability: Instant Transmission §e§lRIGHT CLICK"));
        tooltip.add(Text.literal("§7Teleport ahead of you by §e%d blocks".formatted(TELEPORT_DISTANCE)));
        tooltip.add(Text.literal(""));
        super.appendTooltip(stack, context, tooltip, type);
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
                beforeRound = hit.getPos().add(direction.multiply(-.5));
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

        ClientPacketManager.sendPacketToServer(new UseAotePacket(pos));

        return super.use(world, user, hand);
    }
}
