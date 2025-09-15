package com.example.test;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GridServer1Test extends BaseTest {
    
    @Test(dataProvider = "customerData")
    public void testAsiaCustomers(String region) throws Exception {
        
        // Navigate to your web application
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.findElement(By.partialLinkText("type")).sendKeys(region);
        driver.quit();
        try{
        Thread.sleep(1000);
        }catch(Exception e){}
    }

    @DataProvider(name = "customerData")
    public Object[][] customerDataProvider() {
        return new Object[][] {
            {"Asia"},
            {"Europe"},
            {"America"}
        };
    }
}
