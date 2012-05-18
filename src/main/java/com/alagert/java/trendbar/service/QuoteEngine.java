package com.alagert.java.trendbar.service;

import java.util.concurrent.BlockingQueue;

import com.alagert.java.trendbar.model.Quote;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.service.exception.ProviderAlreadyRegisteredException;

/**
 * @author Andrey Tsvetkov
 */
public interface QuoteEngine {

    BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException;
}
