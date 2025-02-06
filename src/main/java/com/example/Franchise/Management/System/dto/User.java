package com.example.Franchise.Management.System.dto;

import com.example.Franchise.Management.System.enums.Role;

public class User {
    private String userId;
    private String name;
    private String password;
    private int franchiseId;
    private String franchiseLocation;
    private Role role;

    public User() {
    }

    public User(String userid, String name, String password, int franchiseId, String franchiseLocation, String role) {
        this.userId = userid;
        this.name = name;
        this.password = password;
        this.franchiseId = franchiseId;
        this.franchiseLocation = franchiseLocation;
        this.role = Role.valueOf(role);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFranchiseLocation() {
        return franchiseLocation;
    }

    public void setFranchiseLocation(String franchiseLocation) {
        this.franchiseLocation = franchiseLocation;
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}