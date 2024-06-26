package com.e7.spells.item.tools;

import com.e7.spells.E7SpellsCommon;
import com.e7.spells.item.WeaponItem;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.networking.payloads.ZombieSwordParticleAnimationPacket;
import com.e7.spells.util.CCAComponents;
import com.e7.spells.util.IEntityDataSaver;
import com.e7.spells.util.PlayerNbtComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ZombieSword extends WeaponItem
{
    public static final ToolMaterial MATERIAL = ModToolMaterials.ZOMBIE;
    public static final int ATTACK_DAMAGE = 2;
    public static final float ATTACK_SPEED = -2.4f;
    private static final int MAX_CHARGES = 5;
    private static final int MANA_COST = 70;
    private static final int HEAL_AMOUNT = 4;
    private static final int ALLY_HEAL_AMOUNT = 2;
    private static final int HEAL_RANGE = 10;

    public ZombieSword()
    {
        super(MATERIAL, new Settings().attributeModifiers(WeaponItem.createAttributeModifiers(MATERIAL, ATTACK_DAMAGE, ATTACK_SPEED)));
    }


    public static int addCharge(ServerPlayerEntity player) {
        int charges = CCAComponents.PLAYER_NBT.get(player).getZombie_sword_charges();
        if (charges + 1 >= MAX_CHARGES)
        {
            CCAComponents.PLAYER_NBT.get(player).setZombie_sword_charges(MAX_CHARGES);
        }
        else
        {
            CCAComponents.PLAYER_NBT.get(player).incrementZombie_sword_charges(1);
        }
        return charges;
    }



    public static void addChargeToEveryone(MinecraftServer server)
    {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList())
        {
            addCharge(player);
        }
    }

//    @Override
//    public ItemStack getDefaultStack()
//    {
//        ItemStack defaultStack = super.getDefaultStack();
//        NbtCompound nbt = defaultStack.getNbt();
//        nbt.putBoolean("charging", false);
//        nbt.putInt("charges", MAX_CHARGES);
//        return defaultStack;
//    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type)
    {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§6Ability: Instant Heal §e§lRIGHT CLICK"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§7Heal yourself for §c%d ❤ §7and".formatted(HEAL_AMOUNT)));
        tooltip.add(Text.literal("§7others within §e%d blocks §7for §c%d ❤".formatted(HEAL_RANGE, ALLY_HEAL_AMOUNT)));
        tooltip.add(Text.literal("§8Mana Cost: §3%d".formatted(MANA_COST)));


        Entity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int charges = CCAComponents.PLAYER_NBT.get(player).getZombie_sword_charges();
        float ratio = charges/((float)MAX_CHARGES);
        String color;
        if (ratio < .25) color = "§c";
        else if (ratio < .75) color = "§e";
        else color = "§a";
        tooltip.add(Text.literal("%s%d§8/§a%d§8 charges left".formatted(color, charges, MAX_CHARGES)));
        tooltip.add(Text.literal(""));
        super.appendTooltip(stack, context, tooltip, type);
    }


    @Environment(EnvType.CLIENT)
    public static void doParticleAnimation(Vec3d pos)
    {
        Random r = E7SpellsCommon.random;
        int s = 1; // particle separation
        int n = 15; // number of particles
        pos = pos.add(0, .5, 0);

        for (int i = 0; i < n; i++)
        {
            MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.HEART,
                    r.nextFloat(-1, 1)*s + pos.getX(),
                    r.nextFloat(-1, 1)*s*1.5 + pos.getY(),
                    r.nextFloat(-1, 1)*s + pos.getZ(),
                    0, 0, 0
            );
        }
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if (world.isClient()) return super.use(world, user, hand);

        PlayerNbtComponent userData = CCAComponents.PLAYER_NBT.get(user);
        if (userData.getZombie_sword_charges() > 0 && userData.getMana() >= MANA_COST)
        {
            userData.incrementMana(-MANA_COST);
            userData.incrementZombie_sword_charges(-1);

            user.heal(HEAL_AMOUNT);

            BlockPos center = user.getBlockPos();
            Box BoundingBox = new Box(
                    center.getX()-HEAL_RANGE,
                    center.getY()-HEAL_RANGE,
                    center.getZ()-HEAL_RANGE,
                    center.getX()+HEAL_RANGE,
                    center.getY()+HEAL_RANGE,
                    center.getZ()+HEAL_RANGE
            );
            for (PlayerEntity player : world.getEntitiesByClass(PlayerEntity.class, BoundingBox, entity -> true))
            {
                player.heal(ALLY_HEAL_AMOUNT);
            }

            ServerPacketManager.sendPacketToClient((ServerPlayerEntity) user, new ZombieSwordParticleAnimationPacket(user.getPos()));
        }


        return super.use(world, user, hand);
    }
}
