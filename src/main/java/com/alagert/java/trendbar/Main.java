package com.alagert.java.trendbar;

import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.service.QuoteEngine;
import com.alagert.java.trendbar.service.impl.QuoteProviderImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Andrey Tsvetkov
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationContext.registerShutdownHook();

        QuoteEngine quoteEngine = (QuoteEngine) applicationContext.getBean("quoteEngine");//new QuoteEngineImpl();


        QuoteProviderImpl euroProvider = new QuoteProviderImpl(quoteEngine.registerProvider(Symbol.EURUSD));
        QuoteProviderImpl jpyProvider = new QuoteProviderImpl(quoteEngine.registerProvider(Symbol.EURJPY));

        Thread engine = new Thread((Runnable) quoteEngine);

        Thread providerThread = new Thread(euroProvider);
        Thread jpyThread = new Thread(jpyProvider);

        synchronized (Main.class) {
            providerThread.start();
            jpyThread.start();
            engine.start();
        }

        Thread.sleep(80000);
        providerThread.interrupt();

        Thread.sleep(30000);
        jpyThread.interrupt();
        engine.interrupt();
    }
}
