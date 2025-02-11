package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Stock;
import com.example.Franchise.Management.System.rowmapper.StockRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addOrUpdateStock(Stock stock) {
        String sql = "REPLACE INTO stocks VALUES (?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, stock.getFranchiseId(),stock.getProductId(),stock.getQuantity());

        return rowsAffected > 1;
    }

    public Stock getStockById(int productId, int franchiseId) {
        String sql = "SELECT * FROM stocks s JOIN products p ON p.product_id = s.product_id JOIN franchises f ON s.franchise_id = f.franchise_id WHERE s.product_id = ? AND s.franchise_id = ?";

        return jdbcTemplate.queryForObject(sql, new StockRowMapper(), productId, franchiseId);
    }

    public List<Stock> getAllStock(int franchiseId) {
        String sql = "SELECT * FROM stocks s JOIN products p ON p.product_id = s.product_id JOIN franchises f ON s.franchise_id = f.franchise_id WHERE s.franchise_id = ?";

        return jdbcTemplate.query(sql, new StockRowMapper(), franchiseId);
    }

}
