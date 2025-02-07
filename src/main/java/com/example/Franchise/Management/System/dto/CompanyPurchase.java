package com.example.Franchise.Management.System.dto;

import java.sql.Date;

public class CompanyPurchase {
    private int companyPurchaseId;
    private int productId;
    private String productName;
    private String productCompany;
    private int quantity;
    private Date dateOfPurchase;

    public CompanyPurchase() {
    }

    public CompanyPurchase(int companyPurchaseId,int productId, String productName, String productCompany, int quantity, Date dateOfPurchase) {
        this.companyPurchaseId = companyPurchaseId;
        this.productId = productId;
        this.productName = productName;
        this.productCompany = productCompany;
        this.quantity = quantity;
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(String productCompany) {
        this.productCompany = productCompany;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getCompanyPurchaseId() {
        return companyPurchaseId;
    }

    public void setCompanyPurchaseId(int companyPurchaseId) {
        this.companyPurchaseId = companyPurchaseId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
