package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // Database Configuration
    private static final String URL = "jdbc:mysql://localhost:3306/business";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                  //Before java 6 this class present inside dependecy    <artifactId>mysql-connector-java</artifactId>
               Class.forName("com.mysql.cj.jdbc.Driver"); 
               connection = DriverManager.getConnection(URL, USER, PASSWORD);

                //after java 6
              //  Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             
            } catch (Exception e) {
                throw new SQLException("MySQL JDBC Driver not found.", e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
