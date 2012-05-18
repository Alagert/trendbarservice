package com.alagert.java.tradebar.service.impl;

import com.alagert.java.tradebar.dao.TrendBarDao;
import com.alagert.java.tradebar.model.PeriodType;
import com.alagert.java.tradebar.model.Quote;
import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.model.TrendBar;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Andrey Tsvetkov
 */
public class TrendComposer implements Runnable {

    private TrendBarDao trendBarDao;

    private final Object mutex = new Object();

    private final TrendBar minuteBar;
    private final TrendBar hourBar;
    private final TrendBar dayBar;


    private final BlockingQueue<Quote> quotes;
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3);

    public TrendComposer(BlockingQueue<Quote> quotes, TrendBarDao trendBarDao, Symbol symbol) {
        this.quotes = quotes;
        this.trendBarDao = trendBarDao;

        minuteBar = new TrendBar(PeriodType.M1, symbol);
        hourBar = new TrendBar(PeriodType.H1, symbol);
        dayBar = new TrendBar(PeriodType.D1, symbol);

        executorService.scheduleWithFixedDelay(new TrendSaver(minuteBar), minuteBar.getPeriodType().getDelay(),
                minuteBar.getPeriodType().getDelay(), minuteBar.getPeriodType().getTimeUnit());
        executorService.scheduleWithFixedDelay(new TrendSaver(hourBar), hourBar.getPeriodType().getDelay(),
                hourBar.getPeriodType().getDelay(), hourBar.getPeriodType().getTimeUnit());
        executorService.scheduleWithFixedDelay(new TrendSaver(dayBar), dayBar.getPeriodType().getDelay(),
                dayBar.getPeriodType().getDelay(), dayBar.getPeriodType().getTimeUnit());
    }

    @Override
    public void run() {
        Quote newQuote;
        try {
            while ((newQuote = quotes.poll(200, TimeUnit.MILLISECONDS)) != null) {
                synchronized (mutex) {
                    updateTrend(minuteBar, newQuote);
                    updateTrend(hourBar, newQuote);
                    updateTrend(dayBar, newQuote);
                }
            }
            executorService.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void updateTrend(TrendBar bar, Quote quote) {
        double quotePrice = quote.getPrice();
        if (bar.getTimeStamp() < 0) {
            bar.setOpenPrice(quotePrice);
            bar.setTimeStamp(1);
        } else {
            bar.setClosePrice(quotePrice);
        }
        if (quotePrice < bar.getLowPrice()) {
            bar.setLowPrice(quotePrice);
        }
        if (quotePrice > bar.getHighPrice()) {
            bar.setHighPrice(quotePrice);
        }
    }

    private class TrendSaver implements Runnable {
        private final TrendBar trendBar;

        private TrendSaver(TrendBar trendBar) {
            this.trendBar = trendBar;
        }

        @Override
        public void run() {
            synchronized (mutex) {
                trendBar.setTimeStamp(System.currentTimeMillis());
                System.out.println(trendBar.toString());
                trendBarDao.addTrendBar(trendBar);
                trendBar.setClosePrice(0.0);
                trendBar.setLowPrice(Double.MAX_VALUE);
                trendBar.setHighPrice(Double.MIN_VALUE);
                trendBar.setTimeStamp(-1);
            }
        }
    }
}
