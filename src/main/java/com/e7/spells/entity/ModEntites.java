//package com.e7.spells.entity;
//
//import com.e7.spells.E7Spells;
//import com.e7.spells.entity.custom.GoopEntity;
//import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
//import net.minecraft.entity.EntityDimensions;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.SpawnGroup;
//import net.minecraft.registry.Registries;
//import net.minecraft.registry.Registry;
//import net.minecraft.util.Identifier;
//
//public class ModEntites {
//    public static final EntityType<GoopEntity> GOOP = Registry.register(Registries.ENTITY_TYPE,
//            new Identifier(E7Spells.MODID,"goop"),
//            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GoopEntity::new)
//                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());
//
//}
