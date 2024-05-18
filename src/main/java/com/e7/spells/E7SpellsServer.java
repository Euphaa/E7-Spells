package com.e7.spells;

import com.e7.spells.util.Scheduler;
import net.fabricmc.api.DedicatedServerModInitializer;

public class E7SpellsServer implements DedicatedServerModInitializer
{
    private static Scheduler scheduler = new Scheduler();
    @Override
    public void onInitializeServer()
    {
        /* register event handlers */
        scheduler.registerTicker();

    }

    public static Scheduler getScheduler()
    {
        return scheduler;
    }
}
