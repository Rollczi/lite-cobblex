package dev.rollczi.litecobblex;

import dev.rollczi.litecobblex.cobblex.CobbleXManager;
import dev.rollczi.litecobblex.cobblex.CobbleXDrop;
import dev.rollczi.litecobblex.config.ConfigManager;
import dev.rollczi.litecobblex.config.PluginConfig;
import dev.rollczi.litecobblex.reload.ReloadManager;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@Command(name = "cxadmin")
@Permission("litecobblex.admin")
class LiteCobbleXAdminCommand {

    private final ReloadManager reloadManager;
    private final ConfigManager configManager;
    private final CobbleXManager cobbleXManager;
    private final PluginConfig pluginConfig;

    public LiteCobbleXAdminCommand(ReloadManager reloadManager, ConfigManager configManager, CobbleXManager cobbleXManager, PluginConfig pluginConfig) {
        this.reloadManager = reloadManager;
        this.configManager = configManager;
        this.cobbleXManager = cobbleXManager;
        this.pluginConfig = pluginConfig;
    }

    @Async
    @Execute(name = "reload")
    void reload() {
        reloadManager.reload();
    }

    @Execute(name = "additem")
    void addItem(@Context Player player, @Arg("chance") double chance, @Join("name") String name) {
        ItemStack stack = player.getInventory().getItem(EquipmentSlot.HAND);

        if (stack.isEmpty()) {
            return;
        }

        pluginConfig.cobblexDrops.add(new CobbleXDrop(stack, chance, name));
        configManager.save(pluginConfig);
    }

    @Execute(name = "get")
    void get(@Context Player player) {
        cobbleXManager.giveCobbleX(player);
    }

}
