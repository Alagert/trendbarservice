package com.alagert.java.trendbar.model;

/**
 * @author Andrey Tsvetkov
 */
public class Quote {
    private final Symbol symbol;
    private final double price;
    private final long timestamp;

    public Quote(Symbol symbol, double price, long timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }


    @Override
    public String toString() {
        return "Quote{" +
                "symbol=" + symbol +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
}
