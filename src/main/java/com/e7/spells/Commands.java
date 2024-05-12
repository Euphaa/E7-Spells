package com.e7.spells;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import static net.minecraft.server.command.CommandManager.*;

public class Commands
{

    public static void registerCommands()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ping").executes(context -> {
                context.getSource().sendFeedback(() -> Text.literal("pong bitches!!!"), false);

                return 1;
            })));


    }

}
