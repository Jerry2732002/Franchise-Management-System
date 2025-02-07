package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Purchases;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseRowMapper implements RowMapper<Purchases> {
    @Override
    public Purchases mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Purchases(
                rs.getInt("purchase_id"),
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getInt("quantity"),
                rs.getString("location"),
                rs.getString("building_name"),
                rs.getString("user_id"),
                rs.getString("user_name"),
                rs.getDate("date_of_purchase")
                );
    }
}
