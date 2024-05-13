package com.e7.spells;

import com.e7.spells.item.ModItems;
import com.e7.spells.statuseffects.FerocityStatusEffect;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class E7Spells implements ModInitializer
{
	public static final String MODID = "e7-spells";
    public static final Logger E7SPELLS = LoggerFactory.getLogger(MODID);
	public static final StatusEffect FEROCITY = new FerocityStatusEffect();

	@Override
	public void onInitialize()
	{
		/* register event handlers */
		TickHandler.registerTicker();

		/* register items */
		ModItems.registerModItems();

		/* register key bindings */
		KeyBindings.registerKeys();

		/* register commands */
		Commands.registerCommands();

		/* register effects */
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MODID, "ferocity"), FEROCITY);
		FerocityStatusEffect.registerEffect();



		E7SPELLS.info("Hello Fabric world!");
	}

	public static PlayerEntity getPlayer()
	{
		return MinecraftClient.getInstance().player;
	}
}