package com.example.model;

import com.example.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerInfoDAO {
    
    public void createCustomer(CustomerInfo customer) throws SQLException {
        String sql = "INSERT INTO CustomerInfo (CourseName, PurchasedDate, Amount, Location) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getCourseName());
            pstmt.setDate(2, customer.getPurchasedDate());
            pstmt.setInt(3, customer.getAmount());
            pstmt.setString(4, customer.getLocation());
            
            pstmt.executeUpdate();
        }
    }
    
    public CustomerInfo getCustomerByCourseName(String courseName) throws SQLException {
        String sql = "SELECT * FROM CustomerInfo WHERE CourseName = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, courseName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CustomerInfo(
                        rs.getString("CourseName"),
                        rs.getDate("PurchasedDate"),
                        rs.getInt("Amount"),
                        rs.getString("Location")
                    );
                }
            }
        }
        return null;
    }
    
    public List<CustomerInfo> getAllCustomers() throws SQLException {
        List<CustomerInfo> customers = new ArrayList<>();
        String sql = "SELECT * FROM CustomerInfo LIMIT 2 ";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                CustomerInfo customer = new CustomerInfo(
                    rs.getString("CourseName"),
                    rs.getDate("PurchasedDate"),
                    rs.getInt("Amount"),
                    rs.getString("Location")
                );
                customers.add(customer);
            }
        }
        return customers;
    }

    public List<CustomerInfo> getCustomersByLocation(String location) throws SQLException {
        List<CustomerInfo> customers = new ArrayList<>();
        String sql = "SELECT * FROM CustomerInfo WHERE Location = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, location);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CustomerInfo customer = new CustomerInfo(
                        rs.getString("CourseName"),
                        rs.getDate("PurchasedDate"),
                        rs.getInt("Amount"),
                        rs.getString("Location")
                    );
                    customers.add(customer);
                }
            }
        }
        return customers;
    }
    
    public int getTotalAmountByLocation(String location) throws SQLException {
        String sql = "SELECT SUM(Amount) as total FROM CustomerInfo WHERE Location = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, location);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }
}
