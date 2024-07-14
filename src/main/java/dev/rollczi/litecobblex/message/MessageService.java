package dev.rollczi.litecobblex.message;

import dev.rollczi.litecobblex.config.PluginConfig;
import java.util.function.Function;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class MessageService {

    private final PluginConfig pluginConfig;
    private final MiniMessage miniMessage;

    public MessageService(PluginConfig pluginConfig, MiniMessage miniMessage) {
        this.pluginConfig = pluginConfig;
        this.miniMessage = miniMessage;
    }

    public void send(Player player, Function<PluginConfig, String> message) {
        player.sendMessage(miniMessage.deserialize(message.apply(pluginConfig)));
    }

}
