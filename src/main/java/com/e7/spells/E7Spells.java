package com.e7.spells;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class E7Spells implements ModInitializer {
	public static final String MODID = "e7-spells";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}

	public static PlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}
}