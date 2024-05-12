package com.e7.spells;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import static net.minecraft.server.command.CommandManager.*;

public class Commands
{

    public static void registerCommands()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ping")
                .requires(source -> source.hasPermissionLevel(0))
                .executes(context -> {
                context.getSource().sendFeedback(() -> Text.literal("pong bitches!!!"), false);

                return 1;
            })));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("domath")
            .then(argument("value1", IntegerArgumentType.integer())
            .then(argument("value2", IntegerArgumentType.integer())
            .requires(source -> source.hasPermissionLevel(0))
            .executes(context -> {
                final int v1 = IntegerArgumentType.getInteger(context, "value1");
                final int v2 = IntegerArgumentType.getInteger(context, "value2");

                context.getSource().sendFeedback(() -> Text.literal("%d + %d = %d".formatted(v1, v2, v1 + v2)), false);

                return 1;
            }
        )))));

    }

}
