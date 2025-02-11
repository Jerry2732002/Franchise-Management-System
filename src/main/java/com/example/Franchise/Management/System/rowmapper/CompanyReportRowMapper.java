package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.CompanyReport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyReportRowMapper implements RowMapper<CompanyReport> {
    @Override
    public CompanyReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CompanyReport(
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getInt("quantity"),
                rs.getDate("supply_purchase_date"),
                rs.getDouble("price"),
                rs.getDouble("total_price"),
                null
        );
    }
}
