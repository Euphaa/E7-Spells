package com.e7.spells;

import com.e7.spells.util.Scheduler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class E7SpellsClient implements ClientModInitializer
{
    public Scheduler scheduler = new Scheduler();

    @Override
    public void onInitializeClient()
    {
        /* register key bindings */
        KeyBindings.registerKeys();
    }

    public static PlayerEntity getPlayer()
    {

        return MinecraftClient.getInstance().player;
    }
}
