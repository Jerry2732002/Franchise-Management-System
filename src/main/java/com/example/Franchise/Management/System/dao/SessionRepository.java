package com.example.Franchise.Management.System.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;

    public SessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addSession(String userId, String sessionId) {
        String sql = "REPLACE INTO sessions VALUES (?,?)";

        int rowsAffected = jdbcTemplate.update(sql, userId, sessionId);

        return rowsAffected > 0;
    }

}