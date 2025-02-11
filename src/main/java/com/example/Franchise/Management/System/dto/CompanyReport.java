package com.example.Franchise.Management.System.dto;

import java.sql.Date;

public class CompanyReport {
    private String productName;
    private String productCompany;
    private int quantity;
    private Date supplyPurchaseDate;
    private double price;
    private double totalPrice;
    private String buyOrSell;

    public CompanyReport(String productName, String productCompany, int quantity, Date supplyPurchaseDate, double wholesalePrice, double totalAmount, String buyOrSell) {
        this.productName = productName;
        this.productCompany = productCompany;
        this.quantity = quantity;
        this.supplyPurchaseDate = supplyPurchaseDate;
        this.price = wholesalePrice;
        this.totalPrice = totalAmount;
        this.buyOrSell = buyOrSell;
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

    public Date getSupplyPurchaseDate() {
        return supplyPurchaseDate;
    }

    public void setSupplyPurchaseDate(Date supplyPurchaseDate) {
        this.supplyPurchaseDate = supplyPurchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(String buyOrSell) {
        this.buyOrSell = buyOrSell;
    }
}
