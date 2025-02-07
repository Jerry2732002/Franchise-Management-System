package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Request;
import com.example.Franchise.Management.System.enums.Status;
import com.example.Franchise.Management.System.rowmapper.RequestRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        String sql = "INSERT INTO requests (franchise_id, product_id, quantity, status)";

        int rowAffected = jdbcTemplate.update(sql, request.getFranchiseId(),request.getProductId(),request.getQuantity(), request.getStatus());

        return rowAffected == 1;
    }

    public List<Request> getAllRequest() {
        String sql = "SELECT * FROM requests";

        return jdbcTemplate.query(sql, new RequestRowMapper());
    }

    public List<Request> getPendingRequest() {
        String sql = "SELECT * FROM requests WHERE status = 'PENDING'";

        return jdbcTemplate.query(sql, new RequestRowMapper());
    }

    public boolean updateRequest(int requestId, Status status) {
        String sql = "UPDATE requests SET status = ? WHERE request_id = ?";

        int rowsAffected = jdbcTemplate.update(sql);

        return rowsAffected == 1;
    }
}
