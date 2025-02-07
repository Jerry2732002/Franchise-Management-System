package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Purchases;
import com.example.Franchise.Management.System.rowmapper.PurchaseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class PurchaseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PurchaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addPurchase(Purchases purchase) {
        String sql = "INSERT INTO purchases (product_id, user_id, quantity, date_of_purchase) VALUES (?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, purchase.getProductId(), purchase.getUserId(), purchase.getQuantity(), purchase.getDateOfPurchase());

        return rowsAffected == 1;
    }

    public List<Purchases> getAllPurchases() {
        String sql = "SELECT * FROM purchases p JOIN products pr ON p.product_id = pr.product_id JOIN users u ON u.user_id = p.user_id";

        return jdbcTemplate.query(sql, new PurchaseRowMapper());
    }

    public List<Purchases> getPurchases(Date startDate, Date endDate) {
        String sql = "SELECT * FROM purchases p JOIN products pr ON p.product_id = pr.product_id JOIN users u ON u.user_id = p.user_id WHERE date_of_purchase BETWEEN ? AND ?";

        return jdbcTemplate.query(sql, new PurchaseRowMapper(), startDate, endDate);
    }
}
