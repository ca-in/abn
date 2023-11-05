package com.example.demo;

public class User {
    private String username;
    private String password;
    private String password_old = "";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordOld() {
        return password_old;
    }

    public void setPasswordOld(String password_old) {
        this.password_old = password_old;
    }
}