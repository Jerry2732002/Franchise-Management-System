package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.CompanyPurchase;
import com.example.Franchise.Management.System.rowmapper.CompanyPurchaseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class CompanyPurchaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyPurchaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addPurchase(CompanyPurchase purchase) {
        String sql = "INSERT INTO company_purchases (product_id, quantity, date_of_purchase) VALUES (?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, purchase.getProductId(), purchase.getQuantity(), purchase.getDateOfPurchase());

        return rowsAffected == 1;
    }

    public List<CompanyPurchase> getAllCompanyPurchase() {
        String sql = "SELECT * FROM company_purchases cp JOIN products p ON cp.product_id = p.product_id";

        return jdbcTemplate.query(sql, new CompanyPurchaseRowMapper());
    }

    public List<CompanyPurchase> getCompanyPurchase(Date startDate, Date endDate) {
        String sql = "SELECT * FROM company_purchases cp JOIN products p ON cp.product_id = p.product_id WHERE date_of_purchase BETWEEN ? AND ?";

        return jdbcTemplate.query(sql, new CompanyPurchaseRowMapper(), startDate, endDate);
    }
}
