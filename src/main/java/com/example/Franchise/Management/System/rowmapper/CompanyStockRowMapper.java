package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.CompanyStock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyStockRowMapper implements RowMapper<CompanyStock> {
    @Override
    public CompanyStock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CompanyStock(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getInt("quantity")
        );
    }
}
