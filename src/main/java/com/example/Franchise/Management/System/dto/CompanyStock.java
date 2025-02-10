package com.example.Franchise.Management.System.dto;

public class CompanyStock {
    private int productId;
    private String productName;
    private String productCompany;
    private int quantity;

    public CompanyStock() {

    }

    public CompanyStock(int productId, String productName, String productCompany, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productCompany = productCompany;
        this.quantity = quantity;
    }

    public CompanyStock(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(String productCompany) {
        this.productCompany = productCompany;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
