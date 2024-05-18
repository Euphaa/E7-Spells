package com.e7.spells.item.tools.zombie_sword;

import com.e7.spells.E7Spells;
import com.e7.spells.networking.E7Packets;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
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

public class ZombieSwordItem extends SwordItem
{
    private static final int MAX_CHARGES = 5;
    private static final int HEAL_AMOUNT = 4;
    private static final int ALLY_HEAL_AMOUNT = 2;
    private static final int HEAL_RANGE = 10;


    public ZombieSwordItem()
    {
        super(ZombieToolMaterial.INSTANCE, 2, -2.4f, new Item.Settings());
    }


    public static int addCharge(ServerPlayerEntity player) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int charges = nbt.getInt("zombie_sword_charges");
        if(charges + 1 >= MAX_CHARGES) {
            charges = MAX_CHARGES;
        } else {
            charges += 1;
        }

        nbt.putInt("zombie_sword_charges", charges);
        syncChargesNbtWithPlayer(player);
        // sync the data
        return charges;
    }


    public static void syncChargesNbtWithPlayer(ServerPlayerEntity player)
    {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(((IEntityDataSaver) player).getPersistentData().getInt("zombie_sword_charges"));
        ServerPacketManager.sendPacketToClient(player, E7Packets.SYNC_ZOMBIE_SWORD_CHARGES, buf);
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


    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§6Ability: Instant Heal §e§lRIGHT CLICK"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§7Heal yourself for §c%d ❤ §7and".formatted(HEAL_AMOUNT)));
        tooltip.add(Text.literal("§7others within §e%d blocks §7for §c%d ❤".formatted(HEAL_RANGE, ALLY_HEAL_AMOUNT)));

        Entity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int charges = nbt.getInt("zombie_sword_charges");
        float ratio = charges/((float)MAX_CHARGES);
        String color;
        if (ratio < .25) color = "§c";
        else if (ratio < .75) color = "§e";
        else color = "§a";
        tooltip.add(Text.literal("%s%d§8/§a%d§8 charges left".formatted(color, charges, MAX_CHARGES)));
        tooltip.add(Text.literal(""));
    }


    public static void doParticleAnimation(Vec3d pos)
    {
        Random r = E7Spells.random;
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

        NbtCompound nbt = ((IEntityDataSaver) user).getPersistentData();
        int charges = nbt.getInt("zombie_sword_charges");
        if (--charges >= 0)
        {
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

            nbt.putInt("zombie_sword_charges", charges);
            ServerPacketManager.sendPacketToClient((ServerPlayerEntity) user, E7Packets.ZOMBIE_SWORD_PARTICLE_ANIMATION, PacketByteBufs.empty());
        }
        syncChargesNbtWithPlayer((ServerPlayerEntity) user);

        return super.use(world, user, hand);
    }
}
