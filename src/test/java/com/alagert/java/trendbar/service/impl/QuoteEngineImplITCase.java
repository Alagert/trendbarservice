package com.alagert.java.trendbar.service.impl;

import com.alagert.java.trendbar.dao.TrendBarDao;
import com.alagert.java.trendbar.model.PeriodType;
import com.alagert.java.trendbar.model.Quote;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;
import com.alagert.java.trendbar.service.QuoteProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Andrey Tsvetkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class QuoteEngineImplITCase {

    private final Quote dummy = new Quote(Symbol.EURUSD, 0.0, System.currentTimeMillis());

    @Autowired
    private TrendBarDao trendBarDao;

    @Autowired
    private QuoteEngineImpl quoteEngine;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    @Transactional
    public void testFullCycle() throws Exception {
        QuoteProvider provider = new DummyQuoteProvider(quoteEngine.registerProvider(Symbol.EURUSD));
        executorService.submit((Runnable) quoteEngine);
        executorService.submit((Runnable) provider);

        long nTrials = 2;
        PeriodType.M1.getTimeUnit().sleep(nTrials * PeriodType.M1.getDelay() + 1);

        executorService.shutdown();

        Collection<TrendBar> allBars = trendBarDao.getAllBars(Symbol.EURUSD, PeriodType.M1);
        assertNotNull(allBars);
        assertEquals(nTrials, allBars.size());
        for (TrendBar trendBar : allBars) {
            assertEquals(dummy.getPrice(), trendBar.getOpenPrice());
            assertEquals(dummy.getPrice(), trendBar.getClosePrice());
            assertEquals(dummy.getPrice(), trendBar.getLowPrice());
            assertEquals(dummy.getPrice(), trendBar.getHighPrice());
            assertTrue(System.currentTimeMillis() > trendBar.getTimestamp());
        }

        Collection<TrendBar> hoursBars = trendBarDao.getAllBars(Symbol.EURUSD, PeriodType.H1);
        assertTrue(hoursBars.isEmpty());
    }

    private final class DummyQuoteProvider implements QuoteProvider, Runnable {

        private final BlockingQueue<Quote> quotes;

        private DummyQuoteProvider(BlockingQueue<Quote> quotes) {
            this.quotes = quotes;
        }

        @Override
        public void generateQuotes() throws InterruptedException {
            quotes.put(dummy);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    generateQuotes();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
