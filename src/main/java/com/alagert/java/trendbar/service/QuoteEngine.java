package com.alagert.java.trendbar.service;

import com.alagert.java.trendbar.model.PeriodType;
import com.alagert.java.trendbar.model.Quote;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;
import com.alagert.java.trendbar.service.exception.ProviderAlreadyRegisteredException;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;

/**
 * @author Andrey Tsvetkov
 */
public interface QuoteEngine {

    BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException;

    Collection<TrendBar> getAllBars(Symbol symbol, PeriodType periodType);

    Collection<TrendBar> getBars(Symbol symbol, PeriodType periodType, long from, long to);
}
