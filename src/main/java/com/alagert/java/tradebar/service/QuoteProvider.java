package com.alagert.java.tradebar.service;

/**
 * @author Andrey Tsvetkov
 */
public interface QuoteProvider {

    void generateQuotes() throws InterruptedException;
}
