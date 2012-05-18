package com.alagert.java.trendbar.model;


/**
 * @author Andrey Tsvetkov
 */
public class TrendBarFreezable {

    private boolean isFrozen = false;

    private double openPrice;
    private double closePrice;
    private double highPrice;
    private double lowPrice;
    private PeriodType periodType;
    private long timeStamp;

    public TrendBarFreezable(PeriodType periodType) {
        this.periodType = periodType;
    }

    public void freeze() {
        isFrozen = true;
    }

    public void setOpenPrice(double openPrice) {
        if (isFrozen) {
            throw new IllegalStateException();
        }
        this.openPrice = openPrice;
    }

    public void setClosePrice(double closePrice) {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        this.closePrice = closePrice;
    }

    public void setHighPrice(double highPrice) {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        this.highPrice = highPrice;
    }

    public void setLowPrice(double lowPrice) {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        this.lowPrice = lowPrice;
    }

    public void setTimeStamp(long timeStamp) {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        this.timeStamp = timeStamp;
    }

    public double getOpenPrice() {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        return openPrice;
    }

    public double getClosePrice() {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        return closePrice;
    }

    public double getHighPrice() {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        return highPrice;
    }

    public double getLowPrice() {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        return lowPrice;
    }

    public PeriodType getPeriodType() {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        return periodType;
    }

    public long getTimeStamp() {
        if (!isFrozen) {
            throw new IllegalStateException();
        }
        return timeStamp;
    }
}
