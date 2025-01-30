package com.cathaybk.csp.model;

/**
 * Represents a User entity.
 */
public class User {
    private Integer userId;
    private String account;
    private String password;

    // Constructor
    public User() {
    }
    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}