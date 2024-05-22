package com.e7.spells.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class PlayerNbtComponent implements Component, AutoSyncedComponent
{
    private final PlayerEntity provider;
    private int mana = 0;
    private int max_mana = 500;
    private int zombie_sword_charges = 0;
    private int wither_shield_cooldown = 100;

    public PlayerNbtComponent(PlayerEntity user)
    {
        this.provider = user;
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        this.mana = nbt.getInt("mana");
        this.max_mana = nbt.getInt("max_mana");
        this.zombie_sword_charges = nbt.getInt("zombie_sword_charges");
        this.wither_shield_cooldown = nbt.getInt("wither_shield_cooldown");
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        nbt.putInt("mana", this.mana);
        nbt.putInt("max_mana", this.max_mana);
        nbt.putInt("zombie_sword_charges", this.zombie_sword_charges);
        nbt.putInt("wither_shield_cooldown", this.wither_shield_cooldown);
    }

    public boolean subtractAbilityCost(int manaCost)
    {
        if (this.mana >= manaCost)
        {
            this.incrementMana(-manaCost);
            return true;
        }
        else
        {
            provider.sendMessage(Text.literal("§cOut of Mana!!!"));
            return false;
        }
    }

    public int getMana()
    {
        return mana;
    }

    public void setMana(int mana)
    {
        this.mana = mana;
    }

    public void incrementMana(int amount)
    {
        this.mana += amount;
    }

    public void addMana(float percent)
    {
        this.mana += percent * this.max_mana / 100;
        this.mana = Math.min(this.max_mana, this.mana);
    }

    public int getZombie_sword_charges()
    {
        return zombie_sword_charges;
    }

    public void setZombie_sword_charges(int zombie_sword_charges)
    {
        this.zombie_sword_charges = zombie_sword_charges;
    }

    public void incrementZombie_sword_charges(int amount)
    {
        this.zombie_sword_charges += amount;
    }

    public int getMax_mana()
    {
        return max_mana;
    }

    public void setMax_mana(int max_mana)
    {
        this.max_mana = max_mana;
    }

    public int getWither_shield_cooldown()
    {
        return wither_shield_cooldown;
    }

    public void setWither_shield_cooldown(int wither_shield_cooldown)
    {
        this.wither_shield_cooldown = wither_shield_cooldown;
    }

    public void incrementWither_shield_cooldown(int ticks)
    {
        this.wither_shield_cooldown -= ticks;
        if (this.wither_shield_cooldown < 0)
        {
            this.wither_shield_cooldown = 0;
        }
    }
}
