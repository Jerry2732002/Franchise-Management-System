package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.CompanyPurchase;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyPurchaseRowMapper implements RowMapper<CompanyPurchase> {
    @Override
    public CompanyPurchase mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CompanyPurchase(
                rs.getInt("company_purchase_id"),
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getInt("quantity"),
                rs.getDate("date_of_purchase"),
                rs.getDouble("wholesale_price"),
                rs.getDouble("distributor_price")
        );
    }
}
