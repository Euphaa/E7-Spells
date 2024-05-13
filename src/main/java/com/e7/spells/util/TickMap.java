package com.e7.spells.util;

import com.e7.spells.E7Spells;
import net.minecraft.text.Text;

import java.util.*;

public class TickMap
{
//    private TreeMap<Integer, ArrayList<Task>> map = new TreeMap<>();
//    private int soonestEvent;
    private HashMap<Integer, ArrayList<Task>> map = new HashMap<>();
    private ArrayList<Integer> soonestTicks = new ArrayList<>();
    private int currentTick = 0;

    public void tick()
    {
        currentTick++;
        if (currentTick % 5 == 0) System.out.println(currentTick);
        if (currentTick == 10_000_000)
        {
            decrementEverything();
        }
        int soonestTick = (soonestTicks.size() > 0) ? soonestTicks.get(0) : -1;
        if (currentTick == soonestTick)
        {
            for (Task task : map.get(currentTick))
            {
                task.doTask();
            }
            map.remove(soonestTick);
            soonestTicks.remove(0);
        }
    }

    public void addTask(Integer delay, Task task)
    {
        E7Spells.getPlayer().sendMessage(Text.literal("added task"));
        int time = currentTick + delay;
        if (!map.containsKey(time))
        {
            map.put(time, new ArrayList<>());
        }
        map.get(time).add(task);

        if (!soonestTicks.contains(time)) soonestTicks.add(time);
        Collections.sort(soonestTicks);
    }

    private void decrementEverything()
    {
        HashMap<Integer, ArrayList<Task>> newMap = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Task>> entry : map.entrySet())
        {
            newMap.put(entry.getKey() - 10_000_000, entry.getValue());
        }
        map = newMap;

        ArrayList<Integer> newSoonestTicks = new ArrayList<>();
        for (Integer entry : soonestTicks)
        {
            newSoonestTicks.add(entry - 10_000_000);
        }

        currentTick -= 10_000_000;
    }

    public TickMap()
    {

    }

//    public void tick()
//    {
//        if (soonestEvent < 1) return;
//
//        soonestEvent--;
//
//        if (soonestEvent == 0)
//        {
//            for (Task task : map.firstEntry().getValue())
//            {
//                task.doTask();
//            }
//            map.remove(map.firstKey());
//        }
//    }
//
//    public void addTask(Integer delay, Task task)
//    {
//        if (!map.containsKey(delay))
//        {
//            map.put(delay, new ArrayList<>());
//        }
//        map.get(delay).add(task);
//        soonestEvent = map.firstKey();
//    }
}
