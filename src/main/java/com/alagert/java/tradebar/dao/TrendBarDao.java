package com.alagert.java.tradebar.dao;

import com.alagert.java.tradebar.model.Symbol;
import com.alagert.java.tradebar.model.TrendBar;

import java.util.Collection;

/**
 * @author Andrey Tsvetkov
 */
public interface TrendBarDao {
    void addTrendBar(TrendBar trendBar);

    Collection<TrendBar> getTrendBars(Symbol symbol, long from, long to);

}