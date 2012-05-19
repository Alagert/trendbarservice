package com.alagert.java.trendbar.model;


/**
 * @author Andrey Tsvetkov
 */
public class TrendBar {
    private double openPrice;
    private double closePrice;
    private double highPrice;
    private double lowPrice;
    private PeriodType periodType;
    private Symbol symbol;
    private long timestamp;

    public TrendBar(PeriodType periodType, Symbol symbol) {
        this.periodType = periodType;
        this.symbol = symbol;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public TrendBar copy() {
        TrendBar newBar = new TrendBar(periodType, symbol);
        newBar.setClosePrice(closePrice);
        newBar.setHighPrice(highPrice);
        newBar.setLowPrice(lowPrice);
        newBar.setOpenPrice(openPrice);
        newBar.setTimestamp(timestamp);
        return newBar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrendBar trendBar = (TrendBar) o;

        if (Double.compare(trendBar.closePrice, closePrice) != 0) {
            return false;
        }
        if (Double.compare(trendBar.highPrice, highPrice) != 0) {
            return false;
        }
        if (Double.compare(trendBar.lowPrice, lowPrice) != 0) {
            return false;
        }
        if (Double.compare(trendBar.openPrice, openPrice) != 0) {
            return false;
        }
        if (timestamp != trendBar.timestamp) {
            return false;
        }
        if (periodType != trendBar.periodType) {
            return false;
        }
        if (symbol != trendBar.symbol) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = openPrice != +0.0d ? Double.doubleToLongBits(openPrice) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = closePrice != +0.0d ? Double.doubleToLongBits(closePrice) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = highPrice != +0.0d ? Double.doubleToLongBits(highPrice) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = lowPrice != +0.0d ? Double.doubleToLongBits(lowPrice) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (periodType != null ? periodType.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "TrendBar{" +
                "openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", periodType=" + periodType +
                ", symbol=" + symbol +
                ", timestamp=" + timestamp +
                '}';
    }
}
