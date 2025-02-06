package com.example.Franchise.Management.System.dto;

import java.sql.Date;

public class Purchases {
    private String productName;
    private String companyName;
    private int quantity;
    private String Location;
    private String buildingName;
    private String userName;
    private Date dateOfPurchase;

    public Purchases(String productName, String companyName, int quantity, String location, String buildingName, String userName, Date dateOfPurchase) {
        this.productName = productName;
        this.companyName = companyName;
        this.quantity = quantity;
        Location = location;
        this.buildingName = buildingName;
        this.userName = userName;
        this.dateOfPurchase = dateOfPurchase;
    }

    public Purchases() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

}
