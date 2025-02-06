package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Product;
import com.example.Franchise.Management.System.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (product_name, product_company, wholesale_price, distributor_price, retail_price) VALUE (?,?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql,product.getProductName(),product.getProductCompany(),product.getWholesalePrice(), product.getDistributorPrice(), product.getRetailPrice());

        return rowsAffected == 1;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";

        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), productId);
    }
}
