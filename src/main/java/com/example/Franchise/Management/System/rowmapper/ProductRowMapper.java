package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getDouble("wholesale_price"),
                rs.getDouble("distributor_price"),
                rs.getDouble("retail_price")
        );
    }
}
