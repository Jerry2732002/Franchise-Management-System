package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Report;
import com.example.Franchise.Management.System.dto.Supply;
import com.example.Franchise.Management.System.rowmapper.CompanyReportRowMapper;
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

    public List<Report> getCompanyReport(Date startDate, Date endDate) {
        String sql = "SELECT \n" +
                "\tp.product_name, \n" +
                "    p.product_company, \n" +
                "    s.quantity, \n" +
                "    s.date_of_supply as supply_purchase_date, \n" +
                "    p.distributor_price AS price,\n" +
                "    (s.quantity * p.distributor_price) AS total_price\n" +
                "FROM \n" +
                "    supply s \n" +
                "JOIN \n" +
                "    products p \n" +
                "ON \n" +
                "    p.product_id = s.product_id\n" +
                "JOIN \n" +
                "\tfranchises f\n" +
                "ON \n" +
                "\tf.franchise_id = s.franchise_id\n" +
                "WHERE s.date_of_supply BETWEEN ? AND ?";
        List<Report> reports = jdbcTemplate.query(sql, new CompanyReportRowMapper(),startDate,endDate);

        for (Report report : reports) {
            report.setBuyOrSell("SELL");
        }
        return reports;
    }

    public List<Report> getFranchiseReport(Date startDate, Date endDate, int franchiseId) {
        String sql = "SELECT \n" +
                "\tp.product_name, \n" +
                "    p.product_company, \n" +
                "    s.quantity, \n" +
                "    s.date_of_supply as supply_purchase_date, \n" +
                "    p.distributor_price as price,\n" +
                "    (s.quantity * p.distributor_price) AS total_price\n" +
                "FROM \n" +
                "    supply s \n" +
                "JOIN \n" +
                "    products p \n" +
                "ON \n" +
                "    p.product_id = s.product_id\n" +
                "JOIN \n" +
                "\tfranchises f\n" +
                "ON \n" +
                "\tf.franchise_id = s.franchise_id\n" +
                "WHERE s.date_of_supply BETWEEN ? AND ? AND f.franchise_id = ?";
        List<Report> reports = jdbcTemplate.query(sql, new CompanyReportRowMapper(),startDate,endDate,franchiseId);

        for (Report report : reports) {
            report.setBuyOrSell("BUY");
        }
        return reports;
    }

}
