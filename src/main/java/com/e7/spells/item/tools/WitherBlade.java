package com.e7.spells.item.tools;

import com.e7.spells.E7SpellsCommon;
import com.e7.spells.ModDamageTypes;
import com.e7.spells.item.ItemModifier;
import com.e7.spells.item.WeaponItem;
import com.e7.spells.networking.ClientPacketManager;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.networking.payloads.AoteParticleAnimationPacket;
import com.e7.spells.networking.payloads.HyperionParticleAnimationPacket;
import com.e7.spells.networking.payloads.UseHyperionPacket;
import com.e7.spells.util.CCAComponents;
import com.e7.spells.util.WitherScrollsData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.network.packet.CustomPayload;
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

public class WitherBlade extends WeaponItem
{
    public static final ToolMaterial MATERIAL = ModToolMaterials.NECRON;
    public static final int MANA_COST = 150;
    public static final int TELEPORT_DISTANCE = 10;
    public static final int IMPLOSION_RANGE = 7;
    public static final int IMPLOSION_DAMAGE_MULTIPLIER = 16;
    private static final int HEALING_AMOUNT = 8;

    public WitherBlade(ToolMaterial material, int damage, float attackSpeed, ItemModifier... modifiers)
    {
        super(material, damage, attackSpeed, modifiers);
    }

    public static void doWitherImpact(ServerPlayerEntity user, Vec3d pos)
    {
        WitherScrollsData sword = user.getMainHandStack().getComponents().get(WitherScrollsData.COMPONENT_TYPE);
        if (sword == null) return;
        boolean hasShield = (sword.state() & 0b00000001) != 0;
        boolean hasImplosion = (sword.state() & 0b00000010) != 0;
        boolean hasWarp = (sword.state() & 0b00000100) != 0;

        if (!hasShield && !hasImplosion && !hasWarp) return;
        if (!CCAComponents.PLAYER_NBT.get(user).subtractAbilityCost(MANA_COST)) return;


//        ItemStack hyperion = user.getMainHandStack();

        if (hasWarp)
        {
            doShadowWarp(user, pos);
            if (hasImplosion) doImplosion(user, pos);
            if (hasShield) doWitherShield(user, pos);
        }
        else
        {
            if (hasImplosion) doImplosion(user, user.getPos());
            if (hasShield) doWitherShield(user, user.getPos());
        }

    }

    private static void doWitherShield(ServerPlayerEntity user, Vec3d pos)
    {
        if (CCAComponents.PLAYER_NBT.get(user).getWither_shield_cooldown() > 0) return;
        user.heal(HEALING_AMOUNT);
        user.getWorld().playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                pos.getX(), pos.getY(), pos.getZ(), // The position of where the sound will come from
                SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, // The sound that will play
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                .8f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1.2f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
        CCAComponents.PLAYER_NBT.get(user).setWither_shield_cooldown(100);
        CustomPayload p = new AoteParticleAnimationPacket(pos);
        for (ServerPlayerEntity player : PlayerLookup.tracking(user))
        {
            ServerPacketManager.sendPacketToClient(player, p);
        }
        ServerPacketManager.sendPacketToClient(user, p);
    }

    private static void doImplosion(ServerPlayerEntity user, Vec3d pos)
    {
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
            victim.applyDamage(dmgSource, IMPLOSION_DAMAGE_MULTIPLIER);
            victim.getWorld().sendEntityDamage(victim, dmgSource);
        }
        CustomPayload p = new HyperionParticleAnimationPacket(pos);
        for (ServerPlayerEntity player : PlayerLookup.tracking(user))
        {
            ServerPacketManager.sendPacketToClient(player, p);
        }
        ServerPacketManager.sendPacketToClient(user, p);
        // cosmetic stuff
        user.getWorld().playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                pos.getX(), pos.getY(), pos.getZ(), // The position of where the sound will come from
                SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, // The sound that will play
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                .8f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1.2f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
    }

    private static void doShadowWarp(ServerPlayerEntity user, Vec3d pos)
    {
        user.requestTeleport(pos.getX(), pos.getY(), pos.getZ());
        user.fallDistance = 0;
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
        MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.EXPLOSION_EMITTER,
                r.nextFloat(-1, 1)*s + pos.getX(),
                r.nextFloat(-1, 1)*s*1.5 + pos.getY(),
                r.nextFloat(-1, 1)*s + pos.getZ(),
                0, 0, 0
        );
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type)
    {
        WitherScrollsData sword = stack.getComponents().get(E7SpellsCommon.WITHER_SCROLLS_DATA);
        if (sword == null) return;

        boolean hasShield = (sword.state() & 0b00000001) != 0;
        boolean hasImplosion = (sword.state() & 0b00000010) != 0;
        boolean hasWarp = (sword.state() & 0b00000100) != 0;

        if (hasImplosion && hasShield && hasWarp)
        {
            tooltip.add(Text.literal(""));
            tooltip.add(Text.literal("§6Item Ability: Wither Impact §e§lRIGHT CLICK"));
            tooltip.add(Text.literal("§7Teleports §e%d blocks §7ahead of you. Then implode".formatted(TELEPORT_DISTANCE)));
            tooltip.add(Text.literal("§7dealing §4%d damage §7to nearby enemies.".formatted(IMPLOSION_DAMAGE_MULTIPLIER)));
            tooltip.add(Text.literal("§7Also applies the wither shield "));
            tooltip.add(Text.literal("§7scroll ability granting §c+%d ❤§7 (5s cooldown).".formatted(HEALING_AMOUNT)));
            tooltip.add(Text.literal("§8Mana Cost: §3%d".formatted(MANA_COST)));
            super.appendTooltip(stack, context, tooltip, type);
        }
        else
        {
            if (hasImplosion)
            {
                tooltip.add(Text.literal(""));
                tooltip.add(Text.literal("§6Item Ability: Implosion §e§lRIGHT CLICK"));
                tooltip.add(Text.literal("§7Deals §4%d damage §7to nearby enemies.".formatted(IMPLOSION_DAMAGE_MULTIPLIER)));
                tooltip.add(Text.literal("§8Mana Cost: §3%d".formatted(MANA_COST)));

            }
            if (hasShield)
            {
                tooltip.add(Text.literal(""));
                tooltip.add(Text.literal("§6Item Ability: Wither Shield §e§lRIGHT CLICK"));
                tooltip.add(Text.literal("§7Grants §c+%d ❤§7 (5s cooldown).".formatted(HEALING_AMOUNT)));
                tooltip.add(Text.literal("§8Mana Cost: §3%d".formatted(MANA_COST)));

            }
            if (hasWarp)
            {
                tooltip.add(Text.literal(""));
                tooltip.add(Text.literal("§6Item Ability: Shadow Warp §e§lRIGHT CLICK"));
                tooltip.add(Text.literal("§7Teleports §e%d blocks §7ahead of you.".formatted(TELEPORT_DISTANCE)));
                tooltip.add(Text.literal("§8Mana Cost: §3%d".formatted(MANA_COST)));
            }
        }
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

        ClientPacketManager.sendPacketToServer(new UseHyperionPacket(pos));

        return super.use(world, user, hand);
    }
}
