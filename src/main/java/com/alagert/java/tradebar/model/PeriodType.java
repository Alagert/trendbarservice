package com.alagert.java.tradebar.model;

import java.util.concurrent.TimeUnit;

/**
 * @author Andrey Tsvetkov
 */
public enum PeriodType {
    M1(60, TimeUnit.SECONDS),
    H1(60, TimeUnit.MINUTES),
    D1(24, TimeUnit.HOURS);

    private long delay;
    private TimeUnit timeUnit;

    private PeriodType(long delay, TimeUnit timeUnit) {
        this.delay = delay;
        this.timeUnit = timeUnit;
    }

    public long getDelay() {
        return delay;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
