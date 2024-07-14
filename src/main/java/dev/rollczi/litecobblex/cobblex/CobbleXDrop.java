package dev.rollczi.litecobblex.cobblex;

import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.inventory.ItemStack;

@Contextual
public class CobbleXDrop {

    public ItemStack drop;
    public double chance;
    public String name;

    public CobbleXDrop(ItemStack drop, double chance, String name) {
        this.drop = drop;
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
