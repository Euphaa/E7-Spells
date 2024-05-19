package com.e7.spells;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class E7SpellsCommon implements ModInitializer
{
	public static final String MODID = "e7-spells";
    public static final Logger E7SPELLS = LoggerFactory.getLogger(MODID);
	public static final Random random = new Random();


	@Override
	public void onInitialize()
	{
		new E7SpellsServer().onInitializeServer();

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			new E7SpellsClient().onInitializeClient();
		}
	}

}