package com.example.test;

import org.testng.annotations.Test;

public class GridServer1Test extends BaseTest {
    
    @Test
    public void testAsiaCustomers() {
        
        // Navigate to your web application
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.quit();
        try{
        Thread.sleep(1000);
        }catch(Exception e){}
    }
}
