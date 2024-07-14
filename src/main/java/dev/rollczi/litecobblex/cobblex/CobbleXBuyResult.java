package dev.rollczi.litecobblex.cobblex;

public enum CobbleXBuyResult {
    GIVEN,
    DROPPED,
    NOT_ENOUGH_COBBLESTONE,
    ;

    public boolean isSuccess() {
        return this == GIVEN || this == DROPPED;
    }

}
