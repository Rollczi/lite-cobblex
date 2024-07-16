package dev.rollczi.litecobblex.reload;

public interface Reloadable {

    default void init() {}

    void reload();

}
