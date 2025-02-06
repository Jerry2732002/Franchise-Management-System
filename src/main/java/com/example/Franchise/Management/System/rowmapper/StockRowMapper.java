package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Stock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockRowMapper implements RowMapper<Stock> {
    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Stock( rs.getInt(""));
    }
}
