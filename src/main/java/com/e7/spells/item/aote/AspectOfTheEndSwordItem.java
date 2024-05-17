package com.e7.spells.item.aote;

import com.e7.spells.item.zombie_tools.ZombieToolMaterial;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AspectOfTheEndSwordItem extends SwordItem
{
    public static final int TELEPORT_DISTANCE = 12;
    public AspectOfTheEndSwordItem()
    {
        super(AspectOfTheEndMaterial.INSTANCE, 2, -2f, new Item.Settings());
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
    {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§6Ability: Instand Transmission §e§lRIGHT CLICK"));
        tooltip.add(Text.literal("§7Teleport ahead of you by §e%d blocks".formatted(TELEPORT_DISTANCE)));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if (world.isClient()) return super.use(world, user, hand);
        Vec3d startPos = user.getPos();
        double yaw = user.getYaw() * (Math.PI / 180.0);
        double pitch = -user.getPitch() * (Math.PI / 180.0);
        Vec3d direction = new Vec3d(
                -Math.sin(yaw),
                Math.sin(pitch),
                Math.sin(pitch)
        ).normalize();
        Vec3d endPos = startPos.add(direction.multiply(TELEPORT_DISTANCE));
        user.requestTeleport(endPos.getX(), endPos.getY(), endPos.getZ());

        return super.use(world, user, hand);
    }
}
