package com.alagert.java.tradebar.service;

import java.util.concurrent.BlockingQueue;

import com.alagert.java.tradebar.model.Quote;
import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.service.exception.ProviderAlreadyRegisteredException;

/**
 * @author Andrey Tsvetkov
 */
public interface QuoteEngine {

    BlockingQueue<Quote> registerProvider(Symbol symbol) throws ProviderAlreadyRegisteredException;
}
