package com.example.Franchise.Management.System.dto;

import java.sql.Date;

public class Supply {
    private String productName;
    private String productCompany;
    private int quantity;
    private String location;
    private String buildingName;
    private Date dateOfSupply;

    public Supply(String productName, String productCompany, int quantity, String location, String buildingName, Date dateOfSupply) {
        this.productName = productName;
        this.productCompany = productCompany;
        this.quantity = quantity;
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
}
