package com.alagert.java.tradebar.service;

import com.alagert.java.tradebar.model.Quote;

/**
 * @author Andrey Tsvetkov
 */
public interface QuoteProvider {

    Quote getQuote();

    void generateQuotes() throws InterruptedException;

}
