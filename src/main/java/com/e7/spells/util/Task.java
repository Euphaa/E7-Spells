package com.e7.spells.util;

import net.minecraft.server.MinecraftServer;

@FunctionalInterface
public interface Task {
    void doTask(MinecraftServer server);
}
