package dev.rollczi.litecobblex.cobblex;

import dev.rollczi.litecobblex.message.MessageService;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CobbleXController implements Listener {

    private final CobbleXManager cobbleXManager;
    private final MessageService messageService;

    public CobbleXController(CobbleXManager cobbleXManager, MessageService messageService) {
        this.cobbleXManager = cobbleXManager;
        this.messageService = messageService;
    }

    @EventHandler
    void onCobbleXPlace(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();

        if (!cobbleXManager.isCobbleX(itemInHand)) {
            return;
        }

        Block placed = event.getBlockPlaced();
        placed.setType(Material.AIR);

        CobbleXDrop drop = cobbleXManager.openCobbleX(placed.getLocation());

        messageService.send(event.getPlayer(), pluginConfig -> pluginConfig.msgDrop.replace("{item}", drop.getName()));
    }

}
