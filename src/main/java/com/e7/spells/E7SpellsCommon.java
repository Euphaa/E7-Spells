package com.e7.spells;

import com.e7.spells.item.ModItems;
import com.e7.spells.item.tools.ZombieSword;
import com.e7.spells.networking.ModPayloads;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.statuseffects.ModStatusEffects;
import com.e7.spells.util.CCAComponents;
import com.e7.spells.util.Scheduler;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class E7SpellsCommon implements ModInitializer
{
    public static final String MODID = "e7-spells";
    public static final Logger E7SPELLS = LoggerFactory.getLogger(MODID);
    public static final Random random = new Random();
    private static Scheduler scheduler = new Scheduler();

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

        /* register commands */
        Commands.registerCommands();

        /* register event handlers */
        ServerPacketManager.registerPacketListeners();

        /* CCA components */
        scheduler.registerRegularEvent(4, CCAComponents::syncAllPlayers);
        scheduler.registerRegularEvent(4, CCAComponents::addManaToAllPlayers);
        scheduler.registerRegularEvent(15*20, ZombieSword::addChargeToEveryone);

    }

    public static Scheduler getScheduler()
    {
        return scheduler;
    }
}
