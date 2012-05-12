package com.alagert.java.tradebar.model;

import java.math.BigDecimal;

/**
 * @author Andrey Tsvetkov
 */
public class TrendBar {
    private final BigDecimal openPrice;
    private final BigDecimal closePrice;
    private final BigDecimal highPrice;
    private final BigDecimal lowPrice;
    private final PeriodType periodType;
    private final long timeStamp;

    public TrendBar(BigDecimal openPrice, BigDecimal closePrice, BigDecimal highPrice, BigDecimal lowPrice, PeriodType periodType, long timeStamp) {
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.periodType = periodType;
        this.timeStamp = timeStamp;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
