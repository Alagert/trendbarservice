package com.alagert.java.trendbar.service.impl;

import com.alagert.java.trendbar.dao.TrendBarDao;
import com.alagert.java.trendbar.model.PeriodType;
import com.alagert.java.trendbar.model.Quote;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;
import com.alagert.java.trendbar.service.exception.ProviderAlreadyRegisteredException;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

/**
 * @author Andrey Tsvetkov
 */
public class QuoteEngineImplTest {
    private TrendBarDao trendBarDaoMock;
    private QuoteEngineImpl quoteEngine;

    @Before
    public void setUp() {
        trendBarDaoMock = EasyMock.createMock(TrendBarDao.class);
        quoteEngine = new QuoteEngineImpl();
        quoteEngine.setTrendBarDao(trendBarDaoMock);
    }

    @Test
    public void testRegisterProvider() throws Exception {
        BlockingQueue<Quote> eurQuotes = quoteEngine.registerProvider(Symbol.EURUSD);
        assertNotNull(eurQuotes);
        BlockingQueue<Quote> jpyQuotes = quoteEngine.registerProvider(Symbol.EURJPY);
        assertNotNull(jpyQuotes);
        assertNotSame(eurQuotes, jpyQuotes);
    }

    @Test(expected = ProviderAlreadyRegisteredException.class)
    public void testAlreadyRegisteredProvider() throws Exception {
        quoteEngine.registerProvider(Symbol.EURUSD);
        quoteEngine.registerProvider(Symbol.EURUSD);
    }


    @Test
    public void testGetAllBars() throws Exception {
        EasyMock.expect(trendBarDaoMock.getAllBars(Symbol.EURUSD, PeriodType.M1)).andReturn(Collections.<TrendBar>emptyList());
        EasyMock.replay(trendBarDaoMock);
        Collection<TrendBar> allBars = quoteEngine.getAllBars(Symbol.EURUSD, PeriodType.M1);
        assertNotNull(allBars);
        assertTrue(allBars.isEmpty());
        EasyMock.verify(trendBarDaoMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBarsWithWrongPeriod() throws Exception {
        quoteEngine.getBars(Symbol.EURUSD, null, 1233, 12);
    }

    @Test
    public void testGetBars() throws Exception {
        EasyMock.expect(trendBarDaoMock.getTrendBars(Symbol.EURUSD, PeriodType.M1, 10, 20)).andReturn(Collections.<TrendBar>emptyList());
        EasyMock.replay(trendBarDaoMock);
        Collection<TrendBar> bars = quoteEngine.getBars(Symbol.EURUSD, PeriodType.M1, 10, 20);
        assertNotNull(bars);
        assertTrue(bars.isEmpty());
        EasyMock.verify(trendBarDaoMock);
    }
}
