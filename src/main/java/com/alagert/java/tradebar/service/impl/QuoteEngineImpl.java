package com.alagert.java.tradebar.service.impl;

import com.alagert.java.tradebar.model.Quote;

import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrey Tsvetkov
 */
public class QuoteEngineImpl implements Runnable {
    private final BlockingQueue<Quote> quotes;
    private final AtomicInteger counter;

    private final TreeSet<Double> minuteSet = new TreeSet<Double>();


    public QuoteEngineImpl(BlockingQueue<Quote> quotes) {
        this.quotes = quotes;
        counter = new AtomicInteger(0);
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        service.scheduleWithFixedDelay(new TrendBarMaintainer(), 5, 5, TimeUnit.SECONDS);
    }

    public void consumeQuotes() throws InterruptedException {
        Quote quoteFromQueue = null;

        while ((quoteFromQueue = quotes.poll(200, TimeUnit.MILLISECONDS)) != null) {

            synchronized (minuteSet) {
                minuteSet.add(quoteFromQueue.getPrice());
            }

            System.out.println("Counter=" + counter.getAndIncrement() + " " + quoteFromQueue.toString());
        }

    }

    @Override
    public void run() {
        try {
            consumeQuotes();
        } catch (InterruptedException e) {
            System.out.println("Consumer interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private class TrendBarMaintainer implements Runnable {



        @Override
        public void run() {
            synchronized (minuteSet) {
                System.out.println("minPrice = " + minuteSet.first() + " highPrice" + minuteSet.last());
                minuteSet.clear();
            }
        }
    }
}
