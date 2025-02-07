package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.Request;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RequestRowMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Request(
                rs.getInt("request_id"),
                rs.getInt("franchise_id"),
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_company"),
                rs.getString("location"),
                rs.getString("building_name"),
                rs.getInt("quantity"),
                rs.getString("status")
        );
    }
}
