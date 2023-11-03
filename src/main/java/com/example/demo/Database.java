package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    private Connection conn;

    public Database() {
        this.conn = null;
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean isUserExists(String username) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void registerUser(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
