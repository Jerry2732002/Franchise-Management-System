package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Request;
import com.example.Franchise.Management.System.enums.Status;
import com.example.Franchise.Management.System.rowmapper.RequestRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addRequest(Request request) {
        String sql = "INSERT INTO requests (franchise_id, product_id, quantity, status) VALUES (?,?,?,?)";

        int rowAffected = jdbcTemplate.update(sql, request.getFranchiseId(),request.getProductId(),request.getQuantity(), request.getStatus().name());

        return rowAffected == 1;
    }

    public List<Request> getAllRequest() {
        String sql = "SELECT * FROM requests r JOIN franchises f ON r.franchise_id = f.franchise_id JOIN products p ON r.product_id = p.product_id";

        return jdbcTemplate.query(sql, new RequestRowMapper());
    }

    public Request getRequestById(int requestId) {
        String sql = "SELECT * FROM requests r JOIN franchises f ON r.franchise_id = f.franchise_id JOIN products p ON r.product_id = p.product_id WHERE request_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new RequestRowMapper(), requestId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getPendingRequest() {
        String sql = "SELECT * FROM requests r JOIN franchises f ON r.franchise_id = f.franchise_id JOIN products p ON r.product_id = p.product_id WHERE status = 'PENDING'";

        return jdbcTemplate.query(sql, new RequestRowMapper());
    }

    public boolean updateRequest(int requestId, Status status) {
        String sql = "UPDATE requests SET status = ? WHERE request_id = ?";

        int rowsAffected = jdbcTemplate.update(sql, status.name(), requestId);

        return rowsAffected == 1;
    }
}
