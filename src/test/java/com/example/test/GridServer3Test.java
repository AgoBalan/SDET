package com.example.test;

import org.testng.annotations.Test;
import java.sql.SQLException;

public class GridServer3Test extends BaseTest {
    
    @Test
    public void testTotalAmounts() throws SQLException {
       // Navigate to your web application
        driver.get("https://www.gmail.com");
           System.out.println("Title: " + driver.getTitle());
        driver.quit();
           try{
        Thread.sleep(1000);
        }catch(Exception e){}
    }
}
