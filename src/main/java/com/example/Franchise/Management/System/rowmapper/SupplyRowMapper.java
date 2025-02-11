package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Supply;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplyRowMapper implements RowMapper<Supply> {
    @Override
    public Supply mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Supply(
                rs.getInt("supply_id"),
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getInt("quantity"),
                rs.getInt("franchise_id"),
                rs.getString("location"),
                rs.getString("building_name"),
                rs.getDate("date_of_supply"),
                rs.getDouble("distributor_price"),
                rs.getDouble("total_price")
                );
    }
}
