package com.example.Franchise.Management.System.dto;

public class Franchise {
    private int franchiseId;
    private String location;
    private String buildingName;

    public Franchise(int franchiseId, String location, String buildingName) {
        this.franchiseId = franchiseId;
        this.location = location;
        this.buildingName = buildingName;
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
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
