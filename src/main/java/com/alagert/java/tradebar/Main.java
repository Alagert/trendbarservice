package com.alagert.java.tradebar;

import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.service.impl.QuoteEngineImpl;
import com.alagert.java.tradebar.service.impl.QuoteProviderImpl;

/**
 * @author Andrey Tsvetkov
 */
public class Main {


    public static void main(String[] args) throws Exception {


        QuoteEngineImpl quoteEngine = new QuoteEngineImpl();

        QuoteProviderImpl quoteProvider = new QuoteProviderImpl(quoteEngine.registerProvider(Symbol.EURUSD));

        Thread providerThread = new Thread(quoteProvider);

        synchronized (Main.class) {
            providerThread.start();
        }

        Thread.sleep(200000);

        quoteProvider.flag = true;

    }
}
