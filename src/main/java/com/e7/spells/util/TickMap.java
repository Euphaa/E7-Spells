package com.e7.spells.util;

import com.e7.spells.E7Spells;
import net.minecraft.text.Text;

import java.util.*;

public class TickMap
{
    private TreeMap<Integer, ArrayList<Task>> map = new TreeMap<>();
    private int soonestEvent;

    public TickMap()
    {

    }

    public void tick()
    {
        if (soonestEvent < 1) return;

        soonestEvent--;

        if (soonestEvent == 0)
        {
            for (Task task : map.firstEntry().getValue())
            {
                task.doTask();
            }
            map.remove(0);
        }
    }

    public void addTask(Integer delay, Task task)
    {
        if (!map.containsKey(delay))
        {
            map.put(delay, new ArrayList<>());
        }
        map.get(delay).add(task);
        soonestEvent = map.firstKey();
    }
}
