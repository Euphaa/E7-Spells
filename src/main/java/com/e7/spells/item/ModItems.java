package com.e7.spells.item;


import com.e7.spells.E7SpellsCommon;
import com.e7.spells.E7SpellsServer;
import com.e7.spells.item.armor.storm.StormArmorMaterial;
import com.e7.spells.item.items.Gun;
import com.e7.spells.item.items.Smile;
import com.e7.spells.item.tools.aote.AspectOfTheEndSwordItem;
import com.e7.spells.item.tools.hyperion.HyperionSwordItem;
import com.e7.spells.item.tools.zombie_sword.ZombieSwordItem;
import com.e7.spells.networking.ClientPacketManager;
import com.e7.spells.networking.E7Packets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SMILE = registerItem("smile", new Smile(new FabricItemSettings()));
    public static final Item GUN = registerItem("gun", new Gun(new FabricItemSettings()));
    public static final Item ZOMBIE_SWORD = registerItem("zombie_sword", new ZombieSwordItem());
    public static final Item ASPECT_OF_THE_END_SWORD = registerItem("aspect_of_the_end_sword", new AspectOfTheEndSwordItem());
    public static final Item HYPERION_SWORD = registerItem("hyperion_sword", new HyperionSwordItem());
    public static final ArmorMaterial STORM_MATERIAL = new StormArmorMaterial();
    public static final Item STORM_HELMET = registerItem("storm_helmet", new ArmorItem(STORM_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item STORM_CHESTPLATE = registerItem("storm_chestplate", new ArmorItem(STORM_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item STORM_LEGGINGS = registerItem("storm_leggings", new ArmorItem(STORM_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item STORM_BOOTS = registerItem("storm_boots", new ArmorItem(STORM_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()));


    //Registers the item into the game
    public static void registerModItemsForClient()
    {
        E7SpellsCommon.E7SPELLS.info("Registering Mod Items for " + E7SpellsCommon.MODID);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            // send packet to set nbt data to player
            ClientPacketManager.sendPacketToServer(E7Packets.INITIATE_PLAYER_NBT, PacketByteBufs.empty());
        });


    }

    public static void registerModItemsForServer()
    {
        E7SpellsCommon.E7SPELLS.info("Registering Mod Items for " + E7SpellsCommon.MODID);

        E7SpellsServer.getScheduler().registerRegularEvent(15*20, ZombieSwordItem::addChargeToEveryone);
    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(E7SpellsCommon.MODID, name), item);
    }


}
