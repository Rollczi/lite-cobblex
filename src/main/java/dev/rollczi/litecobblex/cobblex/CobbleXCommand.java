package dev.rollczi.litecobblex.cobblex;

import dev.rollczi.litecobblex.message.MessageService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

@Command(name = "cobblex", aliases = "cx")
public class CobbleXCommand {

    private final CobbleXManager cobbleXManager;
    private final MessageService messageService;

    public CobbleXCommand(CobbleXManager cobbleXManager, MessageService messageService) {
        this.cobbleXManager = cobbleXManager;
        this.messageService = messageService;
    }

    @Execute
    void openCrafting(@Context Player player) {
        InventoryView inventoryView = player.openWorkbench(null, true);

        if (inventoryView == null) {
            player.sendMessage("§cYou can't open the crafting table.");
            return;
        }

        CobbleXBuyResult result = cobbleXManager.buyCobbleX(player);

        if (result.isSuccess()) {
            messageService.send(player, config -> config.msgPaymentSucessfull);

            if (result == CobbleXBuyResult.DROPPED) {
                messageService.send(player, config -> config.msgDropOnGround);
            }
        } else {
            messageService.send(player, config -> config.msgPaymentFailed);
        }
    }

}
