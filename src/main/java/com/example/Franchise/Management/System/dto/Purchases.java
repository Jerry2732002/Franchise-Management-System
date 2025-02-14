package com.example.Franchise.Management.System.dto;

import java.sql.Date;

public class Purchases {
    private int purchaseId;
    private int productId;
    private String productName;
    private String productCompany;
    private int quantity;
    private int franchiseId;
    private String location;
    private String buildingName;
    private String userName;
    private String userId;
    private Date dateOfPurchase;
    private boolean returned;

    public Purchases(int purchaseId, int productId, String productName, String productCompany, int quantity, int franchiseId, String location, String buildingName, String userId, String userName, Date dateOfPurchase, boolean returned) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.productName = productName;
        this.productCompany = productCompany;
        this.quantity = quantity;
        this.franchiseId = franchiseId;
        this.location = location;
        this.buildingName = buildingName;
        this.userId = userId;
        this.userName = userName;
        this.dateOfPurchase = dateOfPurchase;
        this.returned = returned;
    }

    public Purchases() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
    }
}
