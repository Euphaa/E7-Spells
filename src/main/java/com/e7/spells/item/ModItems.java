package com.e7.spells.item;


import com.e7.spells.E7Spells;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
        //Test Item
        public static final Item SMILE = registerItem("smile", new Item(new FabricItemSettings()));

        //Make thing avaliable in creative tabs
        private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
            entries.add(SMILE);
        }

        //Setting up for all future items
        private static Item registerItem(String name, Item item) {
            return Registry.register(Registries.ITEM, new Identifier(E7Spells.MODID, name), item);
        }
        //Registers the item into the game
        public static void registerModItems(){
            E7Spells.E7SPELLS.info("Registering Mod Items for " + E7Spells.MODID);

            ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);

    }

}
