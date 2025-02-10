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

        int rowsAffected = jdbcTemplate.update(sql, sessionId, userId);

        return rowsAffected > 0;
    }

    public String getSessionByUserId(String userId) {
        String sql = "SELECT session_id FROM sessions WHERE user_id = ?";
        String sessionId;
        try {
            sessionId = jdbcTemplate.queryForObject(sql, String.class, userId);
        } catch (EmptyResultDataAccessException e) {
            sessionId = null;
        }
        return sessionId;
    }

}