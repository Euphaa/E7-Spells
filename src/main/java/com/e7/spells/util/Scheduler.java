package com.e7.spells.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Scheduler
{
    private HashMap<Integer, ArrayList<Task>> scheduledTasks = new HashMap<>();
    private HashMap<Integer, ArrayList<Task>> regularTasks = new HashMap<>();
    private ArrayList<Integer> soonestTicks = new ArrayList<>();
    private int currentTick = 0;

    public void tick(MinecraftServer server)
    {
        currentTick++;
        if (currentTick == 10_000_000)
        {
            decrementEverything();
        }

        // scheduled tasks
        int soonestTick = (soonestTicks.size() > 0) ? soonestTicks.get(0) : -1;
        if (currentTick == soonestTick)
        {
            for (Task task : scheduledTasks.get(currentTick))
            {
                task.doTask(server);
            }
            scheduledTasks.remove(soonestTick);
            soonestTicks.remove(0);
        }

        // regular tasks
        for (int x : regularTasks.keySet())
        {
            if (currentTick % x == 0)
            {
                for (Task task : regularTasks.get(x))
                {
                    task.doTask(server);
                }
            }
        }
    }

    public void addTask(Integer delay, Task task)
    {
        int time = currentTick + delay;
        if (!scheduledTasks.containsKey(time))
        {
            scheduledTasks.put(time, new ArrayList<>());
        }
        scheduledTasks.get(time).add(task);

        if (!soonestTicks.contains(time)) soonestTicks.add(time);
        Collections.sort(soonestTicks);
    }

    private void decrementEverything()
    {
        HashMap<Integer, ArrayList<Task>> newMap = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Task>> entry : scheduledTasks.entrySet())
        {
            newMap.put(entry.getKey() - 10_000_000, entry.getValue());
        }
        scheduledTasks = newMap;

        ArrayList<Integer> newSoonestTicks = new ArrayList<>();
        for (Integer entry : soonestTicks)
        {
            newSoonestTicks.add(entry - 10_000_000);
        }

        currentTick -= 10_000_000;
    }

    public void registerTicker()
    {
        ServerTickEvents.START_SERVER_TICK.register((server) -> tick(server));
    }

    public void registerRegularEvent(int interval, Task task)
    {
        if (!regularTasks.containsKey(interval))
        {
            regularTasks.put(interval, new ArrayList<>());
        }
        regularTasks.get(interval).add(task);
    }
}
