package dev.rollczi.litecobblex.cobblex;

import dev.piotrulla.craftinglib.Crafting;
import dev.piotrulla.craftinglib.CraftingException;
import dev.piotrulla.craftinglib.CraftingManager;
import dev.rollczi.litecobblex.config.PluginConfig;
import dev.rollczi.litecobblex.reload.Reloadable;
import org.bukkit.Material;

public class CobbleXRecipeCreator implements Reloadable {

    private static final String COBBLEX_KEY = "cobblex";

    private final CraftingManager craftingManager;
    private final PluginConfig pluginConfig;

    public CobbleXRecipeCreator(CraftingManager craftingManager, PluginConfig pluginConfig) {
        this.craftingManager = craftingManager;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public void init() {
        Crafting pickaxeCrafting = Crafting.builder()
            .withName(COBBLEX_KEY)
            .withMaterial(1, Material.COBBLESTONE, 64)
            .withMaterial(2, Material.COBBLESTONE, 64)
            .withMaterial(3, Material.COBBLESTONE, 64)
            .withMaterial(4, Material.COBBLESTONE, 64)
            .withMaterial(5, Material.COBBLESTONE, 64)
            .withMaterial(6, Material.COBBLESTONE, 64)
            .withMaterial(7, Material.COBBLESTONE, 64)
            .withMaterial(8, Material.COBBLESTONE, 64)
            .withMaterial(9, Material.COBBLESTONE, 64)
            .withResultItem(pluginConfig.cobblexItem.clone())
            .build();

        craftingManager.createCrafting(pickaxeCrafting);
    }

    @Override
    public void reload() {
        try {
            craftingManager.removeCrafting(COBBLEX_KEY);
        }
        catch (CraftingException ignored) {}

        this.init();
    }

}
