package com.example.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.net.MalformedURLException;

public class BaseTest {
    protected WebDriver driver;
    
    @BeforeMethod
    @Parameters({"browser", "gridURL"})
    public void setup(String browser, String gridURL) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        
        driver = new RemoteWebDriver(new URL(gridURL), capabilities);
    }
    
    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
