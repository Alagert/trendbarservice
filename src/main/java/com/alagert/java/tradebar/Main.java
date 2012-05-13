package com.alagert.java.tradebar;

import com.alagert.java.tradebar.model.Quote;
import com.alagert.java.tradebar.service.impl.QuoteEngineImpl;
import com.alagert.java.tradebar.service.impl.QuoteProviderImpl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Andrey Tsvetkov
 */
public class Main {


    public static void main(String[] args) throws Exception {


        BlockingQueue<Quote> quotes = new LinkedBlockingQueue<Quote>();

        QuoteProviderImpl quoteProvider = new QuoteProviderImpl(quotes);
        QuoteEngineImpl quoteEngine = new QuoteEngineImpl(quotes);

        Thread providerThread = new Thread(quoteProvider);
        Thread engineThread = new Thread(quoteEngine);

        synchronized (Main.class) {
            providerThread.start();
            engineThread.start();

        }

        Thread.sleep(200000);

        quoteProvider.flag = true;

    }
}
