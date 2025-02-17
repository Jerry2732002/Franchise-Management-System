package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Stock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockRowMapper implements RowMapper<Stock> {
    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Stock(
                rs.getInt("franchise_id"),
                rs.getString("location"),
                rs.getString("building_name"),
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getInt("quantity")
        );
    }
}