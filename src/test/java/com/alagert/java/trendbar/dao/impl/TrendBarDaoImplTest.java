package com.alagert.java.trendbar.dao.impl;

import com.alagert.java.trendbar.dao.TrendBarDao;
import com.alagert.java.trendbar.model.PeriodType;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Collection;

import static junit.framework.Assert.assertFalse;

/**
 * @author Andrey Tsvetkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class TrendBarDaoImplTest {

    @Autowired
    private TrendBarDao trendBarDao;

    @Test
    public void testAddTrendBar() throws Exception {

        TrendBar tb = new TrendBar(PeriodType.M1, Symbol.EURUSD);
        tb.setClosePrice(11.1);
        tb.setHighPrice(23.1);
        tb.setOpenPrice(12.1);
        tb.setLowPrice(2.2);
        tb.setClosePrice(System.currentTimeMillis());

        trendBarDao.addTrendBar(tb);

        Collection<TrendBar> tbs = trendBarDao.getTrendBars(Symbol.EURUSD, 0, Long.MAX_VALUE);
        assertFalse(tbs.isEmpty());
    }
}
