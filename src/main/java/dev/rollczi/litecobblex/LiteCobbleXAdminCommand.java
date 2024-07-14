package dev.rollczi.litecobblex;

import dev.rollczi.litecobblex.cobblex.CobbleXManager;
import dev.rollczi.litecobblex.cobblex.CobbleXDrop;
import dev.rollczi.litecobblex.config.ConfigService;
import dev.rollczi.litecobblex.config.PluginConfig;
import dev.rollczi.litecobblex.message.MessageService;
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
    private final ConfigService configManager;
    private final CobbleXManager cobbleXManager;
    private final PluginConfig pluginConfig;
    private final MessageService messageService;

    public LiteCobbleXAdminCommand(ReloadManager reloadManager, ConfigService configManager, CobbleXManager cobbleXManager, PluginConfig pluginConfig, MessageService messageService) {
        this.reloadManager = reloadManager;
        this.configManager = configManager;
        this.cobbleXManager = cobbleXManager;
        this.pluginConfig = pluginConfig;
        this.messageService = messageService;
    }

    @Async
    @Execute(name = "reload")
    void reload() {
        reloadManager.reload();
    }

    @Execute(name = "add drop")
    void addItem(@Context Player player, @Arg("chance") double chance, @Join("name") String name) {
        ItemStack stack = player.getInventory().getItem(EquipmentSlot.HAND);

        if (stack.isEmpty()) {
            messageService.send(player, config -> config.msgNoItemInHand);
            return;
        }

        pluginConfig.cobblexDrops.add(new CobbleXDrop(stack.clone(), chance, name));
        configManager.save();
    }

    @Execute(name = "set cobblex")
    void setCobbleX(@Context Player player) {
        ItemStack stack = player.getInventory().getItem(EquipmentSlot.HAND);

        if (stack.isEmpty()) {
            messageService.send(player, config -> config.msgNoItemInHand);
            return;
        }

        pluginConfig.cobblexItem = stack.clone();
        configManager.save();
    }

    @Execute(name = "get cobblex")
    void get(@Context Player player) {
        cobbleXManager.giveCobbleX(player);
    }

}
