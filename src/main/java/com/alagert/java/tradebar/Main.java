package com.alagert.java.tradebar;

import com.alagert.java.tradebar.dao.TrendBarDao;
import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.service.impl.QuoteEngineImpl;
import com.alagert.java.tradebar.service.impl.QuoteProviderImpl;
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
        TrendBarDao trendBarDao = (TrendBarDao) applicationContext.getBean("trendBarDao");

        QuoteEngineImpl quoteEngine = new QuoteEngineImpl();
        quoteEngine.setTrendBarDao(trendBarDao);


        QuoteProviderImpl euroProvider = new QuoteProviderImpl(quoteEngine.registerProvider(Symbol.EURUSD));
        QuoteProviderImpl jpyProvider = new QuoteProviderImpl(quoteEngine.registerProvider(Symbol.EURJPY));

        Thread providerThread = new Thread(euroProvider);
        Thread jpyThread = new Thread(jpyProvider);

        synchronized (Main.class) {
            providerThread.start();
            jpyThread.start();
        }

        Thread.sleep(80000);
        providerThread.interrupt();

        Thread.sleep(30000);
        jpyThread.interrupt();
    }
}
