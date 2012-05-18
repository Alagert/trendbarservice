package com.alagert.java.trendbar.service.impl;

import com.alagert.java.trendbar.dao.TrendBarDao;
import com.alagert.java.trendbar.model.Quote;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;
import com.alagert.java.trendbar.service.QuoteEngine;
import com.alagert.java.trendbar.service.exception.ProviderAlreadyRegisteredException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Andrey Tsvetkov
 */
public class QuoteEngineImpl implements QuoteEngine, Runnable {

    private TrendBarDao trendBarDao;

    public void setTrendBarDao(TrendBarDao trendBarDao) {
        this.trendBarDao = trendBarDao;
    }

    private final ConcurrentHashMap<Symbol, BlockingQueue<Quote>> providers = new ConcurrentHashMap<Symbol, BlockingQueue<Quote>>();
    private final BlockingQueue<TrendBar> doneBars = new LinkedBlockingQueue<TrendBar>();

    @Override
    public BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException {
        if (providers.containsKey(symbol)) {
            throw new ProviderAlreadyRegisteredException();
        }
        BlockingQueue<Quote> quotes = new LinkedBlockingQueue<Quote>();
        providers.put(symbol, quotes);

        new Thread(new TrendComposer(quotes, doneBars, symbol)).start();
        return quotes;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TrendBar bar = doneBars.take();
                System.out.println(bar);
                trendBarDao.addTrendBar(bar);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}