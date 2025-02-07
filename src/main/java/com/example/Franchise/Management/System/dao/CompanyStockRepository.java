package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.CompanyStock;
import com.example.Franchise.Management.System.rowmapper.CompanyStockRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyStockRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyStockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addOrUpdateCompanyStock(CompanyStock stock) {
        String sql = "REPLACE INTO company_stocks VALUES (?,?)";

        int rowsAffected = jdbcTemplate.update(sql, stock.getProductId(), stock.getQuantity());

        return rowsAffected == 1;
    }

    public CompanyStock getCompanyStockById(int productId) {
        String sql = "SELECT * FROM company_stocks cs JOIN products p ON p.product_id = cs.product_id WHERE product_id = ?";

        return jdbcTemplate.queryForObject(sql, new CompanyStockRowMapper(), productId);
    }

    public List<CompanyStock> getAllCompanyStock() {
        String sql = "SELECT * FROM company_stocks cs JOIN products p ON p.product_id = cs.product_id";

        return jdbcTemplate.query(sql, new CompanyStockRowMapper());
    }
}
