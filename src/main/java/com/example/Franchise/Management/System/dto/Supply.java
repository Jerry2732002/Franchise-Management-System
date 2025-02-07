package com.example.Franchise.Management.System.dto;

import java.sql.Date;

public class Supply {
    private int supplyId;
    private int productId;
    private String productName;
    private String productCompany;
    private int quantity;
    private int franchiseId;
    private String location;
    private String buildingName;
    private Date dateOfSupply;

    public Supply(int supplyId, int productId, String productName, String productCompany, int quantity, int franchiseId, String location, String buildingName, Date dateOfSupply) {
        this.supplyId = supplyId;
        this.productId = productId;
        this.productName = productName;
        this.productCompany = productCompany;
        this.quantity = quantity;
        this.franchiseId = franchiseId;
        this.location = location;
        this.buildingName = buildingName;
        this.dateOfSupply = dateOfSupply;
    }

    public Supply() {
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

    public Date getDateOfSupply() {
        return dateOfSupply;
    }

    public void setDateOfSupply(Date dateOfSupply) {
        this.dateOfSupply = dateOfSupply;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
    }
}
