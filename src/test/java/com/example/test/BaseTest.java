package com.example.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.nio.Buffer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

public class BaseTest {
    protected WebDriver driver;
    
    @BeforeSuite
    public void runCompose() throws IOException, InterruptedException {
       
        try {
          
            deleteFile("outputOfDown.txt");
            Thread.sleep(10000);
            deleteFile("outputOfNodeUp.txt");
            Thread.sleep(10000);
            deleteFile("output.txt");
           
            //start the Jenkins
            //Runtime.getRuntime().exec("cmd /c start Bat_Files\\jenkins.bat");
            //To run locally docker 
          //  Runtime.getRuntime().exec("cmd /c start Bat_Files\\dockerUp.bat");
            //To run locally docker in Jenkins
            Runtime.getRuntime().exec("cmd /c start Bat_Files\\dockerJenkinsUp.bat");
            verifyServerStatus("output.txt","Node has been added",3);
            //scale up the chrome node
            Runtime.getRuntime().exec("cmd /c start Bat_Files\\scaleUpNode.bat");
            verifyServerStatus("outputOfNodeUp.txt","chrome-5  Creating",1);
            verifyServerStatus("outputOfNodeUp.txt","chrome-5  Started",1);
            Thread.sleep(10000);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally{
            Thread.sleep(5000); // Wait for 5 seconds to allow the command to execute
            // Kill CMD by task name
           // Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
        }
        System.out.println("Starting Docker Compose setup...");
    }
    @AfterSuite
    public void tearDownCompose() throws IOException, InterruptedException {
        // Stop Docker Compose setup
          //To run locally with docker
       // Runtime.getRuntime().exec("cmd /c start Bat_Files\\dockerDown.bat");
        //To run locally docker in Jenkins
         Runtime.getRuntime().exec("cmd /c start Bat_Files\\dockerJenkinsDown.bat");
        Thread.sleep(10000);
        verifyServerStatus("outputOfDown.txt"," Network jdbc-demo_default  Removed",1);
        // Kill CMD by task name
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
     
    }
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

    public void verifyServerStatus(String fileName,String lookFor, int count) throws InterruptedException, IOException{
      BufferedReader reader = null;
        try{
        Thread.sleep(5000); // Wait for 5 seconds to allow the command to execute
        String file = fileName;
        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            if(line.contains(lookFor)) {
                if(line.split(lookFor).length>=count){
                break;
                }
            }
            line = reader.readLine();
        }
        reader.close();
    } catch(Exception e){reader.close(); }
  }

    public void deleteFile(String fileName){
           File file = new File(fileName);
        if (file.delete()) {    
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }
    }
}
