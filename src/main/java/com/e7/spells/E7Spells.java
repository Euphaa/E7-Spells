package com.e7.spells;

//import com.e7.spells.entity.ModEntites;
//import com.e7.spells.entity.custom.GoopEntity;
import com.e7.spells.item.ModItems;
import com.e7.spells.networking.ClientPacketManager;
import com.e7.spells.networking.ServerPacketManager;
import com.e7.spells.statuseffects.FerocityStatusEffect;
import com.e7.spells.util.Scheduler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class E7Spells implements ModInitializer
{
	public static final String MODID = "e7-spells";
    public static final Logger E7SPELLS = LoggerFactory.getLogger(MODID);
	public static final StatusEffect FEROCITY = new FerocityStatusEffect();
	public static final Random random = new Random();
	public static final ItemGroup ITEM_GROUP = Registry.register(
			Registries.ITEM_GROUP,
			new Identifier(MODID, "item_group"),
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




	@Override
	public void onInitialize()
	{
		/* register event handlers */
		Scheduler.registerTicker();
		ClientPacketManager.registerPacketListeners();
		ServerPacketManager.registerPacketListeners();

		/* register items */
		ModItems.registerModItems();


		/* register key bindings */
		KeyBindings.registerKeys();

		/* register commands */
		Commands.registerCommands();

		/* register effects */
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MODID, "ferocity"), FEROCITY);
		FerocityStatusEffect.registerEffect();
//		FabricDefaultAttributeRegistry.register(ModEntites.GOOP, GoopEntity.createGloopAttributes());

		ModPotions.registerPotions();



		E7SPELLS.info("Hello Fabric world!");
	}


	public static PlayerEntity getPlayer()
	{

		return MinecraftClient.getInstance().player;
	}
}