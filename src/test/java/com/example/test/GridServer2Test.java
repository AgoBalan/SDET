package com.example.test;

import org.testng.annotations.Test;
import java.sql.SQLException;

public class GridServer2Test extends BaseTest {
    
    @Test
    public void testAfricaCustomers() throws SQLException {
          // Navigate to your web application
        driver.get("https://www.google.com");
        driver.quit();
    }
}
