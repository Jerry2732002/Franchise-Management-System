package com.example.Franchise.Management.System.dto;

import com.example.Franchise.Management.System.enums.Status;

public class Request {
    private int requestID;
    private int franchiseId;
    private int productId;
    private String productName;
    private String productCompany;
    private String location;
    private String buildingName;
    private int quantity;
    private Status status;

    public Request(int requestID, int franchiseId, int productId, String productName, String productCompany, String location, String buildingName, int quantity, String status) {
        this.requestID = requestID;
        this.franchiseId = franchiseId;
        this.productId = productId;
        this.productName = productName;
        this.productCompany = productCompany;
        this.location = location;
        this.buildingName = buildingName;
        this.quantity = quantity;
        this.status = Status.valueOf(status);
    }

    public Request() {
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
