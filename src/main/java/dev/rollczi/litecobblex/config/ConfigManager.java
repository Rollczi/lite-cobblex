package dev.rollczi.litecobblex.config;

import dev.rollczi.litecobblex.reload.Reloadable;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import net.dzikoysk.cdn.source.Resource;
import org.bukkit.inventory.ItemStack;

public class ConfigManager implements Reloadable {

    private final Cdn cdn;
    private final File folder;
    private final Set<ReloadableConfig> configs = new HashSet<>();

    public ConfigManager(File folder) {
        this.folder = folder;
        this.cdn = CdnFactory.createYamlLike().getSettings()
            .withMemberResolver(Visibility.PACKAGE_PRIVATE)
            .withComposer(ItemStack.class, new ItemStackComposer())
            .build();
    }

    public <T extends ReloadableConfig> T load(T config) {
        Resource resource = config.resource(folder);

        cdn.load(resource, config)
            .orThrow((exception) -> new RuntimeException("Cannot load " + config.getClass().getSimpleName() + " config", exception));

        cdn.render(config, resource)
            .orThrow((exception) -> new RuntimeException("Cannot save " + config.getClass().getSimpleName() + " config", exception));

        configs.add(config);
        return config;
    }

    public <T extends ReloadableConfig> T save(T config) {
        Resource resource = config.resource(folder);

        cdn.render(config, resource)
            .orThrow((exception) -> new RuntimeException("Cannot save " + config.getClass().getSimpleName() + " config", exception));

        return config;
    }

    @Override
    public void reload() {
        for (ReloadableConfig config : configs) {
            load(config);
            config.reload();
        }
    }

}
