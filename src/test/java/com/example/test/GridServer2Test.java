package com.example.test;

import org.testng.annotations.Test;
import java.sql.SQLException;

public class GridServer2Test extends BaseTest {
    
    @Test
    public void testAfricaCustomers() throws SQLException {
          // Navigate to your web application
        driver.get("https://www.yahoo.com");
           System.out.println("Title: " + driver.getTitle());
        driver.quit();
           try{
        Thread.sleep(1000);
        }catch(Exception e){}
    }
}
