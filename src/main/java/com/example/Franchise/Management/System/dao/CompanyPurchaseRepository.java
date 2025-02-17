package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.CompanyPurchase;
import com.example.Franchise.Management.System.dto.Report;
import com.example.Franchise.Management.System.rowmapper.CompanyPurchaseRowMapper;
import com.example.Franchise.Management.System.rowmapper.CompanyReportRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class CompanyPurchaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyPurchaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addPurchase(CompanyPurchase purchase) {
        String sql = "INSERT INTO company_purchases (product_id, quantity, date_of_purchase, wholesale_price, distributor_price) VALUES (?,?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, purchase.getProductId(), purchase.getQuantity(), purchase.getDateOfPurchase(), purchase.getWholesalePrice(), purchase.getDistributorPrice());

        return rowsAffected == 1;
    }

    public List<CompanyPurchase> getAllCompanyPurchase() {
        String sql = "SELECT * FROM company_purchases cp JOIN products p ON cp.product_id = p.product_id";

        return jdbcTemplate.query(sql, new CompanyPurchaseRowMapper());
    }

    public List<Report> getCompanyReport(Date startDate, Date endDate) {
        String sql = "SELECT\n" +
                "    p.product_name,\n" +
                "    p.product_company,\n" +
                "    cp.quantity,\n" +
                "    cp.date_of_purchase AS supply_purchase_date,\n" +
                "    cp.wholesale_price AS price,\n" +
                "    (cp.quantity * cp.wholesale_price) AS total_price\n" +
                "FROM\n" +
                "    company_purchases cp\n" +
                "JOIN\n" +
                "    products p ON p.product_id = cp.product_id\n" +
                "WHERE\n" +
                "    cp.date_of_purchase BETWEEN ? AND ?";
        List<Report> reports = jdbcTemplate.query(sql, new CompanyReportRowMapper(),startDate,endDate);

        for (Report report : reports) {
            report.setBuyOrSell("BUY");
        }
        return reports;
    }
}
