package com.example.Franchise.Management.System.dto;

public class Stock {
    private int franchiseId;
    private int productId;
    private String productName;
    private String productCompany;
    private int quantity;

    public Stock(int franchiseId, int product_id, String productName, String productCompany, int quantity) {
        this.franchiseId = franchiseId;
        this.productId = product_id;
        this.productName = productName;
        this.productCompany = productCompany;
        this.quantity = quantity;
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
