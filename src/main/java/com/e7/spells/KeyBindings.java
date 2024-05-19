package com.e7.spells;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindings
{
    private static KeyBinding KEY_APPLY_FEROCITY = KeyBindingHelper.registerKeyBinding(new KeyBinding("Apply Ferocity", GLFW.GLFW_KEY_LEFT_BRACKET, "E7"));

    public static void registerKeys()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (KEY_APPLY_FEROCITY.wasPressed()) {
//                ClientPacketManager.sendPacketToServer(E7Packets.FEROCITY_KEY_ACTIVATION, PacketByteBufs.empty());
//            }
        });
    }
}
