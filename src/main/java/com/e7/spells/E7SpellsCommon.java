package com.e7.spells;

import com.e7.spells.item.ModItems;
import com.e7.spells.item.tools.ZombieSword;
import com.e7.spells.networking.ModPayloads;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.statuseffects.FerocityStatusEffect;
import com.e7.spells.statuseffects.ModStatusEffects;
import com.e7.spells.util.CCAComponents;
import com.e7.spells.util.Scheduler;
import com.e7.spells.util.WitherScrollsData;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.DataComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class E7SpellsCommon implements ModInitializer
{
    public static final String MODID = "e7-spells";
    public static final Logger E7SPELLS = LoggerFactory.getLogger(MODID);
    public static final int PLAYER_NBT_UPDATE_TICK_INTERVAL = 4;
    public static final Random random = new Random();
    private static Scheduler scheduler = new Scheduler();
    public static final DataComponentType<WitherScrollsData> WITHER_SCROLLS_DATA =
            Registry.register(Registries.DATA_COMPONENT_TYPE, id("scrolls"), WitherScrollsData.COMPONENT_TYPE);
//            Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier(MODID, "wither_scrolls"), new WitherScrollsDataComponent((byte) 100));

    @Override
    public void onInitialize()
    {
        /* register payloads */
        ModPayloads.registerPayloads();

        /* register event handlers */
        scheduler.registerTicker();

        /* register items */
        ModItems.registerModItemsForServer();
        ModPotions.registerPotions();

        /* register effects */
        ModStatusEffects.registerEffects();
        FerocityStatusEffect.registerEffect();

        /* register commands */
        Commands.registerCommands();

        /* register event handlers */
        ServerPacketManager.registerPacketListeners();

        /* CCA components */
        scheduler.registerRegularEvent(PLAYER_NBT_UPDATE_TICK_INTERVAL, CCAComponents::updatePlayerNbt);
        scheduler.registerRegularEvent(PLAYER_NBT_UPDATE_TICK_INTERVAL, CCAComponents::syncAllPlayers);
        scheduler.registerRegularEvent(15*20, ZombieSword::addChargeToEveryone);

        /* MISC */



    }

    public static Identifier id(String name)
    {
        return new Identifier(MODID, name);
    }

    public static Scheduler getScheduler()
    {
        return scheduler;
    }
}
