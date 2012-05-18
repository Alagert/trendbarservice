package com.alagert.java.tradebar.service.impl;

import com.alagert.java.tradebar.model.Quote;
import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.service.QuoteProvider;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author Andrey Tsvetkov
 */
public class QuoteProviderImpl implements QuoteProvider, Runnable {

    private final BlockingQueue<Quote> quotes;

    private final Random rnd;

    public QuoteProviderImpl(BlockingQueue<Quote> quotes) {
        this.quotes = quotes;
        this.rnd = new Random(System.currentTimeMillis());
    }

    @Override
    public void generateQuotes() throws InterruptedException {
        Quote q = new Quote(Symbol.EURUSD, rnd.nextDouble() * 100, System.currentTimeMillis());
        quotes.put(q);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                generateQuotes();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("QuoteProvider was interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
