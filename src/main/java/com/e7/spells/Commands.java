package com.e7.spells;

import com.e7.spells.util.CCAComponents;
import com.e7.spells.util.PlayerNbtComponent;
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

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("setmaxmana")
            .then(argument("max mana", IntegerArgumentType.integer())
            .requires(source -> source.hasPermissionLevel(3))
            .executes(context -> {
                final int v1 = IntegerArgumentType.getInteger(context, "max mana");

                context.getSource().sendFeedback(() -> Text.literal("set max mana to " + v1), false);
                CCAComponents.PLAYER_NBT.get(context.getSource().getPlayer()).setMax_mana(v1);

                return 1;
            }
        ))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("printmana")
            .requires(source -> source.hasPermissionLevel(3))
            .executes(context -> {

                PlayerNbtComponent c = CCAComponents.PLAYER_NBT.get(context.getSource().getPlayer());
                context.getSource().sendFeedback(() -> Text.literal("Max mana: " + c.getMax_mana()), false);
                context.getSource().sendFeedback(() -> Text.literal("Mana: " + c.getMana()), false);

                return 1;
            }
        )));

    }

}
