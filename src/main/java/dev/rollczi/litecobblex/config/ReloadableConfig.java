package dev.rollczi.litecobblex.config;

import dev.rollczi.litecobblex.reload.Reloadable;
import java.io.File;
import net.dzikoysk.cdn.source.Resource;

public interface ReloadableConfig extends Reloadable {

    Resource resource(File folder);

    default void reload() {}

}
