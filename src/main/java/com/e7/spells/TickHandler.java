package com.e7.spells;

import com.e7.spells.util.Task;
import com.e7.spells.util.TickMap;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.text.Text;

public class TickHandler
{
    private static TickMap map = new TickMap();

    public static void registerTicker()
    {
        ServerTickEvents.START_SERVER_TICK.register((thing) -> onServerTickStart());
    }

    private static void onServerTickStart()
    {
        map.tick();
    }

    public static void addTask(Integer delay, Task task)
    {
        map.addTask(delay, task);
    }
}
