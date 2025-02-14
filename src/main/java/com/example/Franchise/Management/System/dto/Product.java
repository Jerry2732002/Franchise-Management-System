package com.example.Franchise.Management.System.dto;

public class Product {
    private int productId;
    private String productName;
    private String productCompany;
    private double retailPrice;

    public Product(int productId, String productName, String productCompany, double retailPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productCompany = productCompany;
        this.retailPrice = retailPrice;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
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
}