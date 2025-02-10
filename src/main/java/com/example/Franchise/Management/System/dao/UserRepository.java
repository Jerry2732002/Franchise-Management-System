package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.rowmapper.AdminRowMapper;
import com.example.Franchise.Management.System.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users VALUES (?,?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, user.getUserId(), user.getName(), user.getPassword(), user.getFranchiseId(), user.getRole().name());

        return rowsAffected == 1;
    }

    public boolean deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        int rowsAffected = jdbcTemplate.update(sql, userId);

        return rowsAffected == 1;
    }

    public User getUserById(String userId) {
        String sql = "SELECT * FROM users u JOIN franchises f ON f.franchise_id = u.franchise_id WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public User getSuperAdminById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new AdminRowMapper(), userId);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";

        return jdbcTemplate.query(sql, new UserRowMapper());
    }
}
