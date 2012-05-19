package com.alagert.java.trendbar.model;

/**
 * @author Andrey Tsvetkov
 */
public enum Symbol {
    EURUSD,
    EURJPY;
    private static final int SIZE = Symbol.values().length;

    public static int size() {
        return SIZE;
    }

}
