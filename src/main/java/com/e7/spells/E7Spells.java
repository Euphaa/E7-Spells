package com.e7.spells;

import com.e7.spells.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class E7Spells implements ModInitializer
{
	public static final String MODID = "e7-spells";


    public static final Logger E7SPELLS = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize()
	{

		ModItems.registerModItems();

		/* register key bindings */
		KeyBindings.registerKeys();

		/* register commands */
		Commands.registerCommands();

		E7SPELLS.info("Hello Fabric world!");
	}

	public static PlayerEntity getPlayer()
	{
		return MinecraftClient.getInstance().player;
	}
}