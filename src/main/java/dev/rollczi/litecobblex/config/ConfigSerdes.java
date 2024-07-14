package dev.rollczi.litecobblex.config;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.meta.ItemMeta;

class ConfigSerdes implements OkaeriSerdesPack {

    private final MiniMessage miniMessage;

    ConfigSerdes(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    @Override
    public void register(SerdesRegistry registry) {
        registry.registerExclusive(ItemMeta.class, new ConfigItemMetaSerializer(miniMessage));
    }

}
