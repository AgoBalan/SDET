package com.example.Selenium;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.RemoteWebDriver;

public class standalone {
    
    public static void run() throws MalformedURLException{
          // TO trigger selenium test againt docker container.
        URL ur = new URL("http://localhost:4444/wd/hub");
        RemoteWebDriver driver = new RemoteWebDriver(ur, new org.openqa.selenium.chrome.ChromeOptions());
        driver.get("http://www.google.com");
        System.out.println(driver.getTitle()); 
        driver.quit();
    }
}
