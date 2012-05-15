package com.alagert.java.tradebar.service.impl;

import com.alagert.java.tradebar.model.Quote;
import com.alagert.java.tradebar.model.Symbol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Andrey Tsvetkov
 */
public class QuoteEngineImpl implements Runnable {
    private final AtomicInteger counter = new AtomicInteger(0);

    private final ConcurrentHashMap<Symbol, BlockingQueue<Quote>> providers = new ConcurrentHashMap<Symbol, BlockingQueue<Quote>>();

    AtomicReference<Double> currentMin = new AtomicReference<Double>(0.0);
    AtomicReference<Double> currentMax = new AtomicReference<Double>(0.0);
    AtomicReference<Double> close = new AtomicReference<Double>(0.0);

    public BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException {
        if (providers.containsKey(symbol)) {
            throw new ProviderAlreadyRegisteredException();
        }

        return providers.put(symbol, new LinkedBlockingQueue<Quote>());
    }

    public void consumeQuotes() throws InterruptedException {
        BlockingQueue<Quote> euroQuote = providers.get(Symbol.EURUSD);
        Quote quoteFromQueue = null;

        while ((quoteFromQueue = euroQuote.poll(200, TimeUnit.MILLISECONDS)) != null) {
            double price = quoteFromQueue.getPrice();
            close.getAndSet(price);
            double min = currentMin.get();
            if (price < min) {
                currentMin.compareAndSet(min, price);
            }
            double max = currentMax.get();
            if (price > max) {
                currentMax.compareAndSet(max, price);
            }
            counter.getAndIncrement();
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
}