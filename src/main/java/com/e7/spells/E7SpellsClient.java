package com.e7.spells;

import com.e7.spells.item.ModItems;
import com.e7.spells.networking.ClientPacketManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class E7SpellsClient implements ClientModInitializer
{
    public static final ItemGroup ITEM_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(E7SpellsCommon.MODID, "item_group"),
            FabricItemGroup.builder()
                    .displayName(Text.literal("E7 Spells"))
                    .icon(() -> new ItemStack(ModItems.HYPERION_SWORD))
                    .entries(((displayContext, entries) -> {
                        // register to custom creative tab here
                        entries.add(ModItems.GUN);
                        entries.add(ModItems.SMILE);
                        entries.add(ModItems.ZOMBIE_SWORD);
                        entries.add(ModItems.ASPECT_OF_THE_END_SWORD);
                        entries.add(ModItems.HYPERION_SWORD);
                        entries.add(ModItems.STORM_HELMET);
                        entries.add(ModItems.STORM_CHESTPLATE);
                        entries.add(ModItems.STORM_LEGGINGS);
                        entries.add(ModItems.STORM_BOOTS);
                    }))
                    .build()
    );

    static {
        System.out.println("printing here be thing was initialized");
    }

    @Override
    public void onInitializeClient()
    {
//        client = this;

        /* register event handlers */
        ClientPacketManager.registerPacketListeners();

        /* register key bindings */
        KeyBindings.registerKeys();

//        /* register items */
//        ModItems.registerModItemsForClient();
//        ModPotions.registerPotions();
//
//        /* register commands */
//        Commands.registerCommands();
//
//        /* register effects */
//        ModStatusEffects.registerEffects();
    }

    public static PlayerEntity getPlayer()
    {

        return MinecraftClient.getInstance().player;
    }
}
