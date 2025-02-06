package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Franchise;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FranchiseRowMapper implements RowMapper<Franchise> {

    @Override
    public Franchise mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Franchise(rs.getInt("franchise_id"),
                rs.getString("location"),
                rs.getString("building_name")
        );
    }
}
