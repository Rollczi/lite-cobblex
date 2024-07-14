package dev.rollczi.litecobblex.reload;

public interface Reloadable {

    default void load() {}

    void reload();

}
