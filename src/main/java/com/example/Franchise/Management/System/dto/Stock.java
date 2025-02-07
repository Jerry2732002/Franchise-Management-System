package com.example.Franchise.Management.System.dto;

public class Stock {
    private int franchiseId;
    private String location;
    private String buildingName;
    private int productId;
    private String productName;
    private String productCompany;
    private int quantity;

    public Stock(int franchiseId, String location, String buildingName, int productId, String productName, String productCompany, int quantity) {
        this.franchiseId = franchiseId;
        this.location = location;
        this.buildingName = buildingName;
        this.productId = productId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
