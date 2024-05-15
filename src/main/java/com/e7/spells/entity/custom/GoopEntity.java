package com.e7.spells.entity.custom;

import com.e7.spells.entity.ModEntites;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GoopEntity extends AnimalEntity {
    public GoopEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals(){
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new TemptGoal(this, 3, Ingredient.ofItems(Items.BREAD), false));

        this.goalSelector.add(2, new WanderAroundFarGoal(this,10));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 12f));
        this.goalSelector.add(4, new LookAroundGoal(this));

    }

    public static DefaultAttributeContainer.Builder createGloopAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);

    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntites.GOOP.create(world);
    }
}
