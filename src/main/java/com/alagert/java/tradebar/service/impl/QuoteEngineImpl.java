package com.alagert.java.tradebar.service.impl;

import com.alagert.java.tradebar.dao.TrendBarDao;
import com.alagert.java.tradebar.model.Quote;
import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.service.QuoteEngine;
import com.alagert.java.tradebar.service.exception.ProviderAlreadyRegisteredException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Andrey Tsvetkov
 */
public class QuoteEngineImpl implements QuoteEngine {

    private TrendBarDao trendBarDao;

    public void setTrendBarDao(TrendBarDao trendBarDao) {
        this.trendBarDao = trendBarDao;
    }

    private final ConcurrentHashMap<Symbol, BlockingQueue<Quote>> providers = new ConcurrentHashMap<Symbol, BlockingQueue<Quote>>();

    @Override
    public BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException {
        if (providers.containsKey(symbol)) {
            throw new ProviderAlreadyRegisteredException();
        }
        BlockingQueue<Quote> quotes = new LinkedBlockingQueue<Quote>();
        providers.put(symbol, quotes);

        new Thread(new TrendComposer(quotes, trendBarDao, symbol)).start();
        return quotes;
    }


}