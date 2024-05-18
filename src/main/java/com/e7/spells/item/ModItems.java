package com.e7.spells.item;


import com.e7.spells.E7Spells;
import com.e7.spells.item.armor.storm.StormArmorMaterial;
import com.e7.spells.item.items.Gun;
import com.e7.spells.item.items.Smile;
import com.e7.spells.item.tools.aote.AspectOfTheEndSwordItem;
import com.e7.spells.item.tools.hyperion.HyperionSwordItem;
import com.e7.spells.item.tools.zombie_sword.ZombieSwordItem;
import com.e7.spells.networking.ClientPacketManager;
import com.e7.spells.networking.E7Packets;
import com.e7.spells.util.Scheduler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SMILE = registerItem("smile", new Smile(new FabricItemSettings()));
    public static final Item GUN = registerItem("gun", new Gun(new FabricItemSettings()));
    public static final Item ZOMBIE_SWORD = new ZombieSwordItem();
    public static final Item ASPECT_OF_THE_END_SWORD = new AspectOfTheEndSwordItem();
    public static final Item HYPERION_SWORD = new HyperionSwordItem();
    public static final ArmorMaterial STORM_MATERIAL = new StormArmorMaterial();
    public static final Item STORM_HELMET = new ArmorItem(STORM_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings());
    public static final Item STORM_CHESTPLATE = new ArmorItem(STORM_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings());
    public static final Item STORM_LEGGINGS = new ArmorItem(STORM_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings());
    public static final Item STORM_BOOTS = new ArmorItem(STORM_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings());


    //Registers the item into the game
    public static void registerModItems()
    {
        E7Spells.E7SPELLS.info("Registering Mod Items for " + E7Spells.MODID);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            // send packet to set nbt data to player
            ClientPacketManager.sendPacketToServer(E7Packets.INITIATE_PLAYER_NBT, PacketByteBufs.empty());
        });

        /* tools */

        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "zombie_sword"), ZOMBIE_SWORD);
        Scheduler.registerRegularEvent(15*20, ZombieSwordItem::addChargeToEveryone);
        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "aspect_of_the_end_sword"), ASPECT_OF_THE_END_SWORD);
        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "hyperion_sword"), HYPERION_SWORD);

//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);

        /* armor */

//        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "storm_material"), STORM_BOOTS);
        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "storm_helmet"), STORM_HELMET);
        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "storm_chestplate"), STORM_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "storm_leggings"), STORM_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, "storm_boots"), STORM_BOOTS);


    }




        //Make thing available in creative tabs
        public static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
            entries.add(SMILE);
            entries.add(GUN);
        }

        //Setting up for all future items
        private static Item registerItem(String name, Item item)
        {
            return Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, name), item);
        }


}
