package dev.rollczi.litecobblex.cobblex;

import dev.rollczi.litecobblex.config.PluginConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CobbleXManager {

    private static final Random RANDOM = new Random();
    private static final int COBBLE_COUNT_FOR_COBBLEX = 64 * 9;

    private final PluginConfig pluginConfig;

    public CobbleXManager(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    public CobbleXBuyResult buyCobbleX(Player player) {
        PlayerInventory inventory = player.getInventory();
        int cobblestoneAmount = 0;
        List<Integer> cobblestoneSlots = new ArrayList<>();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null) {
                continue;
            }

            if (item.getType() == Material.COBBLESTONE) {
                cobblestoneAmount += item.getAmount();
                cobblestoneSlots.add(i);

                if (cobblestoneAmount >= COBBLE_COUNT_FOR_COBBLEX) {
                    break;
                }
            }
        }

        if (cobblestoneAmount < COBBLE_COUNT_FOR_COBBLEX) {
            return CobbleXBuyResult.NOT_ENOUGH_COBBLESTONE;
        }

        int removedCobblestone = 0;

        // remove cobblestone from inventory
        for (int slot : cobblestoneSlots) {
            ItemStack item = inventory.getItem(slot);
            int amount = item.getAmount();

            if (removedCobblestone + amount > COBBLE_COUNT_FOR_COBBLEX) {
                item.setAmount(amount - (COBBLE_COUNT_FOR_COBBLEX - removedCobblestone));
                break;
            }

            removedCobblestone += amount;
            inventory.setItem(slot, null);
        }

        return this.giveCobbleX(player);
    }

    public CobbleXBuyResult giveCobbleX(Player player) {
        ItemStack cloned = this.pluginConfig.cobblexItem.clone();

        if (this.canGiveCobbleX(player)) {
            player.getInventory().addItem(cloned);
            return CobbleXBuyResult.GIVEN;
        }

        player.getWorld().dropItemNaturally(player.getLocation(), cloned);
        return CobbleXBuyResult.DROPPED;
    }

    private boolean canGiveCobbleX(Player player) {
        return player.getInventory().firstEmpty() != -1;
    }

    public boolean isCobbleX(ItemStack stack) {
        return stack.isSimilar(this.pluginConfig.cobblexItem);
    }

    public List<CobbleXDrop> openCobbleX(Location location) {
        List<CobbleXDrop> drops = new ArrayList<>();

        for (CobbleXDrop cobblexDrop : this.pluginConfig.cobblexDrops) {
            if (RANDOM.nextDouble() <= cobblexDrop.getChance()) {
                location.getWorld().dropItemNaturally(location, cobblexDrop.getDrop().clone());
                drops.add(cobblexDrop);
            }
        }

        return drops;
    }

}
