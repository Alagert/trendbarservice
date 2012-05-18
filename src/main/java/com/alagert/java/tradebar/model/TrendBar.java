package com.alagert.java.tradebar.model;


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
    private long timeStamp;

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

    public long getTimeStamp() {
        return timeStamp;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public PeriodType getPeriodType() {
        return periodType;
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
                ", timeStamp=" + timeStamp +
                '}';
    }
}
