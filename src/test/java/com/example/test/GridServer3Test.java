package com.example.test;

import org.testng.annotations.Test;
import java.sql.SQLException;

public class GridServer3Test extends BaseTest {
    
    @Test
    public void testTotalAmounts() throws SQLException {
       // Navigate to your web application
        driver.get("https://www.google.com");
        driver.quit();
    }
}
