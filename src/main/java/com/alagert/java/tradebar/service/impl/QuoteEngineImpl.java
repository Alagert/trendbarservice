package com.alagert.java.tradebar.service.impl;

import com.alagert.java.tradebar.model.Quote;
import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.service.exception.ProviderAlreadyRegisteredException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrey Tsvetkov
 */
public class QuoteEngineImpl {
    private final AtomicInteger counter = new AtomicInteger(0);

    private final ConcurrentHashMap<Symbol, BlockingQueue<Quote>> providers = new ConcurrentHashMap<Symbol, BlockingQueue<Quote>>();

    public BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException {
        if (providers.containsKey(symbol)) {
            throw new ProviderAlreadyRegisteredException();
        }
        BlockingQueue<Quote> quotes = new LinkedBlockingQueue<Quote>();
        providers.put(symbol, quotes);

        new Thread(new TrendComposer(quotes)).start();
        return quotes;

    }


}