package com.example.Franchise.Management.System.rowmapper;

import com.example.Franchise.Management.System.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("user_id"),
                rs.getString("user_name"),
                rs.getString("hashed_password"),
                -1,
                null,
                rs.getString("role")
        );
    }
}
