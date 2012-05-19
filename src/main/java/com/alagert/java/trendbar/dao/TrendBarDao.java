package com.alagert.java.trendbar.dao;

import com.alagert.java.trendbar.model.PeriodType;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;

import java.util.Collection;

/**
 * @author Andrey Tsvetkov
 */
public interface TrendBarDao {
    void addTrendBar(TrendBar trendBar);

    Collection<TrendBar> getTrendBars(Symbol symbol, PeriodType periodType, long from, long to);

    Collection<TrendBar> getAllBars(Symbol symbol, PeriodType periodType);

}