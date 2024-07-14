package dev.rollczi.litecobblex.cobblex;

import eu.okaeri.configs.OkaeriConfig;
import java.util.function.Supplier;
import org.bukkit.inventory.ItemStack;

public class CobbleXDrop extends OkaeriConfig {

    private ItemStack drop;
    private double chance;
    private String name;

    public CobbleXDrop() {
    }

    public CobbleXDrop(ItemStack drop, double chance, String name) {
        this.drop = drop;
        this.chance = chance;
        this.name = name;
    }

    public CobbleXDrop(Supplier<ItemStack> drop, double chance, String name) {
        this.drop = drop.get();
        this.chance = chance;
        this.name = name;
    }

    public ItemStack getDrop() {
        return drop;
    }

    public double getChance() {
        return chance;
    }

    public String getName() {
        return name;
    }

}
