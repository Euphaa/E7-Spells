package com.e7.spells;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyBindings
{
    private static KeyBinding KEY_APPLY_STRENGTH = KeyBindingHelper.registerKeyBinding(new KeyBinding("Apply Strength", GLFW.GLFW_KEY_LEFT_BRACKET, "E7"));

    public static void registerKeys()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KEY_APPLY_STRENGTH.wasPressed()) {
                EffectHandler.addStrengthEffectToThePlayer();
                client.player.sendMessage(Text.literal("applying strength for 10 seconds!"), false);
            }
        });
    }
}
