package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Report;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyReportRowMapper implements RowMapper<Report> {
    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Report(
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
