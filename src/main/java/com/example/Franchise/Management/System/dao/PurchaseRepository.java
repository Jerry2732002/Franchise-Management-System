package com.example.Franchise.Management.System.dao;

import com.example.Franchise.Management.System.dto.Purchases;
import com.example.Franchise.Management.System.dto.Report;
import com.example.Franchise.Management.System.rowmapper.CompanyReportRowMapper;
import com.example.Franchise.Management.System.rowmapper.PurchaseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class PurchaseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PurchaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addPurchase(Purchases purchase) {
        String sql = "INSERT INTO purchases (product_id, user_id, quantity, date_of_purchase) VALUES (?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, purchase.getProductId(), purchase.getUserId(), purchase.getQuantity(), purchase.getDateOfPurchase());

        return rowsAffected == 1;
    }

    public List<Purchases> getAllPurchases() {
        String sql = "SELECT * FROM purchases p JOIN products pr ON p.product_id = pr.product_id JOIN users u ON u.user_id = p.user_id";

        return jdbcTemplate.query(sql, new PurchaseRowMapper());
    }

    public List<Report> getFranchiseReport(Date startDate, Date endDate, int franchiseId) {
        String sql = "SELECT \n" +
                "\tpr.product_name, \n" +
                "    pr.product_company, \n" +
                "    p.quantity, \n" +
                "    p.date_of_purchase as supply_purchase_date, \n" +
                "    pr.retail_price as price,\n" +
                "    (p.quantity * pr.retail_price) AS total_price\n" +
                "FROM \n" +
                "    purchases p \n" +
                "JOIN \n" +
                "    products pr \n" +
                "ON \n" +
                "    p.product_id = pr.product_id\n" +
                "JOIN \n" +
                "\tusers u\n" +
                "ON\n" +
                "\tp.user_id = u.user_id\n" +
                "JOIN \n" +
                "\tfranchises f\n" +
                "ON \n" +
                "\tf.franchise_id = u.franchise_id\n" +
                "WHERE p.date_of_purchase BETWEEN ? AND ? AND f.franchise_id = ?";

        List<Report> reports = jdbcTemplate.query(sql, new CompanyReportRowMapper(),startDate,endDate, franchiseId);

        for (Report report : reports) {
            report.setBuyOrSell("SELL");
        }
        return reports;
    }

}
