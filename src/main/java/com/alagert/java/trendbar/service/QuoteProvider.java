package com.alagert.java.trendbar.service;

/**
 * @author Andrey Tsvetkov
 */
public interface QuoteProvider {

    void generateQuotes() throws InterruptedException;
}
