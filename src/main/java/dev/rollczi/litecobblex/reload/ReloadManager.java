package dev.rollczi.litecobblex.reload;

import java.util.LinkedHashSet;
import java.util.Set;

public class ReloadManager {

    private final Set<Reloadable> reloadables = new LinkedHashSet<>();

    public <T extends Reloadable> T register(T reloadable) {
        reloadables.add(reloadable);
        return reloadable;
    }

    public void reload() {
        reloadables.forEach(reloadable -> reloadable.reload());
    }

}
