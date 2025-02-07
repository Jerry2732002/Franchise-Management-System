package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Supply;
import com.example.Franchise.Management.System.rowmapper.PurchaseRowMapper;
import com.example.Franchise.Management.System.rowmapper.SupplyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class SupplyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SupplyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addSupply(Supply supply) {
        String sql = "INSERT INTO supply (franchise_id, product_id, quantity, date_of_supply) VALUES (?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql,supply.getFranchiseId(),supply.getProductId(),supply.getQuantity(),supply.getDateOfSupply());

        return rowsAffected == 1;
    }

    public List<Supply> getAllSupply() {
        String sql = "SELECT * FROM supply s JOIN products p ON s.product_id = p.product_id JOIN franchises f ON f.franchise_id = s.franchise_id";

        return jdbcTemplate.query(sql, new SupplyRowMapper());
    }

    public List<Supply> getSupply(Date startDate, Date endDate) {
        String sql = "SELECT * FROM supply s JOIN products p ON s.product_id = p.product_id JOIN franchises f ON f.franchise_id = s.franchise_id WHERE date_of_supply BETWEEN ? AND ?";

        return jdbcTemplate.query(sql, new SupplyRowMapper());
    }
}
