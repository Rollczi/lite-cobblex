package dev.rollczi.litecobblex.config;

import dev.rollczi.litecobblex.reload.Reloadable;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ConfigService implements Reloadable {

    private final File dataFolder;
    private final MiniMessage miniMessage;
    private final Set<OkaeriConfig> configs = new HashSet<>();

    public ConfigService(File dataFolder, MiniMessage miniMessage) {
        this.dataFolder = dataFolder;
        this.miniMessage = miniMessage;
    }

    public <T extends OkaeriConfig> T load(T config, String fileName) {
        T configFile = ConfigManager.initialize(config);

        configFile
            .withConfigurer(new YamlBukkitConfigurer(), new SerdesCommons(), new SerdesBukkit(), new ConfigSerdes(miniMessage))
            .withBindFile(new File(this.dataFolder, fileName))
            .withRemoveOrphans(true)
            .saveDefaults()
            .load(true);

        this.configs.add(configFile);

        return configFile;
    }

    @Override
    public void reload() {
        for (OkaeriConfig config : this.configs) {
            config.load();
        }
    }

    public void save() {
        for (OkaeriConfig config : this.configs) {
            config.save();

            if (config instanceof Reloadable reloadable) {
                reloadable.reload();
            }
        }
    }

}
