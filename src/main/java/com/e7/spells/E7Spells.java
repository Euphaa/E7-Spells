package com.e7.spells;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class E7Spells implements ModInitializer
{
	public static final String MODID = "e7-spells";


    public static final Logger E7SPELLS = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize()
	{

		/* register key bindings */
		KeyBindings.registerKeys();

		E7SPELLS.info("Hello Fabric world!");
	}

	public static PlayerEntity getPlayer()
	{
		return MinecraftClient.getInstance().player;
	}
}