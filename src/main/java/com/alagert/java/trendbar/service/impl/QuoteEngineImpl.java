package com.alagert.java.trendbar.service.impl;

import com.alagert.java.trendbar.dao.TrendBarDao;
import com.alagert.java.trendbar.model.PeriodType;
import com.alagert.java.trendbar.model.Quote;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;
import com.alagert.java.trendbar.service.QuoteEngine;
import com.alagert.java.trendbar.service.exception.ProviderAlreadyRegisteredException;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private final ExecutorService executorService = Executors.newFixedThreadPool(Symbol.size());

    @Override
    public BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException {
        if (providers.containsKey(symbol)) {
            throw new ProviderAlreadyRegisteredException();
        }
        BlockingQueue<Quote> quotes = new LinkedBlockingQueue<Quote>();
        providers.put(symbol, quotes);

        executorService.execute(new TrendComposer(quotes, doneBars, symbol));
        return quotes;
    }

    @Override
    public Collection<TrendBar> getAllBars(Symbol symbol, PeriodType periodType) {
        Collection<TrendBar> allBars = trendBarDao.getAllBars(symbol, periodType);
        return returnCollection(allBars);
    }

    @Override
    public Collection<TrendBar> getBars(Symbol symbol, PeriodType periodType, long from, long to) {
        if (to < from) {
            throw new IllegalArgumentException("To can not be earlier from");
        }
        Collection<TrendBar> trendBars = trendBarDao.getTrendBars(symbol, periodType, from, to);
        return returnCollection(trendBars);
    }

    private Collection<TrendBar> returnCollection(Collection<TrendBar> trendBars) {
        if (trendBars.isEmpty()) {
            return Collections.emptyList();
        }
        return trendBars;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TrendBar bar = doneBars.take();
                trendBarDao.addTrendBar(bar);
            } catch (InterruptedException e) {
                executorService.shutdown();
                Thread.currentThread().interrupt();
            }
        }
    }
}