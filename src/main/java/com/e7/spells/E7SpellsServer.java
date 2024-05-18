package com.e7.spells;

import com.e7.spells.item.ModItems;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.statuseffects.ModStatusEffects;
import com.e7.spells.util.Scheduler;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class E7SpellsServer implements DedicatedServerModInitializer
{
//    public static E7SpellsServer server;
    private static Scheduler scheduler = new Scheduler();
    @Override
    public void onInitializeServer()
    {
//        server = this;

        /* register event handlers */
        scheduler.registerTicker();

        /* register items */
        ModItems.registerModItemsForServer();
        ModPotions.registerPotions();

        /* register effects */
        ModStatusEffects.registerEffects();

        /* register commands */
        Commands.registerCommands();

        /* register event handlers */
        ServerPacketManager.registerPacketListeners();
    }

    public static Scheduler getScheduler()
    {
        return scheduler;
    }
}
