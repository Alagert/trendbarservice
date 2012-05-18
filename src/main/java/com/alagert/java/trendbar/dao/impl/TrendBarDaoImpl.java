package com.alagert.java.trendbar.dao.impl;

import com.alagert.java.trendbar.dao.TrendBarDao;
import com.alagert.java.trendbar.model.PeriodType;
import com.alagert.java.trendbar.model.Symbol;
import com.alagert.java.trendbar.model.TrendBar;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Andrey Tsvetkov
 */
public class TrendBarDaoImpl extends JdbcDaoSupport implements TrendBarDao {

    private final static TrendBarRowMapper TREND_BAR_ROW_MAPPER = new TrendBarRowMapper();

    @Override
    public void addTrendBar(TrendBar trendBar) {
        String sql = "INSERT INTO Public.TrendBar (open_price, low_price, high_price, close_price, period_type, symbol, creation_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql, trendBar.getOpenPrice(), trendBar.getLowPrice(), trendBar.getHighPrice(),
                trendBar.getClosePrice(),
                trendBar.getPeriodType().name(), trendBar.getSymbol().name(), trendBar.getTimeStamp());
    }


    @Override
    public Collection<TrendBar> getTrendBars(Symbol symbol, long from, long to) {
        String sql = "SELECT open_price, low_price, high_price, close_price, period_type, symbol, creation_time FROM TrendBar " +
                "WHERE creation_time >= ? AND creation_time <= ? and symbol = ?";

        return getJdbcTemplate().query(sql, TREND_BAR_ROW_MAPPER, from, to, symbol.name());
    }

    private static class TrendBarRowMapper implements RowMapper<TrendBar> {
        //SELECT open_price, low_price, high_price, close_price, period_type, symbol, creation_time
        @Override
        public TrendBar mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrendBar trendBar = new TrendBar(PeriodType.valueOf(rs.getString(5)), Symbol.valueOf(rs.getString(6)));

            trendBar.setOpenPrice(rs.getDouble(1));
            trendBar.setLowPrice(rs.getDouble(2));
            trendBar.setClosePrice(rs.getDouble(3));
            trendBar.setHighPrice(rs.getDouble(4));

            return trendBar;
        }
    }
}
