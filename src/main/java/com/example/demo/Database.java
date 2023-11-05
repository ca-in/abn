package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

    public Boolean isPasswordCorrect(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.next())
                return false;

            String hashedPassword = rs.getString("password");
            System.out.println("Hashed password: " + hashedPassword);
            System.out.println("Password: " + password);
            return BCrypt.checkpw(password, hashedPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void loginUser(String username) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE users SET online = 1 WHERE username='" + username + "'";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
/*
    public String getPasswordOld(String username){
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT password FROM users WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            return rs.getString("password");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
*/
    public void recoverUser(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE users SET password = '" + password + "' WHERE username='" + username + "'";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<String>();

        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT username FROM users WHERE online = 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                onlineUsers.add(rs.getString("username"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return onlineUsers;
    }

    public void logoutUser(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE users SET online = 0 WHERE username='" + username + "'";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
