package com.swalha.dto;

import com.swalha.entity.UserRole;

public class LoginResponse {
    
    private String token;
    private String username;
    private String fullName;
    private UserRole role;
    
    // Default constructor
    public LoginResponse() {}
    
    // Constructor with parameters
    public LoginResponse(String token, String username, String fullName, UserRole role) {
        this.token = token;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
} 