package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Franchise;
import com.example.Franchise.Management.System.rowmapper.FranchiseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FranchiseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FranchiseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addFranchise(Franchise franchise) {
        String sql = "INSERT INTO franchises (location, building_name) values (?,?)";

        int rowsAffected = jdbcTemplate.update(sql, franchise.getLocation(), franchise.getBuildingName());

        return rowsAffected == 1;
    }

    public boolean deleteFranchise(int franchiseId) {
        String sql = "DELETE FROM franchises WHERE franchise_id = ?";

        int rowsAffected = jdbcTemplate.update(sql, franchiseId);

        return rowsAffected == 1;
    }

    public List<Franchise> getAllFranchise() {
        String sql = "SELECT * FROM franchises";

        return jdbcTemplate.query(sql, new FranchiseRowMapper());
    }



}
